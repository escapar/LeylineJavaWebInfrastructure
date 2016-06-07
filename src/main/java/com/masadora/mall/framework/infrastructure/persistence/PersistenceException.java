package com.masadora.mall.framework.infrastructure.persistence;

import com.masadora.mall.framework.interfaces.view.LeylineException;

/**
 * Created by POJO on 6/3/16.
 */
public class PersistenceException extends LeylineException {
    public PersistenceException(String ex){
        super(ex);
    }
}
