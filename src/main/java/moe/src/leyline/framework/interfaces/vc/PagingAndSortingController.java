package moe.src.leyline.framework.interfaces.vc;

import com.google.common.reflect.TypeToken;
import moe.src.leyline.framework.interfaces.dto.DTO;
import moe.src.leyline.framework.interfaces.dto.assembler.DTOAssembler;
import moe.src.leyline.framework.infrastructure.common.LeylineException;
import moe.src.leyline.framework.service.DomainService;
import org.jodah.typetools.TypeResolver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.lang.reflect.Type;

/**
 * Created by POJO on 6/7/16.
 */
@Controller
public abstract class PagingAndSortingController<S extends DomainService, T extends DTO> {
    @Autowired
    S service;
    private Class<?>[] typeArgs;
    private Type typeDTO;
    private Integer pagesize = 20;
    private String modelName;

    public PagingAndSortingController() {
        typeArgs = TypeResolver.resolveRawArguments(PagingAndSortingController.class, getClass());
        this.typeDTO = TypeToken.of(typeArgs[1]).getType();
        String[] nameOfDTO = typeDTO.getTypeName().split("\\.");
        modelName = nameOfDTO[nameOfDTO.length - 1].replace("DTO", "").toLowerCase();
    }

    @RequestMapping("")
    public String list(Model model, Pageable pageable) throws LeylineException {
        model.addAttribute("res", DTOAssembler.buildDTOList(service.findAll(pageable).getContent(), typeDTO));
        return modelName.concat("/list");
    }

    @RequestMapping("/list_{page}")
    public String list(Model model, @PathVariable Integer page, @RequestParam(required = false) String direction, @RequestParam(required = false) String properties) throws LeylineException {
        Sort.Direction d = Sort.Direction.DESC;
        Pageable pReal;
        if (direction.equals("ASC")) {
            d = Sort.Direction.ASC;
        }
        pReal = properties != null && !properties.isEmpty() && properties.contains(",") ?
                new PageRequest(page, pagesize, d, properties.split(",")) :
                new PageRequest(page, pagesize, d, properties);

        model.addAttribute("res", DTOAssembler.buildDTOList(service.findAll(pReal).getContent(), typeDTO));
        return modelName.concat("/list");
    }

    @RequestMapping("/list_{page}_{property}")
    public String listByProperty(Model model, @PathVariable Integer page, @RequestParam(required = false) String direction, @PathVariable String property) throws LeylineException {
        return list(model,page,direction,property);
    }

    @RequestMapping("/list_{page}_{property}_{direction}")
    public String listByPropertyAndDirection(Model model, @PathVariable Integer page,  @PathVariable String direction, @PathVariable String property) throws LeylineException {
        return listByProperty(model,page,direction,property);
    }

    @RequestMapping("/detail_{id}")
    public String list(Model model, @PathVariable Long id) throws LeylineException {
        model.addAttribute("res", DTOAssembler.buildDTO(service.findOne(id), typeDTO));
        return modelName.concat("/detail");
    }

    public Integer getPagesize() {
        return pagesize;
    }

    public void setPagesize(Integer pagesize) {
        this.pagesize = pagesize;
    }
}
