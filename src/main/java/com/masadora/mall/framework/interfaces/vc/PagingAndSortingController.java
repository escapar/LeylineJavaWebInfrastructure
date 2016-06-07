package com.masadora.mall.framework.interfaces.vc;

import com.google.common.reflect.TypeToken;
import com.masadora.mall.business.service.ProductService;
import com.masadora.mall.framework.interfaces.dto.DTO;
import com.masadora.mall.framework.interfaces.dto.assembler.DTOAssembler;
import com.masadora.mall.framework.interfaces.view.LeylineException;
import com.masadora.mall.framework.service.CRUDService;
import com.masadora.mall.interfaces.dto.ProductDTO;
import org.jodah.typetools.TypeResolver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.lang.reflect.Type;

/**
 * Created by POJO on 6/7/16.
 */
@Controller
public abstract class PagingAndSortingController<S extends CRUDService , T extends DTO> {
    private Class<?>[] typeArgs;
    private Type typeDTO;
    private Integer pagesize = 20;
    private String modelName;

    @Autowired
    S service;

    public PagingAndSortingController(){
        typeArgs = TypeResolver.resolveRawArguments(PagingAndSortingController.class,getClass());
        this.typeDTO = TypeToken.of(typeArgs[1]).getType();
        String[] nameOfDTO = typeDTO.getTypeName().split("\\.");
        modelName = nameOfDTO[nameOfDTO.length-1].replace("DTO","").toLowerCase();
    }

    @RequestMapping("")
    public String list(Model model, Pageable pageable) throws LeylineException {
        model.addAttribute("res", DTOAssembler.buildDTOList(service.findAll(pageable).getContent(),typeDTO));
        return modelName.concat("/list");
    }

    @RequestMapping("/list_{page}")
    public String list(Model model , @PathVariable Integer page, @RequestParam(required=false) String direction,@RequestParam(required=false) String properties) throws LeylineException{
        Sort.Direction d = Sort.Direction.DESC ;
        Pageable pReal;
        if(direction.equals("ASC")){
            d = Sort.Direction.ASC;
        }
        pReal = properties!=null && !properties.isEmpty() && properties.contains(",") ?
                    new PageRequest(page,pagesize,d,properties.split(",")) :
                    new PageRequest(page, pagesize, d, properties);

        model.addAttribute("res", DTOAssembler.buildDTOList(service.findAll(pReal).getContent(),typeDTO));
        return modelName.concat("/list");
    }

    @RequestMapping("/{id}")
    public String list(Model model, @PathVariable Long id) throws LeylineException {
        model.addAttribute("res", DTOAssembler.buildDTO(service.findOne(id),typeDTO));
        return modelName.concat("/detail");
    }

    public Integer getPagesize() {
        return pagesize;
    }

    public void setPagesize(Integer pagesize) {
        this.pagesize = pagesize;
    }
}
