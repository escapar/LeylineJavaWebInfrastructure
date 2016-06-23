package moe.src.leyline.framework.interfaces.rest;

import com.fasterxml.jackson.annotation.JsonView;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.reflect.TypeToken;
import com.mysema.query.types.Predicate;
import moe.src.leyline.framework.interfaces.view.AppView;
import net.masadora.mall.framework.domain.AppDO;
import net.masadora.mall.framework.infrastructure.common.exceptions.PersistenceException;
import net.masadora.mall.framework.interfaces.dto.AppDTO;
import net.masadora.mall.framework.interfaces.dto.PageJSON;
import net.masadora.mall.framework.interfaces.dto.assembler.DTOAssembler;
import net.masadora.mall.framework.service.MasadoraUserDetailsService;
import net.masadora.mall.framework.service.TransactionalService;
import org.jodah.typetools.TypeResolver;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.NameTokenizers;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.support.DefaultConversionService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.querydsl.SimpleEntityPathResolver;
import org.springframework.data.querydsl.binding.QuerydslBindings;
import org.springframework.data.querydsl.binding.QuerydslBindingsFactory;
import org.springframework.data.querydsl.binding.QuerydslPredicateBuilder;
import org.springframework.data.util.ClassTypeInformation;
import org.springframework.data.util.TypeInformation;
import org.springframework.data.web.config.EnableSpringDataWebSupport;
import org.springframework.security.core.userdetails.User;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.List;

/**
 * 分页增删改查Restful API控制器抽象类,自动完成DO->DTO的Mapping
 * 包含了分页排序的基础功能
 * Mapping可以通过替换Assembler来自定义
 */
@EnableSpringDataWebSupport
@RestController
public abstract class RestCRUD<T extends TransactionalService, O extends AppDO, D extends AppDTO> implements CRUDOperation {
    public final Logger logger = LoggerFactory.getLogger(getClass());
    private ObjectMapper mapper = new ObjectMapper();
    public DTOAssembler<O,D> dtoAssembler;
    private static final QuerydslBindingsFactory bindingsFactory = new QuerydslBindingsFactory(SimpleEntityPathResolver.INSTANCE);
    private static final QuerydslPredicateBuilder predicateBuilder = new QuerydslPredicateBuilder(new DefaultConversionService(), bindingsFactory.getEntityPathResolver());
    private final Class<?>[] typeArgs;
    private final Type typeService;
    private final Type typeDTO;
    private final Type typeDO;
    private final Class<T> classService;
    private final Class<D> classDTO;
    private final Class<O> classDO;
    private final JavaType typeDTOList;
    private final JavaType typeDOList;
    @Autowired
    protected T service;

    @Autowired
    protected MasadoraUserDetailsService userDetailsService;

    @SuppressWarnings(value = "unchecked")
    public RestCRUD() {
        typeArgs = TypeResolver.resolveRawArguments(RestCRUD.class, getClass());
        classService = (Class<T>) typeArgs[0];
        classDTO = (Class<D>) typeArgs[2];
        classDO = (Class<O>) typeArgs[1];
        this.typeService = TypeToken.of(classService).getType();
        this.typeDTO = TypeToken.of(classDTO).getType();
        this.typeDO = TypeToken.of(classDO).getType();
        typeDTOList = mapper.getTypeFactory().constructCollectionType(List.class, classDTO);
        typeDOList = mapper.getTypeFactory().constructCollectionType(List.class, classDO);
        setDTOAssembler(new DTOAssembler<>(typeDO,typeDTO));
    }

    private Page doQueryDSL(Pageable p, MultiValueMap<String, String> parameters) throws PersistenceException {
        TypeInformation<O> domainType = ClassTypeInformation.from(classDO);
        QuerydslBindings bindings = bindingsFactory.createBindingsFor(null, domainType);

        Predicate predicate = predicateBuilder.getPredicate(domainType, parameters, bindings);

        Page res = service.findAll(predicate, p);
        return dtoAssembler.buildPageDTO(res);
    }

    @RequestMapping(value = "", method = RequestMethod.GET, produces = "application/json")
    @JsonView(AppView.LIST.class)
    @ResponseBody
    @SuppressWarnings(value = "unchecked")
    public PageJSON<D> list(Pageable p) throws PersistenceException {
        assertQuery(null);
        Page res = service.findAll(p);
        res = dtoAssembler.buildPageDTO(res);
        return new PageJSON<>(res, p);
    }

