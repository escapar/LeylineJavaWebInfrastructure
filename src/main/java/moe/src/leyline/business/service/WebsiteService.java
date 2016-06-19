package moe.src.leyline.business.service;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

import org.asynchttpclient.AsyncHttpClient;
import org.asynchttpclient.DefaultAsyncHttpClient;
import org.asynchttpclient.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import moe.src.leyline.business.domain.user.DomainUser;
import moe.src.leyline.business.domain.website.Website;
import moe.src.leyline.business.domain.website.WebsiteRepo;
import moe.src.leyline.business.domain.website.WebsiteUserVerify;
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

        if(!url.contains(w.getDomain()) || !checkOwnerOf(w.getUser())){
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
        if(checkOwnerOf(w.getUser())) {
            return false;
        }
        w.addVerify((DomainUser) getCurrentDomainUser());
        save(w);
        if (w.getWebsiteUserVerifies().size() > 1) {
            verify(w);
            return true;
        }
        return true;
    }

    public Boolean verifyManually(Long id) throws PersistenceException{
        Website w = get(id);
        verify(w);
        return true;
    }

    private Website verify(Website w) throws PersistenceException{
        w.setVerifyKey(null);
        domainUserService.verify(w.getUser());
        return save(w);
    }

    private Website getByKey(String key){
        return websiteRepo.getByVerifyKey(key);
    }
}
