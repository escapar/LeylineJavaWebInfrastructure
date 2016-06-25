package moe.src.leyline.business.service;

import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.stream.Collectors;

import org.asynchttpclient.DefaultAsyncHttpClient;
import org.asynchttpclient.Response;
import org.jsoup.Jsoup;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import moe.src.leyline.business.domain.user.User;
import moe.src.leyline.business.domain.website.Website;
import moe.src.leyline.business.domain.website.WebsiteRelation;
import moe.src.leyline.business.domain.website.WebsiteRelationRepo;
import moe.src.leyline.business.domain.website.WebsiteRepo;
import moe.src.leyline.business.infrastructure.screenshot.ScreenshotProcess;
import moe.src.leyline.framework.infrastructure.common.exceptions.PersistenceException;
import moe.src.leyline.framework.service.LeylineTransactionalService;
import static org.assertj.core.api.Assertions.assertThat;

/**
 * Created by bytenoob on 6/19/16.
 */
@Service
public class WebsiteService extends LeylineTransactionalService<WebsiteRepo, Website> {
    @Autowired
    private UserService userService;
    @Autowired
    private WebsiteRepo repo;

    @Autowired
    private WebsiteRelationRepo websiteRelationRepo;

    public Boolean verifyByURL(Long id, String url) throws PersistenceException, InterruptedException, ExecutionException {
        Website w = get(id);

        URLAssertion(w, url);

        Future<Response> f = new DefaultAsyncHttpClient().prepareGet(url).execute();

        if (Jsoup.parse(f.get().getResponseBody())
                .getElementsByTag("head")
                .html()
                .contains(w.getVerifyKey())) {

            verify(w);
            return true;
        }

        return false;
    }

    public Boolean verifyByFile(Long id, String url) throws PersistenceException, InterruptedException, ExecutionException {
        Website w = get(id);

        URLAssertion(w, url);

        Future<Response> f = new DefaultAsyncHttpClient().prepareGet(url).execute();

        if (url.endsWith(w.getVerifyKey() + ".txt")
                && f.get().getResponseBody().contains(w.getVerifyKey())) {

            verify(w);
            return true;
        }

        return false;
    }

    public Boolean verifyByFriend(String key) throws PersistenceException {
        Website w = getByKey(key);

        userAssertion(w.getUser());

        w = save(
                w.addVerify((User) getCurrentUser()));

        if (w.getWebsiteUserVerifies().size() > 1) {
            verify(w);
        }

        return true;
    }

    public Boolean verifyManually(Long id) throws PersistenceException {
        Website w = get(id);

        assertThat(w).isNotNull();

        verify(w);

        return true;
    }

    private Website verify(Website w) throws PersistenceException {
        assertThat(w).isNotNull();

        userService.verify(w.getUser());

        return save(
                w.setVerifyKey(null));
    }

    private Website getByKey(String key) {
        return repo.getByVerifyKey(key);
    }

    public byte[] screenshot(Long id) throws Exception {
        return ScreenshotProcess.execute(get(id).getDomain());
    }

    private void URLAssertion(Website w, String url) {
        assertThat(w).isNotNull()
                .extracting(Website::getDomain)
                .contains(url);

        userAssertion(w.getUser());

        assertThat(w)
                .extracting(Website::getVerifyKey)
                .isNotNull();
    }

    public WebsiteRelation establishRelation(Website m, Website s) {
        assertThat(m).isNotNull();
        assertThat(s).isNotNull();
        userAssertion(m.getUser());
        assertThat(websiteRelationRepo.findByMasterAndServant(m,s)).isNull();

        return websiteRelationRepo.save(m.addFriend(
                new WebsiteRelation()
                        .setApproved(false)
                        .setMaster(m)
                        .setServant(s)
                        .setTitle(s.getTitle())
                        .setDescription(m.getTitle() + " x " + s.getTitle())));

    }

    public WebsiteRelation link(Long masterId, Long servantId) throws PersistenceException {
        Website m = findOne(masterId);
        Website s = findOne(servantId);

        assertThat(m).isNotNull();
        assertThat(s).isNotNull();

        /*List<WebsiteRelation> existingServant =
                m.getFriends().parallelStream()
                        .filter(i-> !i.getServant().equals(s))
                        .collect(Collectors.toList());*/

        WebsiteRelation existing = websiteRelationRepo.findByMasterAndServant(m, s);

        return existing == null ? establishRelation(m, s) : handshake(existing);
    }

    public WebsiteRelation handshake(WebsiteRelation wr) {
        assertThat(wr.isApproved()).isFalse();
        userAssertion(wr.getServant().getUser());

        return websiteRelationRepo.save(wr.setApproved(true));
    }

    public List<WebsiteRelation> getLinks() {
        User u = (User) getCurrentUser();
        assertThat(u).isNotNull();
        return repo.findByUser(u).parallelStream().flatMap(i -> i.getFriends().stream()).sorted().collect(Collectors.toList());
    }

    public List<WebsiteRelation> getReferenced() {
        User u = (User) getCurrentUser();
        assertThat(u).isNotNull();
        return repo.findByUser(u).parallelStream().flatMap(i -> i.getReferencedBy().stream()).sorted().collect(Collectors.toList());
    }
}
