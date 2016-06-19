package moe.src.leyline.business.service;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

import org.asynchttpclient.AsyncHttpClient;
import org.asynchttpclient.DefaultAsyncHttpClient;
import org.asynchttpclient.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import moe.src.leyline.business.domain.user.DomainUser;
import moe.src.leyline.business.domain.website.Website;
import moe.src.leyline.business.domain.website.WebsiteRepo;
import moe.src.leyline.business.domain.website.WebsiteUserVerify;
import moe.src.leyline.business.infrastructure.screenshot.ScreenshotProcess;
import moe.src.leyline.framework.infrastructure.common.exceptions.PersistenceException;
import moe.src.leyline.framework.service.LeylineDomainService;

/**
 * Created by bytenoob on 6/19/16.
 */
@Service
public class WebsiteService extends LeylineDomainService<WebsiteRepo,Website> {
    @Autowired
    private DomainUserService domainUserService;
    @Autowired
    private WebsiteRepo websiteRepo;

    public Boolean verifyByURL(Long id,String url) throws PersistenceException,InterruptedException,ExecutionException {
        Website w = get(id);

        if(w==null || !url.contains(w.getDomain()) || !checkOwnerOf(w.getUser())){
            return false;
        }else if(w.getVerifyKey()==null){
            return true;
        }

        Future<Response> f = new DefaultAsyncHttpClient().prepareGet(url).execute();
        if(f.get().getResponseBody().contains(w.getVerifyKey())){
            verify(w);
            return true;
        }
        return false;
    }

    public Boolean verifyByFriend(String key) throws PersistenceException{
        Website w = getByKey(key);
        if(w == null || checkOwnerOf(w.getUser())) {
            return false;
        }
        w.addVerify((DomainUser) getCurrentDomainUser());
        w = save(w);
        if (w.getWebsiteUserVerifies().size() > 1) {
            verify(w);
            return true;
        }
        return true;
    }

    public Boolean verifyManually(Long id) throws PersistenceException{
        Website w = get(id);
        if(w == null){
            return null;
        }
        verify(w);
        return true;
    }

    private Website verify(Website w) throws PersistenceException{
        if(w == null){
            return null;
        }
        w.setVerifyKey(null);
        domainUserService.verify(w.getUser());
        return save(w);
    }

    private Website getByKey(String key){
        return websiteRepo.getByVerifyKey(key);
    }

    public byte[] screenshot(Long id) throws Exception{
        return ScreenshotProcess.execute(get(id).getDomain());
    }
}
