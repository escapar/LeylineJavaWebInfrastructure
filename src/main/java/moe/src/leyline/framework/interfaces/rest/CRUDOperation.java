package moe.src.leyline.framework.interfaces.rest;

import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

/**
 * Created by POJO on 5/30/16.
 */
public interface CRUDOperation<T> {
    void update(String t) throws Exception;

    void insert(String t) throws Exception;

    void delete(Long id) throws Exception;

    PageImpl list(Pageable p) throws Exception;

    T find(Long id) throws Exception;


}
