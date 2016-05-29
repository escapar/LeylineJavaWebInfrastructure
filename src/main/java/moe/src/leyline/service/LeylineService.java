package moe.src.leyline.service;

import org.jooq.DSLContext;
import org.jooq.impl.DAOImpl;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Created by bytenoob on 5/29/16.
 */
public abstract class LeylineService<T extends DAOImpl> {
    @Autowired
    protected DSLContext create;

    @Autowired
    protected T serviceDao;
/*
    public LeylineService(DSLContext dslContext) {
        this.create = dslContext;
    }*/
}
