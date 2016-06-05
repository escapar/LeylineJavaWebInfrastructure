package moe.src.leyline.interfaces.rest;

import moe.src.leyline.domain.DO;
import moe.src.leyline.domain.Repo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

/**
 * Created by bytenoob on 5/29/16.
 */
public abstract class DOCRUDAPI<T extends Repo,D extends DO> implements CRUDAPI<D> {
    @Autowired
    protected T dao;

    @RequestMapping(value = "", method = RequestMethod.GET, produces = "application/json")
    public List<D> list(){
        return (List<D>)dao.findAll();
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET, produces = "application/json")
    public D find(Integer id){
        return (D)dao.findOne(id);
    }


    @RequestMapping(value = "", method = RequestMethod.POST, produces = "application/json")
    public D update(@RequestBody D[] obj){
        return (D)dao.save(obj);
    }

    @RequestMapping(value = "", method = RequestMethod.PUT, produces = "application/json")
    public D insert(@RequestBody D[] obj){
        return (D)dao.save(obj);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE, produces = "application/json")
    public void delete(Integer id){
        dao.delete(id);
    }

    @RequestMapping(value = "", method = RequestMethod.DELETE, produces = "application/json")
    public void delete(@RequestBody int[] id){
        dao.delete(id);
    }


}
