package moe.src.leyline.interfaces.rest;

import moe.src.leyline.infrastructure.tagging.*;
import org.jooq.impl.DAOImpl;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;

/**
 * Created by POJO on 5/30/16.
 */
public class DTOCrudAPI<T extends DAOImpl,D extends DTO,O extends DO> implements CrudAPI<D> {
    @Autowired
    protected T dao;

    @RequestMapping(value = "", method = RequestMethod.GET, produces = "application/json")
    @SuppressWarnings(value="unchecked")
    public List<D> list(){
        return (List<D>) dao.findAll()
                .stream()
                .map( e -> DTOAssembler.buildDTO((DO)e,new TypeToken<D>(){}.getType()))
                .collect(Collectors.toList());
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET, produces = "application/json")
    @SuppressWarnings(value="unchecked")
    public D find(Integer id){
        return (D)DTOAssembler.buildDTO((DO)dao.findById(id),new TypeToken<D>(){}.getType());
    }


    @RequestMapping(value = "", method = RequestMethod.POST, produces = "application/json")
    @SuppressWarnings(value="unchecked")
    public void update(@RequestBody D[] obj){
        dao.update(DTOAssembler.buildDO(obj,new TypeToken<O[]>(){}.getType()));
    }

    @RequestMapping(value = "", method = RequestMethod.PUT, produces = "application/json")
    @SuppressWarnings(value="unchecked")
    public void insert(@RequestBody D[] obj){
        dao.insert(DTOAssembler.buildDO(obj,new TypeToken<O[]>(){}.getType()));
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

