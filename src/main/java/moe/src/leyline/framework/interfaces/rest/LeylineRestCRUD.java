package moe.src.leyline.framework.interfaces.rest;

import com.fasterxml.jackson.annotation.JsonView;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.reflect.TypeToken;
import moe.src.leyline.framework.domain.LeylineDO;
import moe.src.leyline.framework.infrastructure.common.exceptions.PersistenceException;
import moe.src.leyline.framework.interfaces.dto.LeylineDTO;
import moe.src.leyline.framework.interfaces.dto.PageJSON;
import moe.src.leyline.framework.interfaces.dto.assembler.DTOAssembler;
import moe.src.leyline.framework.interfaces.view.LeylineView;
import moe.src.leyline.framework.service.LeylineDomainService;
import org.jodah.typetools.TypeResolver;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.NameTokenizers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.config.EnableSpringDataWebSupport;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by POJO on 5/30/16.
 */
@Component
@EnableSpringDataWebSupport
public abstract class LeylineRestCRUD<T extends LeylineDomainService, D extends LeylineDTO, O extends LeylineDO> implements LeylineCRUD {
    private static final ObjectMapper mapper = new ObjectMapper();
    private static final DTOAssembler assembler = new DTOAssembler();
    @Autowired
    protected T service;
    private Class<?>[] typeArgs;
    private Type typeService;
    private Type typeDTO;
    private Type typeDO;
    private JavaType typeDTOList;
    private JavaType typeDOList;

    public LeylineRestCRUD() {
        typeArgs = TypeResolver.resolveRawArguments(LeylineRestCRUD.class, getClass());
        this.typeService = TypeToken.of(typeArgs[0]).getType();
        this.typeDTO = TypeToken.of(typeArgs[1]).getType();
        this.typeDO = TypeToken.of(typeArgs[2]).getType();
        typeDTOList = mapper.getTypeFactory().constructCollectionType(List.class, typeArgs[1]);
        typeDOList = mapper.getTypeFactory().constructCollectionType(List.class, typeArgs[2]);

    }

    @RequestMapping(value = "", method = RequestMethod.GET, produces = "application/json")
    @JsonView(LeylineView.LIST.class)
    @ResponseBody
    @SuppressWarnings(value = "unchecked")
    public PageJSON<D> list(Pageable p) throws PersistenceException {
        Page res = service.findAll(p);
        res = assembler.buildPageDTO(res,typeDTO);
        return new PageJSON<>(res,p);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET, produces = "application/json")
    @JsonView(LeylineView.DETAIL.class)
    @ResponseBody
    @SuppressWarnings(value = "unchecked")
    public D find(@PathVariable Long id) throws PersistenceException {
        return (D) assembler.buildDTO(service.findOne(id), typeDTO);
    }


    @RequestMapping(value = "", method = RequestMethod.POST, produces = "application/json")
    @ResponseBody
    @SuppressWarnings(value = "unchecked")
    public void update(@RequestBody String json) throws IOException, PersistenceException {
        service.save(assembler.buildDOList(mapper.readValue(json, typeDTOList), typeDO));
    }

    @RequestMapping(value = "/one", method = RequestMethod.POST, produces = "application/json")
    @ResponseBody
    @SuppressWarnings(value = "unchecked")
    public void updateOne(@RequestBody D obj) throws IOException, PersistenceException {
        service.save(assembler.buildDO(obj, typeDO));
    }

    @RequestMapping(value = "", method = RequestMethod.PUT, produces = "application/json")
    @ResponseBody
    @SuppressWarnings(value = "unchecked")
    public void insert(@RequestBody String json) throws IOException, PersistenceException {
        service.save(assembler.buildDOList(mapper.readValue(json, typeDTOList), typeDO));
    }

    @RequestMapping(value = "/one", method = RequestMethod.PUT, produces = "application/json")
    @ResponseBody
    @SuppressWarnings(value = "unchecked")
    public Object insertOne(@RequestBody D obj) throws PersistenceException {
        ModelMapper mm = new ModelMapper();
        mm.getConfiguration().setSourceNameTokenizer(NameTokenizers.UNDERSCORE);
        return mm.map(service.save(assembler.buildDO(obj, typeDO)), typeDO);
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

}

