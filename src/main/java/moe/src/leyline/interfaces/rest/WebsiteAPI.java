package moe.src.leyline.interfaces.rest;

import java.util.List;
import java.util.concurrent.ExecutionException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import moe.src.leyline.business.domain.website.Website;
import moe.src.leyline.business.domain.website.WebsiteRelation;
import moe.src.leyline.business.service.WebsiteService;
import moe.src.leyline.framework.infrastructure.common.exceptions.PersistenceException;
import moe.src.leyline.framework.interfaces.dto.assembler.DTOAssembler;
import moe.src.leyline.framework.interfaces.rest.LeylineRestCRUD;
import moe.src.leyline.interfaces.dto.website.WebsiteDTO;
import moe.src.leyline.interfaces.dto.website.WebsiteRelationDTO;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

/**
 * Created by bytenoob on 6/19/16.
 */
@RestController
@RequestMapping(value = "api/website/")
public class WebsiteAPI extends LeylineRestCRUD<WebsiteService, Website, WebsiteDTO> {
    @Autowired
    WebsiteService websiteService;

    DTOAssembler<WebsiteRelation, WebsiteRelationDTO> relationDTOAssembler = new DTOAssembler<>(WebsiteRelation.class, WebsiteRelationDTO.class);

    @RequestMapping(value = "{id}/verify/head", method = RequestMethod.POST, consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
    public @ResponseBody Boolean websiteVerify(@PathVariable Long id, @RequestBody String url) throws PersistenceException, InterruptedException, ExecutionException {
        return websiteService.verifyByURL(id, url);
    }

    @RequestMapping(value = "{id}/verify/file", method = RequestMethod.POST, consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
    public @ResponseBody Boolean websiteVerifyByFile(@PathVariable Long id, @RequestBody String url) throws PersistenceException, InterruptedException, ExecutionException {
        return websiteService.verifyByURL(id, url);
    }

    @RequestMapping(value = "verify/friend", method = RequestMethod.POST, consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
    public @ResponseBody Boolean websiteVerify(@RequestBody String key) throws PersistenceException, InterruptedException, ExecutionException {
        return websiteService.verifyByFriend(key);
    }

    @RequestMapping(value = "{id}/verify/manual", method = RequestMethod.POST, consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public @ResponseBody Boolean websiteVerifyManual(@PathVariable Long id) throws PersistenceException, InterruptedException, ExecutionException {
        return websiteService.verifyManually(id);
    }

    @RequestMapping(value = "{id}/screenshot", method = RequestMethod.GET)
    public @ResponseBody void screenshot(@PathVariable Long id) throws Exception {
        websiteService.screenshot(id);
    }

    @RequestMapping(value = "{masterId}/{servantId}/link", method = RequestMethod.GET)
    public @ResponseBody WebsiteRelationDTO doLink(@PathVariable Long masterId, @PathVariable Long servantId) throws Exception {
        return relationDTOAssembler.buildDTO(
                websiteService.link(masterId, servantId)
        );
    }

    @RequestMapping(value = "links", method = RequestMethod.GET)
    public @ResponseBody List link() throws Exception {
        return relationDTOAssembler.buildDTOList(
                websiteService.getLinks()
        );
    }

    @RequestMapping(value = "referenced", method = RequestMethod.GET)
    public @ResponseBody List referenced() throws Exception {
        return relationDTOAssembler.buildDTOList(
                websiteService.getReferenced()
        );
    }

}
