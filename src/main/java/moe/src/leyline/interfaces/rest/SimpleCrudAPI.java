package moe.src.leyline.interfaces.rest;

import moe.src.leyline.domain.DO;
import org.jooq.impl.DAOImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

/**
 * Created by bytenoob on 5/29/16.
 */
public abstract class SimpleCrudAPI<T extends DAOImpl,D extends DO> implements CrudAPI<D>{
    @Autowired
    protected T dao;

    @RequestMapping(value = "", method = RequestMethod.GET, produces = "application/json")
    public List<D> list(){
        return dao.findAll();
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET, produces = "application/json")
    public D find(Integer id){
        return (D)dao.findById(id);
    }


    @RequestMapping(value = "", method = RequestMethod.POST, produces = "application/json")
    public void update(@RequestBody D[] obj){
        dao.update(obj);
    }

    @RequestMapping(value = "", method = RequestMethod.PUT, produces = "application/json")
    public void insert(@RequestBody D[] obj){
        dao.insert(obj);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE, produces = "application/json")
    public void delete(Integer id){
        dao.deleteById(id);
    }

    @RequestMapping(value = "", method = RequestMethod.DELETE, produces = "application/json")
    public void delete(@RequestBody int[] id){
        dao.deleteById(id);
    }


}
