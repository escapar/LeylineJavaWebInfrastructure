package moe.src.leyline.framework.service;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

import com.querydsl.core.types.Predicate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import moe.src.leyline.framework.domain.LeylineDO;
import moe.src.leyline.framework.domain.LeylineRepo;
import moe.src.leyline.framework.infrastructure.common.exceptions.PersistenceException;

/**
 * Created by POJO on 5/29/16.
 */
@Service
public abstract class LeylineDomainService<T extends LeylineRepo> {
    @Autowired
    protected T dao;

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
    public LeylineDO findOne(Long id) throws PersistenceException {
        try {
            return (LeylineDO) dao.findOne(id);
        } catch (Exception e) {
            e.printStackTrace();
            throw new PersistenceException("FindFailed");
        }
    }

    @SuppressWarnings(value = "unchecked")
    public List<LeylineDO> findAll(List<Integer> ids) throws PersistenceException {
        try {
            return (List<LeylineDO>) dao.findAll(ids);
        } catch (Exception e) {
            e.printStackTrace();
            throw new PersistenceException("FindFailed");
        }
    }
    @SuppressWarnings(value = "unchecked")
    public List<LeylineDO> findAll() throws PersistenceException {
        try {
            return (List<LeylineDO>) dao.findAll();
        } catch (Exception e) {
            e.printStackTrace();
            throw new PersistenceException("FindFailed");
        }
    }
    @SuppressWarnings(value = "unchecked")
    public Page<LeylineDO> findAll(Pageable p) throws PersistenceException {
        try {
            return dao.findAll(p);
        } catch (Exception e) {
            e.printStackTrace();
            throw new PersistenceException("FindFailed");
        }
    }
    @SuppressWarnings(value = "unchecked")
    public Page<LeylineDO> findAll(Predicate p , Pageable pageable) throws PersistenceException {
        try {
            return dao.findAll(p,pageable);
        } catch (Exception e) {
            e.printStackTrace();
            throw new PersistenceException("FindFailed");
        }
    }

    @SuppressWarnings(value = "unchecked")
    public List<LeylineDO> findAll(Sort s) throws PersistenceException {
        try {
            return (List<LeylineDO>) dao.findAll(s);
        } catch (Exception e) {
            e.printStackTrace();
            throw new PersistenceException("FindFailed");
        }
    }

    @SuppressWarnings(value = "unchecked")
    protected static Map<String , Object> customedQueryResult(String[] params , Stream res){
        Map<String , Object> resultMap = new HashMap<>();
        int[] idx = { 0 };
        res.forEach(e-> resultMap.put(params[idx[0]++],e));
        return resultMap;
    }
}
