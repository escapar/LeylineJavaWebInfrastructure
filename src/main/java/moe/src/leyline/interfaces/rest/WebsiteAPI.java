package moe.src.leyline.interfaces.rest;

import moe.src.leyline.business.domain.website.Website;
import moe.src.leyline.business.domain.website.WebsiteRepo;
import moe.src.leyline.business.service.WebsiteService;
import moe.src.leyline.framework.interfaces.rest.LeylineRestCRUD;
import moe.src.leyline.interfaces.dto.WebsiteDTO;

/**
 * Created by bytenoob on 6/19/16.
 */
public class WebsiteAPI extends LeylineRestCRUD<WebsiteService,WebsiteDTO,Website> {
}
