package moe.src.leyline.interfaces.rest;

import java.io.Serializable;
import java.util.List;

/**
 * Created by POJO on 5/30/16.
 */
public interface CrudAPI<T extends Serializable> {
    public void update(T[] t);
    public void insert(T[] t);
    public void delete(Integer id);
    public List<T> list();
    public T find(Integer id);
}
