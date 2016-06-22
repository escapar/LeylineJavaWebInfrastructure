package net.masadora.mall.framework.interfaces.vc;

import com.google.common.reflect.TypeToken;
import com.mysema.query.types.Predicate;
import net.masadora.mall.framework.domain.LeylineDO;
import net.masadora.mall.framework.infrastructure.common.exceptions.LeylineException;
import net.masadora.mall.framework.infrastructure.common.exceptions.PersistenceException;
import net.masadora.mall.framework.interfaces.dto.LeylineDTO;
import net.masadora.mall.framework.interfaces.dto.assembler.DTOAssembler;
import net.masadora.mall.framework.service.LeylineDomainService;
import org.jodah.typetools.TypeResolver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.support.DefaultConversionService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.querydsl.SimpleEntityPathResolver;
import org.springframework.data.querydsl.binding.QuerydslBindings;
import org.springframework.data.querydsl.binding.QuerydslBindingsFactory;
import org.springframework.data.querydsl.binding.QuerydslPredicateBuilder;
import org.springframework.data.util.ClassTypeInformation;
import org.springframework.data.util.TypeInformation;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.lang.reflect.Type;

/**
 * Created by POJO on 6/7/16.
 */
@Controller
public abstract class LeylinePageableController<S extends LeylineDomainService, DO extends LeylineDO,T extends LeylineDTO> {
    private static final QuerydslBindingsFactory bindingsFactory = new QuerydslBindingsFactory(SimpleEntityPathResolver.INSTANCE);
    private static final QuerydslPredicateBuilder predicateBuilder = new QuerydslPredicateBuilder(new DefaultConversionService(), bindingsFactory.getEntityPathResolver());
    @Autowired
    public S service;
    private Class<?>[] typeArgs;
    private Class<T> classDO;
    private Class<T> classDTO;
    private Type typeDO;
    private Type typeDTO;
    private Integer pagesize = 20;
    private String modelName;
    public DTOAssembler<DO,T> dtoAssembler;

    @SuppressWarnings(value = "unchecked")
    public LeylinePageableController() {
        typeArgs = TypeResolver.resolveRawArguments(LeylinePageableController.class, getClass());
        classDO = (Class<T>) typeArgs[1];
        classDTO = (Class<T>) typeArgs[2];
        this.typeDTO = TypeToken.of(classDTO).getType();
        this.typeDO = TypeToken.of(classDTO).getType();
        String[] nameOfDTO = typeDTO.getTypeName().split("\\.");
        modelName = nameOfDTO[nameOfDTO.length - 1].toLowerCase().replace("dto", "");
        setDtoAssembler(new DTOAssembler(typeDO,typeDTO));
    }

    public Page doQueryDSL(Pageable p, MultiValueMap<String, String> parameters) throws PersistenceException {
        TypeInformation<T> domainType = ClassTypeInformation.from(classDTO);
        QuerydslBindings bindings = bindingsFactory.createBindingsFor(null, domainType);

        Predicate predicate = predicateBuilder.getPredicate(domainType, parameters, bindings);

        Page res = service.findAll(predicate, p);
        return dtoAssembler.buildPageDTO(res);
    }

    public String list(Model model, Page page) throws LeylineException {
        model.addAttribute("page",page);
        return modelName.concat("/list");
    }

    public String list(Model model, Pageable pageable) throws LeylineException {
        Page res = service.findAll(pageable);
        model.addAttribute("page", dtoAssembler.buildPageDTO(res));
        return modelName.concat("/list");
    }

    @RequestMapping("")
    public String listDefault(Model model, @RequestParam(required = false) Integer page, @RequestParam(required = false) String direction, @RequestParam(required = false, defaultValue = "id") String properties, @RequestParam(required = false) Integer pagesize) throws LeylineException {
        if (page == null || page < 0) {
            page = 0;
        }
        return list(model, page, direction, properties, pagesize);
    }

    @RequestMapping("/page/{page}")
    public String list(Model model, @PathVariable Integer page, @RequestParam(required = false) String direction, @RequestParam(required = false, defaultValue = "id") String properties, @RequestParam(required = false) Integer pagesize) throws LeylineException {
        return list(model, getPageRequest(page,direction,properties,pagesize));
    }

    @RequestMapping("sort/{property}/page/{page}")
    public String listByProperty(Model model, @PathVariable Integer page, @RequestParam(required = false) String direction, @PathVariable String property, @RequestParam(required = false) Integer pagesize) throws LeylineException {
        return list(model, page, direction, property, pagesize);
    }

    @RequestMapping("sort/{property}/{direction}/page/{page}")
    public String listByPropertyAndDirection(Model model, @PathVariable Integer page, @PathVariable String direction, @PathVariable String property, @RequestParam(required = false) Integer pagesize) throws LeylineException {
        return list(model, page, direction, property, pagesize);
    }

    @RequestMapping("/detail_{id}")
    public String list(Model model, @PathVariable Long id) throws LeylineException {
        model.addAttribute("res", dtoAssembler.buildDTO((DO)service.findOne(id)));
        return modelName.concat("/detail");
    }

    public PageRequest getPageRequest(Integer page,String direction, String property, Integer pagesize) throws LeylineException {
        Sort.Direction d = Sort.Direction.DESC;
        if (direction != null && !direction.isEmpty() && direction.toUpperCase().equals("ASC")) {
            d = Sort.Direction.ASC;
        }
        if(pagesize == null){
            pagesize = getPagesize();
        }
        return new PageRequest(page, pagesize, d, property.split(","));
    }

    public Integer getPagesize() {
        return pagesize;
    }

    public void setPagesize(Integer pagesize) {
        this.pagesize = pagesize;
    }

    public DTOAssembler getDtoAssembler() {
        return dtoAssembler;
    }

    public void setDtoAssembler(DTOAssembler dtoAssembler) {
        this.dtoAssembler = dtoAssembler;
    }
}
