package moe.src.leyline.framework.interfaces.vc;

import com.google.common.reflect.TypeToken;
import moe.src.leyline.framework.interfaces.dto.DTO;
import moe.src.leyline.framework.interfaces.dto.assembler.DTOAssembler;
import moe.src.leyline.framework.infrastructure.common.exceptions.LeylineException;
import moe.src.leyline.framework.service.DomainService;

import org.jodah.typetools.TypeResolver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
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

    public String list(Model model, Pageable pageable) throws LeylineException {
        Page res = service.findAll(pageable);
        model.addAttribute("page",DTOAssembler.buildPageDTO(res,typeDTO));
        return modelName.concat("/list");
    }

    @RequestMapping("")
    public String listDefault(Model model,@RequestParam(required = false) Integer page,@RequestParam(required = false) String direction, @RequestParam(required = false,defaultValue = "id") String properties, @RequestParam(required = false) Integer pagesize) throws LeylineException {
        if(page==null || page < 0){
            page = 0;
        }
        return list(model,page,direction,properties,pagesize);
    }

    @RequestMapping("/page/{page}")
    public String list(Model model, @PathVariable Integer page, @RequestParam(required = false) String direction, @RequestParam(required = false,defaultValue = "id") String properties, @RequestParam(required = false) Integer pagesize) throws LeylineException {
        Sort.Direction d = Sort.Direction.DESC;
        Pageable pReal;
        if (direction!=null && !direction.isEmpty() && direction.toUpperCase().equals("ASC")) {
            d = Sort.Direction.ASC;
        }
        if(pagesize == null || pagesize <=0){
            pagesize = this.pagesize;
        }
        pReal = new PageRequest(page, pagesize, d, properties.split(","));
        return list(model,pReal);
    }

    @RequestMapping("sort/{property}/page/{page}")
    public String listByProperty(Model model, @PathVariable Integer page, @RequestParam(required = false) String direction, @PathVariable String property,@RequestParam(required = false) Integer pagesize) throws LeylineException {
        return list(model,page,direction,property,pagesize);
    }

    @RequestMapping("sort/{property}/{direction}/page/{page}")
    public String listByPropertyAndDirection(Model model, @PathVariable Integer page,  @PathVariable String direction, @PathVariable String property,@RequestParam(required = false) Integer pagesize) throws LeylineException {
        return listByProperty(model,page,direction,property,pagesize);
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
