package moe.src.leyline.interfaces.rest;

import moe.src.leyline.business.domain.website.Website;
import moe.src.leyline.business.service.WebsiteService;
import moe.src.leyline.framework.infrastructure.common.exceptions.PersistenceException;
import moe.src.leyline.framework.interfaces.rest.LeylineRestCRUD;
import moe.src.leyline.interfaces.dto.WebsiteDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.concurrent.ExecutionException;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;


/**
 * Created by bytenoob on 6/19/16.
 */
@RestController
@RequestMapping(value = "api/website/")
public class WebsiteAPI extends LeylineRestCRUD<WebsiteService,WebsiteDTO,Website> {
    @Autowired
    WebsiteService websiteService;

    @RequestMapping(value = "{id}/verify/url", method = RequestMethod.POST, consumes = APPLICATION_JSON_VALUE,produces = APPLICATION_JSON_VALUE)
    public @ResponseBody Boolean websiteVerify(@PathVariable Long id , @RequestBody String url) throws PersistenceException,InterruptedException,ExecutionException{
        return websiteService.verifyByURL(id,url);
    }

    @RequestMapping(value = "verify/friend", method = RequestMethod.POST, consumes = APPLICATION_JSON_VALUE,produces = APPLICATION_JSON_VALUE)
    public @ResponseBody Boolean websiteVerify(@RequestBody String key ) throws PersistenceException,InterruptedException,ExecutionException{
        return websiteService.verifyByFriend(key);
    }

    @RequestMapping(value = "{id}/verify/manual", method = RequestMethod.POST, consumes = APPLICATION_JSON_VALUE,produces = APPLICATION_JSON_VALUE)
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public @ResponseBody Boolean websiteVerifyManual(@PathVariable Long id ) throws PersistenceException,InterruptedException,ExecutionException{
        return websiteService.verifyManually(id);
    }

    @RequestMapping(value = "{id}/screenshot", method = RequestMethod.GET)
    public @ResponseBody void screenshot(@PathVariable Long id ) throws Exception{
        websiteService.screenshot(id);
    }
}
