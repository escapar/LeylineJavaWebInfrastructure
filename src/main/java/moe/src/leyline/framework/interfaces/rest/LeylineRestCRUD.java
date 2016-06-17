package moe.src.leyline.framework.interfaces.rest;

import com.fasterxml.jackson.annotation.JsonView;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.reflect.TypeToken;
import com.mysema.query.types.Predicate;
import moe.src.leyline.framework.domain.LeylineDO;
import moe.src.leyline.framework.infrastructure.common.exceptions.PersistenceException;
import moe.src.leyline.framework.interfaces.dto.LeylineDTO;
import moe.src.leyline.framework.interfaces.dto.PageJSON;
import moe.src.leyline.framework.interfaces.dto.assembler.DTOAssembler;
import moe.src.leyline.framework.interfaces.view.LeylineView;
import moe.src.leyline.framework.service.LeylineDomainService;
import moe.src.leyline.framework.service.LeylineUserDetailsService;
import org.jodah.typetools.TypeResolver;
import org.modelmapper.ModelMapper;

import org.modelmapper.convention.NameTokenizers;
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
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.List;

/**
 * Created by POJO on 5/30/16.
 */
@Component
@EnableSpringDataWebSupport
public abstract class LeylineRestCRUD<T extends LeylineDomainService, D extends LeylineDTO, O extends LeylineDO> implements LeylineCRUD {
    private static final ObjectMapper mapper = new ObjectMapper();

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
    protected LeylineUserDetailsService userDetailsService;

    @SuppressWarnings(value = "unchecked")
    public LeylineRestCRUD() {
        typeArgs = TypeResolver.resolveRawArguments(LeylineRestCRUD.class, getClass());
        classService = (Class<T>) typeArgs[0];
        classDTO = (Class<D>) typeArgs[1];
        classDO = (Class<O>) typeArgs[2];
        this.typeService = TypeToken.of(classService).getType();
        this.typeDTO = TypeToken.of(classDTO).getType();
        this.typeDO = TypeToken.of(classDO).getType();
        typeDTOList = mapper.getTypeFactory().constructCollectionType(List.class, classDTO);
        typeDOList = mapper.getTypeFactory().constructCollectionType(List.class, classDO);
    }

    private Page doQueryDSL(Pageable p, MultiValueMap<String, String> parameters) throws PersistenceException {
        TypeInformation<O> domainType = ClassTypeInformation.from(classDO);
        QuerydslBindings bindings = bindingsFactory.createBindingsFor(null, domainType);

        Predicate predicate = predicateBuilder.getPredicate(domainType, parameters, bindings);

        Page res = service.findAll(predicate, p);
        return DTOAssembler.buildPageDTO(res, typeDTO);
    }

    @RequestMapping(value = "", method = RequestMethod.GET, produces = "application/json")
    @JsonView(LeylineView.LIST.class)
    @ResponseBody
    @SuppressWarnings(value = "unchecked")
    public PageJSON<D> list(Pageable p) throws PersistenceException {
        Page res = service.findAll(p);
        res = DTOAssembler.buildPageDTO(res, typeDTO);
        return new PageJSON<>(res, p);
    }

    @SuppressWarnings(value = "unchecked")
    @JsonView(LeylineView.LIST.class)
    @RequestMapping(value = "/list/query", method = RequestMethod.GET)
    public PageJSON<D> listWithQuery(
            Pageable p, @RequestParam MultiValueMap<String, String> parameters) throws PersistenceException, NoSuchMethodException {
        return new PageJSON<>(doQueryDSL(p, parameters), p);
    }

    @SuppressWarnings(value = "unchecked")
    @JsonView(LeylineView.DETAIL.class)
    @RequestMapping(value = "/query", method = RequestMethod.GET)
    public PageJSON<D> listWithDetail(
            Pageable p, @RequestParam MultiValueMap<String, String> parameters) throws PersistenceException, NoSuchMethodException {
        return new PageJSON<>(doQueryDSL(p, parameters), p);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET, produces = "application/json")
    @JsonView(LeylineView.DETAIL.class)
    @ResponseBody
    @SuppressWarnings(value = "unchecked")
    public D find(@PathVariable Long id) throws PersistenceException {
        return (D) DTOAssembler.buildDTO(service.findOne(id), typeDTO);
    }


    @RequestMapping(value = "", method = RequestMethod.POST, produces = "application/json")
    @ResponseBody
    @SuppressWarnings(value = "unchecked")
    public void update(@RequestBody String json) throws IOException, PersistenceException {
        service.save(DTOAssembler.buildDOList(mapper.readValue(json, typeDTOList), typeDO));
    }

    @RequestMapping(value = "/one", method = RequestMethod.POST, produces = "application/json")
    @ResponseBody
    @SuppressWarnings(value = "unchecked")
    public void updateOne(@RequestBody D obj) throws IOException, PersistenceException {
        service.save(DTOAssembler.buildDO(obj, typeDO));
    }

    @RequestMapping(value = "", method = RequestMethod.PUT, produces = "application/json")
    @ResponseBody
    @SuppressWarnings(value = "unchecked")
    public void insert(@RequestBody String json) throws IOException, PersistenceException {
        service.save(DTOAssembler.buildDOList(mapper.readValue(json, typeDTOList), typeDO));
    }

    @RequestMapping(value = "/one", method = RequestMethod.PUT, produces = "application/json")
    @ResponseBody
    @SuppressWarnings(value = "unchecked")
    public D insertOne(@RequestBody D obj) throws PersistenceException {
        ModelMapper mm = new ModelMapper();
        mm.getConfiguration().setSourceNameTokenizer(NameTokenizers.UNDERSCORE);
        return mm.map(service.save(DTOAssembler.buildDO(obj, typeDO)), typeDTO);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE, produces = "application/json")
    @ResponseBody
    @SuppressWarnings(value = "unchecked")
    public void delete(@PathVariable Long id) throws PersistenceException {
        service.delete(id);
    }

    @RequestMapping(value = "", method = RequestMethod.DELETE, produces = "application/json")
    @ResponseBody
    @SuppressWarnings(value = "unchecked")
    public void delete(@RequestBody List<Long> id) throws PersistenceException {
        service.delete(id);
    }

    public User getCurrentUser() {
        return userDetailsService.getCurrentUser();
    }
}

