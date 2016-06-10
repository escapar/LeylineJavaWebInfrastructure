package moe.src.leyline.framework.interfaces.rest;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

/**
 * Created by POJO on 5/30/16.
 */
public interface LeylineCRUD<T> {
    public void update(String t) throws Exception;

    public void insert(String t) throws Exception;

    public void delete(Long id) throws Exception;

    public PageImpl list(Pageable p) throws Exception;

    public T find(Long id) throws Exception;
}
