package moe.src.leyline.interfaces.rest;

import java.io.Serializable;
import java.math.BigInteger;
import java.util.List;

/**
 * Created by POJO on 5/30/16.
 */
public interface CrudAPI<T> {
    public void update(String t) throws Exception;
    public void insert(String t) throws Exception;
    public void delete(Long id) throws Exception;
    public List<T> list() throws Exception;
    public T find(Long id) throws Exception;
}
