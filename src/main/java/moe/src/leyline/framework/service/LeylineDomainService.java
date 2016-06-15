package moe.src.leyline.framework.service;

import com.mysema.query.types.Predicate;
import moe.src.leyline.framework.domain.LeylineDO;
import moe.src.leyline.framework.domain.LeylineRepo;
import moe.src.leyline.framework.infrastructure.common.Utils;
import moe.src.leyline.framework.infrastructure.common.exceptions.PersistenceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Stream;

/**
 * Created by POJO on 5/29/16.
 */
@Service
@Transactional(rollbackFor = Throwable.class,isolation = Isolation.REPEATABLE_READ)
public abstract class LeylineDomainService<T extends LeylineRepo> {
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

    public LeylineDO save(LeylineDO entity) throws PersistenceException {
        try {
            return (LeylineDO) dao.save(entity);
        } catch (Exception e) {
            e.printStackTrace();
            throw new PersistenceException("InsertFailed");
        }
    }

    @SuppressWarnings(value = "unchecked")
    public List<LeylineDO> save(Collection<LeylineDO> entities) throws PersistenceException {
        try {
            return (List<LeylineDO>) dao.save(entities);
        } catch (Exception e) {
            e.printStackTrace();
            throw new PersistenceException("InsertFailed");
        }
    }

    @SuppressWarnings(value = "unchecked")
    public boolean delete(Collection<LeylineDO> entities) throws PersistenceException {
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
    public boolean delete(LeylineDO entity) throws PersistenceException {
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
    public LeylineDO findOne(Long id) throws PersistenceException {
        try {
            return (LeylineDO) dao.findOne(id);
        } catch (Exception e) {
            e.printStackTrace();
            throw new PersistenceException("FindFailed");
        }
    }

    @SuppressWarnings(value = "unchecked")
    @Transactional(propagation = Propagation.SUPPORTS,readOnly=true)
    public List<LeylineDO> findAll(List<Integer> ids) throws PersistenceException {
        try {
            return (List<LeylineDO>) dao.findAll(ids);
        } catch (Exception e) {
            e.printStackTrace();
            throw new PersistenceException("FindFailed");
        }
    }

    @SuppressWarnings(value = "unchecked")
    @Transactional(propagation = Propagation.SUPPORTS,readOnly=true)
    public List<LeylineDO> findAll() throws PersistenceException {
        try {
            return (List<LeylineDO>) dao.findAll();
        } catch (Exception e) {
            e.printStackTrace();
            throw new PersistenceException("FindFailed");
        }
    }

    @SuppressWarnings(value = "unchecked")
    @Transactional(propagation = Propagation.SUPPORTS,readOnly=true)
    public Page<LeylineDO> findAll(Pageable p) throws PersistenceException {
        try {
            return dao.findAll(p);
        } catch (Exception e) {
            e.printStackTrace();
            throw new PersistenceException("FindFailed");
        }
    }

    @SuppressWarnings(value = "unchecked")
    @Transactional(propagation = Propagation.SUPPORTS,readOnly=true)
    public Page<LeylineDO> findAll(Predicate p, Pageable pageable) throws PersistenceException {
        try {
            return dao.findAll(p, pageable);
        } catch (Exception e) {
            e.printStackTrace();
            throw new PersistenceException("FindFailed");
        }
    }

    @SuppressWarnings(value = "unchecked")
    @Transactional(propagation = Propagation.SUPPORTS,readOnly=true)
    public List<LeylineDO> findAll(Sort s) throws PersistenceException {
        try {
            return (List<LeylineDO>) dao.findAll(s);
        } catch (Exception e) {
            e.printStackTrace();
            throw new PersistenceException("FindFailed");
        }
    }

    public User getCurrentUser() {
        return userDetailsService.getCurrentUser();
    }

}
