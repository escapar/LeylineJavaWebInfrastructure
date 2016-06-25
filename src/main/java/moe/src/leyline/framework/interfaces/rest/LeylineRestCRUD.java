package moe.src.leyline.framework.interfaces.rest;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonView;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.reflect.TypeToken;
import com.querydsl.core.types.Predicate;

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
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import moe.src.leyline.framework.domain.LeylineDO;
import moe.src.leyline.framework.domain.user.LeylineUser;
import moe.src.leyline.framework.infrastructure.common.exceptions.PersistenceException;
import moe.src.leyline.framework.interfaces.dto.LeylineDTO;
import moe.src.leyline.framework.interfaces.dto.PageJSON;
import moe.src.leyline.framework.interfaces.dto.assembler.DTOAssembler;
import moe.src.leyline.framework.interfaces.view.LeylineView;
import moe.src.leyline.framework.service.LeylineTransactionalService;
import moe.src.leyline.framework.service.LeylineUserDetailsService;

/**
 * 分页增删改查Restful API控制器抽象类,自动完成DO->DTO的Mapping
 * 包含了分页排序的基础功能
 * Mapping可以通过替换Assembler来自定义
 */
@EnableSpringDataWebSupport
@RestController
public abstract class LeylineRestCRUD<T extends LeylineTransactionalService, O extends LeylineDO, D extends LeylineDTO> implements CRUDOperation {
    private static final QuerydslBindingsFactory bindingsFactory = new QuerydslBindingsFactory(SimpleEntityPathResolver.INSTANCE);
    private static final QuerydslPredicateBuilder predicateBuilder = new QuerydslPredicateBuilder(new DefaultConversionService(), bindingsFactory.getEntityPathResolver());
    public final Logger logger = LoggerFactory.getLogger(getClass());
    private final Class<?>[] typeArgs;
    private final Type typeService;
    private final Type typeDTO;
    private final Type typeDO;
    private final Class<T> classService;
    private final Class<D> classDTO;
    private final Class<O> classDO;
    private final JavaType typeDTOList;
    private final JavaType typeDOList;
    public DTOAssembler<O, D> dtoAssembler;
    @Autowired
    protected T service;
    @Autowired
    protected LeylineUserDetailsService userDetailsService;
    private ObjectMapper mapper = new ObjectMapper();

    @SuppressWarnings(value = "unchecked")
    public LeylineRestCRUD() {
        typeArgs = TypeResolver.resolveRawArguments(LeylineRestCRUD.class, getClass());
        classService = (Class<T>) typeArgs[0];
        classDTO = (Class<D>) typeArgs[2];
        classDO = (Class<O>) typeArgs[1];
        this.typeService = TypeToken.of(classService).getType();
        this.typeDTO = TypeToken.of(classDTO).getType();
        this.typeDO = TypeToken.of(classDO).getType();
        typeDTOList = mapper.getTypeFactory().constructCollectionType(List.class, classDTO);
        typeDOList = mapper.getTypeFactory().constructCollectionType(List.class, classDO);
        setDTOAssembler(new DTOAssembler<>(typeDO, typeDTO));
    }

    private Page doQueryDSL(Pageable p, MultiValueMap<String, String> parameters) throws PersistenceException {
        TypeInformation<O> domainType = ClassTypeInformation.from(classDO);
        QuerydslBindings bindings = bindingsFactory.createBindingsFor(null, domainType);

        Predicate predicate = predicateBuilder.getPredicate(domainType, parameters, bindings);

        Page res = service.findAll(predicate, p);
        return dtoAssembler.buildPageDTO(res);
    }

    @RequestMapping(value = "", method = RequestMethod.GET, produces = "application/json")
    @JsonView(LeylineView.LIST.class)
    @ResponseBody
    @SuppressWarnings(value = "unchecked")
    public PageJSON<D> list(Pageable p) throws PersistenceException {
        assertQuery(null);
        Page res = service.findAll(p);
        res = dtoAssembler.buildPageDTO(res);
        return new PageJSON<>(res, p);
    }

    @SuppressWarnings(value = "unchecked")
    @JsonView(LeylineView.LIST.class)
    @RequestMapping(value = "/list/query", method = RequestMethod.GET)
    public PageJSON<D> listWithQuery(
            Pageable p, @RequestParam MultiValueMap<String, String> parameters) throws PersistenceException, NoSuchMethodException {
        assertQuery(parameters);
        return new PageJSON<>(doQueryDSL(p, parameters), p);
    }

    @SuppressWarnings(value = "unchecked")
    @JsonView(LeylineView.DETAIL.class)
    @RequestMapping(value = "/query", method = RequestMethod.GET)
    public PageJSON<D> listWithDetail(
            Pageable p, @RequestParam MultiValueMap<String, String> parameters) throws PersistenceException, NoSuchMethodException {
        assertQuery(parameters);
        return new PageJSON<>(doQueryDSL(p, parameters), p);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET, produces = "application/json")
    @JsonView(LeylineView.DETAIL.class)
    @ResponseBody
    @SuppressWarnings(value = "unchecked")
    public D find(@PathVariable Long id) throws PersistenceException {
        assertQuery(null);
        return dtoAssembler.buildDTO((O) service.findOne(id));
    }

    @RequestMapping(value = "/batch", method = RequestMethod.POST, produces = "application/json")
    @ResponseBody
    @SuppressWarnings(value = "unchecked")
    public void update(@RequestBody String json) throws IOException, PersistenceException {
        List<O> doList = dtoAssembler.buildDOList(mapper.readValue(json, typeDTOList));
        assertUpdateBatch(doList);
        service.save(doList);
    }

    @RequestMapping(value = "", method = RequestMethod.POST, consumes = "application/json", produces = "application/json")
    @ResponseBody
    @SuppressWarnings(value = "unchecked")
    public void updateOne(@RequestBody D obj) throws IOException, PersistenceException, AssertionError {
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

    public LeylineUser getCurrentUser() {
        return userDetailsService.getCurrentUser();
    }

    public void setDTOAssembler(DTOAssembler<O, D> dtoAssembler) {
        this.dtoAssembler = dtoAssembler;
    }

    /**
     * 增删改查检查预留方法 可以在implementation里override
     *
     * @param o
     * @return
     */
    public void assertQuery(Object o) {

    }

    public void assertUpdateBatch(List<O> o) {
        o.forEach(this::assertUpdate);
    }

    public void assertUpdate(O o) {

    }

    public void assertDelete() {

    }
}

