package moe.src.leyline.interfaces.rest;

import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import moe.src.leyline.business.domain.commons.lang.Lang;
import moe.src.leyline.business.domain.commons.lang.LangRepo;
import moe.src.leyline.business.domain.commons.location.Location;
import moe.src.leyline.business.domain.commons.location.LocationRepo;
import moe.src.leyline.business.domain.commons.platform.Platform;
import moe.src.leyline.business.domain.commons.platform.PlatformRepo;
import moe.src.leyline.framework.interfaces.rest.LeylineSimpleRestCRUD;

/**
 * Created by bytenoob on 6/26/16.
 */
@Component
public class CommonsAPI {
    @RestController
    @RequestMapping(value = "api/lang")
    public static final class lang extends LeylineSimpleRestCRUD<LangRepo,Lang>{};

    @RestController
    @RequestMapping(value = "api/location")
    public static final class location extends LeylineSimpleRestCRUD<LocationRepo,Location>{};

    @RestController
    @RequestMapping(value = "api/platform")
    public static final class platform extends LeylineSimpleRestCRUD<PlatformRepo,Platform>{};

}
