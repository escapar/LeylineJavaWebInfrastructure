package moe.src.leyline.interfaces.rest;

import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.reflect.TypeToken;
import moe.src.leyline.infrastructure.tagging.*;
import org.jodah.typetools.TypeResolver;
import org.jooq.impl.DAOImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by POJO on 5/30/16.
 */
public abstract class DTOCrudAPI<T extends DAOImpl,D extends DTO,O extends DO> implements CrudAPI{
    @Autowired
    protected T dao;
    private Class<?>[] typeArgs;
    private Type typeDAO;
    private Type typeDTO;
    private Type typeDO;
    private JavaType typeDTOList;
    private JavaType typeDOList;

    private static final ObjectMapper mapper = new ObjectMapper();
    private static final DTOAssembler assembler = new DTOAssembler();

    DTOCrudAPI(){
        typeArgs = TypeResolver.resolveRawArguments(DTOCrudAPI.class,getClass());
        this.typeDAO = TypeToken.of(typeArgs[0]).getType();
        this.typeDTO = TypeToken.of(typeArgs[1]).getType();
        this.typeDO  = TypeToken.of(typeArgs[2]).getType();
        typeDTOList = mapper.getTypeFactory().constructCollectionType(List.class,typeArgs[1]);
        typeDOList = mapper.getTypeFactory().constructCollectionType(List.class,typeArgs[2]);

    }

    @RequestMapping(value = "", method = RequestMethod.GET, produces = "application/json")
    @SuppressWarnings(value="unchecked")
    public List list(){
        return (List) dao.findAll()
                .stream()
                .map( e -> assembler.buildDTO((DO)e,typeDTO))
                .collect(Collectors.toList());
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET, produces = "application/json")
    @SuppressWarnings(value="unchecked")
    public D find(Integer id){
        return (D)assembler.buildDTO((DO)dao.findById(id),typeDTO);
    }


    @RequestMapping(value = "", method = RequestMethod.POST, produces = "application/json")
    @SuppressWarnings(value="unchecked")
    public void update(@RequestBody String json) throws IOException{
        dao.insert(assembler.buildDOList(mapper.readValue(json,typeDTOList),typeDO));
    }

    @RequestMapping(value = "/one", method = RequestMethod.POST, produces = "application/json")
    @SuppressWarnings(value="unchecked")
    public void updateOne(@RequestBody D obj){
        dao.insert(assembler.buildDO(obj, typeDO));
    }

    @RequestMapping(value = "", method = RequestMethod.PUT, produces = "application/json")
    @SuppressWarnings(value="unchecked")
    public void insert(@RequestBody String json) throws IOException{
        dao.insert(assembler.buildDOList(mapper.readValue(json,typeDTOList),typeDO));
    }

    @RequestMapping(value = "/one", method = RequestMethod.PUT, produces = "application/json")
    @SuppressWarnings(value="unchecked")
    public void insertOne(@RequestBody D obj){
        dao.insert(assembler.buildDO(obj, typeDO));
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE, produces = "application/json")
    @SuppressWarnings(value="unchecked")
    public void delete(Integer id){
        dao.deleteById(id);
    }

    @RequestMapping(value = "", method = RequestMethod.DELETE, produces = "application/json")
    @SuppressWarnings(value="unchecked")
    public void delete(@RequestBody int[] id){
        dao.deleteById(id);
    }


}

