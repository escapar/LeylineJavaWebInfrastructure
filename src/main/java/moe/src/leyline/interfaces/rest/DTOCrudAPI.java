package moe.src.leyline.interfaces.rest;

import com.fasterxml.jackson.annotation.JsonView;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.reflect.TypeToken;
import moe.src.leyline.domain.DO;
import moe.src.leyline.infrastructure.persistence.PersistenceException;
import moe.src.leyline.interfaces.dto.DTO;
import moe.src.leyline.interfaces.dto.DTOAssembler;
import moe.src.leyline.interfaces.view.View;
import moe.src.leyline.service.LeylineService;
import org.jodah.typetools.TypeResolver;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.NameTokenizers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by POJO on 5/30/16.
 */
public abstract class DTOCrudAPI<T extends LeylineService,D extends DTO,O extends DO> implements CrudAPI{
    @Autowired
    protected T service;
    private Class<?>[] typeArgs;
    private Type typeService;
    private Type typeDTO;
    private Type typeDO;
    private JavaType typeDTOList;
    private JavaType typeDOList;

    private static final ObjectMapper mapper = new ObjectMapper();
    private static final DTOAssembler assembler = new DTOAssembler();

    public DTOCrudAPI(){
        typeArgs = TypeResolver.resolveRawArguments(DTOCrudAPI.class,getClass());
        this.typeService = TypeToken.of(typeArgs[0]).getType();
        this.typeDTO = TypeToken.of(typeArgs[1]).getType();
        this.typeDO  = TypeToken.of(typeArgs[2]).getType();
        typeDTOList = mapper.getTypeFactory().constructCollectionType(List.class,typeArgs[1]);
        typeDOList = mapper.getTypeFactory().constructCollectionType(List.class,typeArgs[2]);

    }

    @RequestMapping(value = "", method = RequestMethod.GET, produces = "application/json")
    @JsonView(View.LIST.class)
    @ResponseBody
    @SuppressWarnings(value="unchecked")
    public List list() throws PersistenceException{
        return (List) service.findAll()
                .stream()
                .map( e -> assembler.buildDTO((DO)e,typeDTO))
                .collect(Collectors.toList());
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET, produces = "application/json")
    @JsonView(View.DETAIL.class)
    @ResponseBody
    @SuppressWarnings(value="unchecked")
    public D find(@PathVariable Long id) throws PersistenceException{
        return (D)assembler.buildDTO(service.findOne(id),typeDTO);
    }


    @RequestMapping(value = "", method = RequestMethod.POST, produces = "application/json")
    @ResponseBody
    @SuppressWarnings(value="unchecked")
    public void update(@RequestBody String json) throws IOException,PersistenceException{
        service.save(assembler.buildDOList(mapper.readValue(json,typeDTOList),typeDO));
    }

    @RequestMapping(value = "/one", method = RequestMethod.POST, produces = "application/json")
    @ResponseBody
    @SuppressWarnings(value="unchecked")
    public void updateOne(@RequestBody D obj) throws IOException,PersistenceException {
        service.save(assembler.buildDO(obj, typeDO));
    }

    @RequestMapping(value = "", method = RequestMethod.PUT, produces = "application/json")
    @ResponseBody
    @SuppressWarnings(value="unchecked")
    public void insert(@RequestBody String json) throws IOException,PersistenceException{
        service.save(assembler.buildDOList(mapper.readValue(json,typeDTOList),typeDO));
    }

    @RequestMapping(value = "/one", method = RequestMethod.PUT, produces = "application/json")
    @ResponseBody
    @SuppressWarnings(value="unchecked")
    public Object insertOne(@RequestBody D obj) throws PersistenceException{
        ModelMapper mm = new ModelMapper();
        mm.getConfiguration().setSourceNameTokenizer(NameTokenizers.UNDERSCORE);
        return mm.map(service.save(assembler.buildDO(obj, typeDO)),typeDO);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE, produces = "application/json")
    @ResponseBody
    @SuppressWarnings(value="unchecked")
    public void delete(@PathVariable Long id) throws PersistenceException{
        service.delete(id);
    }

    @RequestMapping(value = "", method = RequestMethod.DELETE, produces = "application/json")
    @ResponseBody
    @SuppressWarnings(value="unchecked")
    public void delete(@RequestBody List<Long> id) throws PersistenceException{
        service.delete(id);
    }

}

