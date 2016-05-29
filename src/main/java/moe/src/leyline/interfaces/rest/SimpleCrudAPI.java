package moe.src.leyline.interfaces.rest;

import org.jooq.DAO;
import org.jooq.DSLContext;
import org.jooq.impl.DAOImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import moe.src.leyline.infrastructure.persistence.daos.ProductDao;

/**
 * Created by bytenoob on 5/29/16.
 */
public abstract class SimpleCrudAPI<T extends DAOImpl> {
    @Autowired
    protected T dao;

    @RequestMapping(value = "", method = RequestMethod.GET, produces = "application/json")
    public Object list(){
        return dao.findAll();
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET, produces = "application/json")
    public Object list(Integer id){
        return dao.findById(id);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE, produces = "application/json")
    public void delete(Integer id){
        dao.deleteById(id);
    }

    @RequestMapping(value = "", method = RequestMethod.DELETE, produces = "application/json")
    public void delete(@RequestBody int[] id){
        dao.deleteById(id);
    }

    @RequestMapping(value = "", method = RequestMethod.POST, produces = "application/json")
    public void update(@RequestBody Object[] obj){
        dao.update(obj);
    }

    @RequestMapping(value = "", method = RequestMethod.PUT, produces = "application/json")
    public void insert(@RequestBody Object[] obj){
        dao.insert(obj);
    }

}
