package moe.src.leyline.framework.interfaces.vc;

import com.google.common.reflect.TypeToken;
import com.mysema.query.types.Predicate;
import net.masadora.mall.framework.domain.AppDO;
import net.masadora.mall.framework.infrastructure.common.exceptions.ApplicationException;
import net.masadora.mall.framework.infrastructure.common.exceptions.PersistenceException;
import net.masadora.mall.framework.interfaces.dto.AppDTO;
import net.masadora.mall.framework.interfaces.dto.assembler.DTOAssembler;
import net.masadora.mall.framework.service.TransactionalService;
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
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 分页增删改查后端渲染控制器抽象类,自动完成DO->DTO的Mapping
 * 包含了分页排序的基础功能
 * Mapping可以通过替换Assembler来自定义
 */
@Controller
public abstract class PageableController<S extends TransactionalService, DO extends AppDO,T extends AppDTO> {
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
    public PageableController() {
        typeArgs = TypeResolver.resolveRawArguments(PageableController.class, getClass());
        classDO = (Class<T>) typeArgs[1];
        classDTO = (Class<T>) typeArgs[2];
        this.typeDTO = TypeToken.of(classDTO).getType();
        this.typeDO = TypeToken.of(classDTO).getType();
        String[] nameOfDTO = typeDTO.getTypeName().split("\\.");
        modelName = nameOfDTO[nameOfDTO.length - 1].toLowerCase().replace("dto", "");
        setDtoAssembler(new DTOAssembler(typeDO,typeDTO));
    }

    /**
     * 用QueryDSL构建复杂JPA查询
     * @param p
     * @param parameters
     * @return
     * @throws PersistenceException
     */
    public Page doQueryDSL(Pageable p, MultiValueMap<String, String> parameters) throws PersistenceException {
        TypeInformation<T> domainType = ClassTypeInformation.from(classDTO);
        QuerydslBindings bindings = bindingsFactory.createBindingsFor(null, domainType);

        Predicate predicate = predicateBuilder.getPredicate(domainType, parameters, bindings);

        Page res = service.findAll(predicate, p);
        return dtoAssembler.buildPageDTO(res);
    }

    /**
     * 分页列出辅助方法
     * @param model
     * @param page
     * @return
     * @throws ApplicationException
     */
    public String list(Model model, Page page) throws ApplicationException {
        model.addAttribute("page",page);
        return modelName.concat("/list");
    }

    /**
     * 分页列出辅助方法
     * @param model
     * @param pageable
     * @return
     * @throws ApplicationException
     */
    public String list(Model model, Pageable pageable) throws ApplicationException {
        Page res = service.findAll(pageable);
        model.addAttribute("page", dtoAssembler.buildPageDTO(res));
        return modelName.concat("/list");
    }

    /**
     * 默认入口
     * @param model
     * @param page
     * @param direction
     * @param properties
     * @param pagesize
     * @return
     * @throws ApplicationException
     */
    @RequestMapping("")
    public String listDefault(Model model, @RequestParam(required = false) Integer page, @RequestParam(required = false) String direction, @RequestParam(required = false, defaultValue = "id") String properties, @RequestParam(required = false) Integer pagesize) throws ApplicationException {
        if (page == null || page < 0) {
            page = 0;
        }
        return list(model, page, direction, properties, pagesize);
    }

    /**
     * 全部显示带分页
     * @param model
     * @param page
     * @param direction
     * @param properties
     * @param pagesize
     * @return
     * @throws ApplicationException
     */
    @RequestMapping("/page/{page}")
    public String list(Model model, @PathVariable Integer page, @RequestParam(required = false) String direction, @RequestParam(required = false, defaultValue = "id") String properties, @RequestParam(required = false) Integer pagesize) throws ApplicationException {
        return list(model, getPageRequest(page,direction,properties,pagesize));
    }

    /**
     * 属性排序带分页
     * @param model
     * @param page
     * @param direction
     * @param property
     * @param pagesize
     * @return
     * @throws ApplicationException
     */
    @RequestMapping("sort/{property}/page/{page}")
    public String listByProperty(Model model, @PathVariable Integer page, @RequestParam(required = false) String direction, @PathVariable String property, @RequestParam(required = false) Integer pagesize) throws ApplicationException {
        return list(model, page, direction, property, pagesize);
    }

    /**
     * 属性排序+方向带分页
     * @param model
     * @param page
     * @param direction
     * @param property
     * @param pagesize
     * @return
     * @throws ApplicationException
     */
    @RequestMapping("sort/{property}/{direction}/page/{page}")
    public String listByPropertyAndDirection(Model model, @PathVariable Integer page, @PathVariable String direction, @PathVariable String property, @RequestParam(required = false) Integer pagesize) throws ApplicationException {
        return list(model, page, direction, property, pagesize);
    }

    /**
     * 以id为标识的详情页
     * @param model
     * @param id
     * @return
     * @throws ApplicationException
     */
    @RequestMapping("/detail_{id}")
    public String list(Model model, @PathVariable Long id) throws ApplicationException {
        model.addAttribute("res", dtoAssembler.buildDTO((DO)service.findOne(id)));
        return modelName.concat("/detail");
    }

    /**
     * 造PageRequest分页信息对象
     * @param page
     * @param direction
     * @param property
     * @param pagesize
     * @return
     * @throws ApplicationException
     */
    public PageRequest getPageRequest(Integer page,String direction, String property, Integer pagesize) throws ApplicationException {
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

    public List<Long> splitPropertIdsToList(String properties){
        return Arrays.asList(properties.split(",")).parallelStream().map(Long::valueOf).collect(Collectors.toList());
    }
}
