package moe.src.leyline.framework.service;

import moe.src.leyline.framework.domain.DO;
import moe.src.leyline.framework.domain.Repo;
import moe.src.leyline.framework.infrastructure.common.PersistenceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;

/**
 * Created by bytenoob on 5/29/16.
 */
@Service
public abstract class DomainService<T extends Repo> {
    @Autowired
    protected T dao;

    public DO save(DO entity) throws PersistenceException {
        try {
            return (DO) dao.save(entity);
        } catch (Exception e) {
            throw new PersistenceException("InsertFailed");
        }
    }

    public List<DO> save(Collection<DO> entities) throws PersistenceException {
        try {
            return (List<DO>) dao.save(entities);
        } catch (Exception e) {
            throw new PersistenceException("InsertFailed");
        }
    }


    public boolean delete(Collection<DO> entities) throws PersistenceException {
        try {
            dao.delete(entities);
        } catch (Exception e) {
            throw new PersistenceException("DeleteFailed");
        }
        return true;
    }

    public boolean delete(Long id) throws PersistenceException {
        try {
            dao.delete(id);
        } catch (Exception e) {
            throw new PersistenceException("DeleteFailed");
        }
        return true;
    }

    public boolean delete(DO entity) throws PersistenceException {
        try {
            dao.delete(entity);
        } catch (Exception e) {
            throw new PersistenceException("DeleteFailed");
        }
        return true;
    }

    public DO findOne(Long id) throws PersistenceException {
        try {
            return (DO) dao.findOne(id);
        } catch (Exception e) {
            throw new PersistenceException("FindFailed");
        }
    }

    public List<DO> findAll(List<Integer> ids) throws PersistenceException {
        try {
            return (List<DO>) dao.findAll(ids);
        } catch (Exception e) {
            throw new PersistenceException("FindFailed");
        }
    }

    public List<DO> findAll() throws PersistenceException {
        try {
            return (List<DO>) dao.findAll();
        } catch (Exception e) {
            throw new PersistenceException("FindFailed");
        }
    }

    public Page<DO> findAll(Pageable p) throws PersistenceException {
        try {
            return dao.findAll(p);
        } catch (Exception e) {
            throw new PersistenceException("FindFailed");
        }
    }

    public List<DO> findAll(Sort s) throws PersistenceException {
        try {
            return (List<DO>) dao.findAll(s);
        } catch (Exception e) {
            throw new PersistenceException("FindFailed");
        }
    }


}
