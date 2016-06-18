package moe.src.leyline.framework.interfaces.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.config.EnableSpringDataWebSupport;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import moe.src.leyline.framework.domain.LeylineDO;
import moe.src.leyline.framework.domain.LeylineRepo;
import moe.src.leyline.framework.interfaces.dto.PageJSON;


/**
 * Created by POJO on 5/29/16.
 */
@Component
@EnableSpringDataWebSupport
public abstract class LeylineDomainRestCRUD<T extends LeylineRepo, D extends LeylineDO> implements LeylineCRUD<D> {
    @Autowired
    protected T dao;

    @SuppressWarnings(value = "unchecked")
    @RequestMapping(value = "", method = RequestMethod.GET, produces = "application/json")
    public PageJSON<D> list(Pageable p) {
        return new PageJSON<>(dao.findAll(p), p);
    }

    @SuppressWarnings(value = "unchecked")
    @RequestMapping(value = "/{id}", method = RequestMethod.GET, produces = "application/json")
    public D find(Integer id) {
        return (D) dao.findOne(id);
    }


    @SuppressWarnings(value = "unchecked")
    @RequestMapping(value = "", method = RequestMethod.POST, produces = "application/json")
    public D update(@RequestBody D[] obj) {
        return (D) dao.save(obj);
    }

    @SuppressWarnings(value = "unchecked")
    @RequestMapping(value = "", method = RequestMethod.PUT, produces = "application/json")
    public D insert(@RequestBody D[] obj) {
        return (D) dao.save(obj);
    }

    @SuppressWarnings(value = "unchecked")
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE, produces = "application/json")
    public void delete(Integer id) {
        dao.delete(id);
    }

    @SuppressWarnings(value = "unchecked")
    @RequestMapping(value = "", method = RequestMethod.DELETE, produces = "application/json")
    public void delete(@RequestBody int[] id) {
        dao.delete(id);
    }

}