    @SuppressWarnings(value = "unchecked")
    @JsonView(AppView.LIST.class)
    @RequestMapping(value = "/list/query", method = RequestMethod.GET)
    public PageJSON<D> listWithQuery(
            Pageable p, @RequestParam MultiValueMap<String, String> parameters) throws PersistenceException, NoSuchMethodException {
        assertQuery(parameters);
        return new PageJSON<>(doQueryDSL(p, parameters), p);
    }

    @SuppressWarnings(value = "unchecked")
    @JsonView(AppView.DETAIL.class)
    @RequestMapping(value = "/query", method = RequestMethod.GET)
    public PageJSON<D> listWithDetail(
            Pageable p, @RequestParam MultiValueMap<String, String> parameters) throws PersistenceException, NoSuchMethodException {
        assertQuery(parameters);
        return new PageJSON<>(doQueryDSL(p, parameters), p);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET, produces = "application/json")
    @JsonView(AppView.DETAIL.class)
    @ResponseBody
    @SuppressWarnings(value = "unchecked")
    public D find(@PathVariable Long id) throws PersistenceException {
        assertQuery(null);
        return  dtoAssembler.buildDTO((O)service.findOne(id));
    }


    @RequestMapping(value = "/batch", method = RequestMethod.POST, produces = "application/json")
    @ResponseBody
    @SuppressWarnings(value = "unchecked")
    public void update(@RequestBody String json) throws IOException, PersistenceException {
        List<O> doList = dtoAssembler.buildDOList(mapper.readValue(json, typeDTOList));
        assertUpdateBatch(doList);
        service.save(doList);
    }

    @RequestMapping(value = "", method = RequestMethod.POST, consumes = "application/json",produces = "application/json")
    @ResponseBody
    @SuppressWarnings(value = "unchecked")
    public void updateOne(@RequestBody D obj) throws IOException, PersistenceException,AssertionError {
        O objDO = dtoAssembler.buildDO(obj);
        assertUpdate(objDO);
        service.save(dtoAssembler.buildDO(obj));
    }

    @RequestMapping(value = "/batch", method = RequestMethod.PUT, produces = "application/json")
    @ResponseBody
    @SuppressWarnings(value = "unchecked")
    public void insert(@RequestBody String json) throws IOException, PersistenceException {
        update(json);
    }

    @RequestMapping(value = "", method = RequestMethod.PUT, produces = "application/json")
    @ResponseBody
    @SuppressWarnings(value = "unchecked")
    public D insertOne(@RequestBody D obj) throws PersistenceException {
        O objDO = dtoAssembler.buildDO(obj);
        assertUpdate(objDO);
        ModelMapper mm = new ModelMapper();
        mm.getConfiguration().setSourceNameTokenizer(NameTokenizers.UNDERSCORE);
        return mm.map(service.save(objDO), typeDTO);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE, produces = "application/json")
    @ResponseBody
    @SuppressWarnings(value = "unchecked")
    public void delete(@PathVariable Long id) throws PersistenceException {
        assertDelete();
        service.delete(id);
    }

    @RequestMapping(value = "", method = RequestMethod.DELETE, produces = "application/json")
    @ResponseBody
    @SuppressWarnings(value = "unchecked")
    public void delete(@RequestBody List<Long> id) throws PersistenceException {
        assertDelete();
        service.delete(id);
    }

    @RequestMapping(value = "/batch", method = RequestMethod.DELETE, produces = "application/json")
    @ResponseBody
    @SuppressWarnings(value = "unchecked")
    public void deleteBatch(@RequestBody List<Long> id) throws PersistenceException {
        assertDelete();
        delete(id);
    }

    public User getCurrentUser() {
        return userDetailsService.getCurrentUser();
    }

    public void setDTOAssembler(DTOAssembler<O,D> dtoAssembler) {
        this.dtoAssembler = dtoAssembler;
    }

    /**
     * 增删改查检查预留方法 可以在implementation里override
     * @param o
     * @return
     */
    public void assertQuery(Object o){

    }

    public void assertUpdateBatch(List<O> o){
        o.forEach(this::assertUpdate);
    }

    public void assertUpdate(O o){

    }

    public void assertDelete(){

    }
}

