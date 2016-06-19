package moe.src.leyline.framework.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.querydsl.core.types.Predicate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import moe.src.leyline.framework.domain.LeylineDO;
import moe.src.leyline.framework.domain.LeylineRepo;
import moe.src.leyline.framework.domain.user.LeylineUser;
import moe.src.leyline.framework.infrastructure.common.exceptions.PersistenceException;

/**
 * Created by POJO on 5/29/16.
 */
@Service
@Transactional(rollbackFor = Throwable.class)
public abstract class LeylineDomainService<T extends LeylineRepo,D extends LeylineDO> {
    @Autowired
    protected T dao;

    @Autowired
    protected LeylineUserDetailsService userDetailsService;

    @SuppressWarnings(value = "unchecked")
    protected static List<Map<String, Object>> resMap(String[] params, Iterable res) {
        List resultList = new ArrayList<Map<String,Object>>();
        if(params!=null && params.length>0 && res!=null) {
            for(Object i : res){
                int c = 0;
                HashMap resMap = new HashMap();
                for(Object e : (Object[])i){
                    resMap.put(params[c++],e);
                }
                resultList.add(resMap);
            }
        }
        return resultList;
    }

    public D save(D entity) throws PersistenceException {
        try {
            return (D)dao.save(entity);
        } catch (Exception e) {
            e.printStackTrace();
            throw new PersistenceException("InsertFailed");
        }
    }

    @SuppressWarnings(value = "unchecked")
    public List<D> save(Collection<D> entities) throws PersistenceException {
        try {
            return (List<D>) dao.save(entities);
        } catch (Exception e) {
            e.printStackTrace();
            throw new PersistenceException("InsertFailed");
        }
    }

    @SuppressWarnings(value = "unchecked")
    public boolean delete(Collection<D> entities) throws PersistenceException {
        try {
            dao.delete(entities);
        } catch (Exception e) {
            e.printStackTrace();
            throw new PersistenceException("DeleteFailed");
        }
        return true;
    }

    @SuppressWarnings(value = "unchecked")
    public boolean delete(Long id) throws PersistenceException {
        try {
            dao.delete(id);
        } catch (Exception e) {
            e.printStackTrace();
            throw new PersistenceException("DeleteFailed");
        }
        return true;
    }

    @SuppressWarnings(value = "unchecked")
    public boolean delete(D entity) throws PersistenceException {
        try {
            dao.delete(entity);
        } catch (Exception e) {
            e.printStackTrace();
            throw new PersistenceException("DeleteFailed");
        }
        return true;
    }

    @SuppressWarnings(value = "unchecked")
    @Transactional(propagation = Propagation.SUPPORTS,readOnly=true)
    public D findOne(Long id) throws PersistenceException {
        try {
            return (D)dao.findOne(id);
        } catch (Exception e) {
            e.printStackTrace();
            throw new PersistenceException("FindFailed");
        }
    }

    @SuppressWarnings(value = "unchecked")
    @Transactional(propagation = Propagation.SUPPORTS,readOnly=true)
    public D get(Long id) throws PersistenceException {
        return findOne(id);
    }

    @SuppressWarnings(value = "unchecked")
    @Transactional(propagation = Propagation.SUPPORTS,readOnly=true)
    public List<D> findAll(List<Integer> ids) throws PersistenceException {
        try {
            return (List<D>) dao.findAll(ids);
        } catch (Exception e) {
            e.printStackTrace();
            throw new PersistenceException("FindFailed");
        }
    }

    @SuppressWarnings(value = "unchecked")
    @Transactional(propagation = Propagation.SUPPORTS,readOnly=true)
    public List<D> findAll() throws PersistenceException {
        try {
            return (List<D>) dao.findAll();
        } catch (Exception e) {
            e.printStackTrace();
            throw new PersistenceException("FindFailed");
        }
    }

    @SuppressWarnings(value = "unchecked")
    @Transactional(propagation = Propagation.SUPPORTS,readOnly=true)
    public Page<D> findAll(Pageable p) throws PersistenceException {
        try {
            return dao.findAll(p);
        } catch (Exception e) {
            e.printStackTrace();
            throw new PersistenceException("FindFailed");
        }
    }

    @SuppressWarnings(value = "unchecked")
    @Transactional(propagation = Propagation.SUPPORTS,readOnly=true)
    public Page<D> findAll(Predicate p, Pageable pageable) throws PersistenceException {
        try {
            return dao.findAll(p, pageable);
        } catch (Exception e) {
            e.printStackTrace();
            throw new PersistenceException("FindFailed");
        }
    }

    @SuppressWarnings(value = "unchecked")
    @Transactional(propagation = Propagation.SUPPORTS,readOnly=true)
    public List<D> findAll(Sort s) throws PersistenceException {
        try {
            return (List<D>) dao.findAll(s);
        } catch (Exception e) {
            e.printStackTrace();
            throw new PersistenceException("FindFailed");
        }
    }

    public User getCurrentUser() {
        return userDetailsService.getCurrentUser();
    }

    public LeylineUser getCurrentDomainUser() {
        return userDetailsService.getByNameEq(getCurrentUser().getUsername());
    }

    public Boolean checkOwnerOf(LeylineUser u) {
        return getCurrentUser().getUsername().equals(u.getName());
    }

    public Boolean checkOwnerOf(User u) {
        return getCurrentUser().getUsername().equals(u.getUsername());
    }

}
