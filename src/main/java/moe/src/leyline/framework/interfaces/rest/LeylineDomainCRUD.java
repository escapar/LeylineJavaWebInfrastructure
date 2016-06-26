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

import moe.src.leyline.framework.domain.LeylineCacheableRepo;
import moe.src.leyline.framework.domain.LeylineDO;
import moe.src.leyline.framework.domain.user.LeylineUser;
import moe.src.leyline.framework.infrastructure.common.exceptions.PersistenceException;
import moe.src.leyline.framework.interfaces.dto.PageJSON;
import moe.src.leyline.framework.interfaces.view.LeylineView;
import moe.src.leyline.framework.service.LeylineSimpleService;
import moe.src.leyline.framework.service.LeylineUserDetailsService;

/**
 * 分页增删改查Restful API控制器抽象类,自动完成DO->DTO的Mapping
 * 包含了分页排序的基础功能
 * Mapping可以通过替换Assembler来自定义
 */
@EnableSpringDataWebSupport
@RestController
public class LeylineDomainCRUD<T extends LeylineCacheableRepo, O extends LeylineDO> implements CRUDOperation {
    private static final QuerydslBindingsFactory bindingsFactory = new QuerydslBindingsFactory(SimpleEntityPathResolver.INSTANCE);
    private static final QuerydslPredicateBuilder predicateBuilder = new QuerydslPredicateBuilder(new DefaultConversionService(), bindingsFactory.getEntityPathResolver());
    public final Logger logger = LoggerFactory.getLogger(getClass());
    private final Type typeService;
    private final Type typeDO;
    private final Class<T> classRepo;
    private final Class<O> classDO;
    private final JavaType typeDOList;
    @Autowired
    protected LeylineUserDetailsService userDetailsService;
    private ObjectMapper mapper = new ObjectMapper();
    public LeylineSimpleService<T,O> service;

    @SuppressWarnings(value = "unchecked")
    public LeylineDomainCRUD() {
        Class<?>[] typeArgs = TypeResolver.resolveRawArguments(LeylineDomainCRUD.class, getClass());
        classRepo = (Class<T>) typeArgs[0];
        classDO = (Class<O>) typeArgs[1];
        this.typeService = TypeToken.of(classRepo).getType();
        this.typeDO = TypeToken.of(classDO).getType();
        typeDOList = mapper.getTypeFactory().constructCollectionType(List.class, classDO);
        service = new LeylineSimpleService<>();
    }

    public LeylineDomainCRUD(Class<T> repo , Class<O> DO) {
        classRepo = repo;
        classDO = DO;
        this.typeService = TypeToken.of(classRepo).getType();
        this.typeDO = TypeToken.of(classDO).getType();
        typeDOList = mapper.getTypeFactory().constructCollectionType(List.class, classDO);
    }

    private Page doQueryDSL(Pageable p, MultiValueMap<String, String> parameters) throws PersistenceException {
        TypeInformation<O> domainType = ClassTypeInformation.from(classDO);
        QuerydslBindings bindings = bindingsFactory.createBindingsFor(null, domainType);

        Predicate predicate = predicateBuilder.getPredicate(domainType, parameters, bindings);

        return service.findAll(predicate, p);
    }

    @RequestMapping(value = "", method = RequestMethod.GET, produces = "application/json")
    @JsonView(LeylineView.LIST.class)
    @ResponseBody
    @SuppressWarnings(value = "unchecked")
    public PageJSON<O> list(Pageable p) throws PersistenceException {
        assertQuery(null);
        Page res = service.findAll(p);
        return new PageJSON<>(res, p);
    }

    @SuppressWarnings(value = "unchecked")
    @JsonView(LeylineView.LIST.class)
    @RequestMapping(value = "/list/query", method = RequestMethod.GET)
    public PageJSON<O> listWithQuery(
            Pageable p, @RequestParam MultiValueMap<String, String> parameters) throws PersistenceException, NoSuchMethodException {
        assertQuery(parameters);
        return new PageJSON<>(doQueryDSL(p, parameters), p);
    }

    @SuppressWarnings(value = "unchecked")
    @JsonView(LeylineView.DETAIL.class)
    @RequestMapping(value = "/query", method = RequestMethod.GET)
    public PageJSON<O> listWithDetail(
            Pageable p, @RequestParam MultiValueMap<String, String> parameters) throws PersistenceException, NoSuchMethodException {
        assertQuery(parameters);
        return new PageJSON<>(doQueryDSL(p, parameters), p);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET, produces = "application/json")
    @JsonView(LeylineView.DETAIL.class)
    @ResponseBody
    @SuppressWarnings(value = "unchecked")
    public O find(@PathVariable Long id) throws PersistenceException {
        assertQuery(null);
        return (O) service.findOne(id);
    }

    @RequestMapping(value = "/batch", method = RequestMethod.POST, produces = "application/json")
    @ResponseBody
    @SuppressWarnings(value = "unchecked")
    public void update(@RequestBody String json) throws IOException, PersistenceException {
        List<O> doList = mapper.readValue(json, typeDOList);
        assertUpdateBatch(doList);
        service.save(doList);
    }

    @RequestMapping(value = "", method = RequestMethod.POST, consumes = "application/json", produces = "application/json")
    @ResponseBody
    @SuppressWarnings(value = "unchecked")
    public void updateOne(@RequestBody O obj) throws IOException, PersistenceException, AssertionError {
        assertUpdate(obj);
        service.save(obj);
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
    public O insertOne(@RequestBody O obj) throws PersistenceException {
        assertUpdate(obj);
        return (O)service.save(obj);
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
        id.forEach(i -> {
            try {
                delete(i);
            } catch (PersistenceException e) {
                e.printStackTrace();
            }
        });
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

    public LeylineSimpleService<T, O> getService() {
        return service;
    }

    public LeylineDomainCRUD setService(final LeylineSimpleService<T, O> service) {
        this.service = service;
        return this;
    }
}

