package com.masadora.mall.framework.interfaces.rest;

import java.util.List;

/**
 * Created by POJO on 5/30/16.
 */
public interface CRUDAPI<T> {
    public void update(String t) throws Exception;
    public void insert(String t) throws Exception;
    public void delete(Long id) throws Exception;
    public List<T> list() throws Exception;
    public T find(Long id) throws Exception;
}
