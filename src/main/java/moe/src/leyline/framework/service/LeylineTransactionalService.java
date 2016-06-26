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
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import moe.src.leyline.business.domain.user.User;
import moe.src.leyline.framework.domain.LeylineCacheableRepo;
import moe.src.leyline.framework.domain.LeylineDO;
import moe.src.leyline.framework.domain.user.LeylineUser;
import moe.src.leyline.framework.infrastructure.common.exceptions.PersistenceException;
import static org.assertj.core.api.Assertions.assertThat;

/**
 * Created by POJO on 5/29/16.
 */
@Service
@Transactional(rollbackFor = Throwable.class, isolation = Isolation.REPEATABLE_READ)
public abstract class LeylineTransactionalService<T extends LeylineCacheableRepo, E extends LeylineDO> {
    @Autowired
    protected T repo;

    @Autowired
    protected LeylineUserDetailsService userDetailsService;

    public LeylineTransactionalService(T repo , LeylineUserDetailsService userDetailsService){
        this.repo = repo;
        this.userDetailsService = userDetailsService;
    }

    public LeylineTransactionalService(){

    }

    @SuppressWarnings(value = "unchecked")
    protected static List<Map<String, Object>> resMap(String[] params, Iterable res) {
        List resultList = new ArrayList<Map<String, Object>>();
        if (params != null && params.length > 0 && res != null) {
            for (Object i : res) {
                int c = 0;
                HashMap resMap = new HashMap();
                for (Object e : (Object[]) i) {
                    resMap.put(params[c++], e);
                }
                resultList.add(resMap);
            }
        }
        return resultList;
    }

    public E save(E entity) throws PersistenceException {
        try {
            return (E) repo.save(entity);
        } catch (Exception e) {
            e.printStackTrace();
            throw new PersistenceException("InsertFailed");
        }
    }

    @SuppressWarnings(value = "unchecked")
    public List<E> save(Collection<E> entities) throws PersistenceException {
        try {
            return (List<E>) repo.save(entities);
        } catch (Exception e) {
            e.printStackTrace();
            throw new PersistenceException("InsertFailed");
        }
    }

    @SuppressWarnings(value = "unchecked")
    public boolean delete(Collection<E> entities) throws PersistenceException {
        try {
            repo.delete(entities);
        } catch (Exception e) {
            e.printStackTrace();
            throw new PersistenceException("DeleteFailed");
        }
        return true;
    }

    @SuppressWarnings(value = "unchecked")
    public boolean delete(Long id) throws PersistenceException {
        try {
            repo.delete(id);
        } catch (Exception e) {
            e.printStackTrace();
            throw new PersistenceException("DeleteFailed");
        }
        return true;
    }

    @SuppressWarnings(value = "unchecked")
    public boolean delete(E entity) throws PersistenceException {
        try {
            repo.delete(entity);
        } catch (Exception e) {
            e.printStackTrace();
            throw new PersistenceException("DeleteFailed");
        }
        return true;
    }

    @SuppressWarnings(value = "unchecked")
    @Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
    public E findOne(Long id) throws PersistenceException {
        try {
            return (E) repo.findOne(id);
        } catch (Exception e) {
            e.printStackTrace();
            throw new PersistenceException("FindFailed");
        }
    }

    @SuppressWarnings(value = "unchecked")
    @Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
    public E get(Long id) throws PersistenceException {
        return findOne(id);
    }

    @SuppressWarnings(value = "unchecked")
    @Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
    public List<? extends E> findAll(List<Integer> ids) throws PersistenceException {
        try {
            return (List<? extends E>) repo.findAll(ids);
        } catch (Exception e) {
            e.printStackTrace();
            throw new PersistenceException("FindFailed");
        }
    }

    @SuppressWarnings(value = "unchecked")
    @Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
    public List<? extends E> findAll() throws PersistenceException {
        try {
            return (List<? extends E>) repo.findAll();
        } catch (Exception e) {
            e.printStackTrace();
            throw new PersistenceException("FindFailed");
        }
    }

    @SuppressWarnings(value = "unchecked")
    @Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
    public Page<? extends E> findAll(Pageable p) throws PersistenceException {
        try {
            return repo.findAll(p);
        } catch (Exception e) {
            e.printStackTrace();
            throw new PersistenceException("FindFailed");
        }
    }

    @SuppressWarnings(value = "unchecked")
    @Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
    public Page<? extends E> findAll(Predicate p, Pageable pageable) throws PersistenceException {
        try {
            return repo.findAll(p, pageable);
        } catch (Exception e) {
            e.printStackTrace();
            throw new PersistenceException("FindFailed");
        }
    }

    @SuppressWarnings(value = "unchecked")
    @Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
    public List<? extends E> findAll(Sort s) throws PersistenceException {
        try {
            return (List<? extends E>) repo.findAll(s);
        } catch (Exception e) {
            e.printStackTrace();
            throw new PersistenceException("FindFailed");
        }
    }

    public LeylineUser getCurrentUser() {
        return userDetailsService.getCurrentUser();
    }

    public Boolean checkOwnerOf(LeylineUser u) {
        return getCurrentUser() == null ? null : getCurrentUser().getUsername().equals(u.getName());
    }

    public Boolean checkOwnerOf(User u) {
        return getCurrentUser() != null && getCurrentUser().getUsername().equals(u.getUsername());
    }

    public void userAssertion(User u) {
        assertThat(u.getId())
                .isEqualTo(getCurrentUser().getId());
    }

}
