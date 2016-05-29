package moe.src.leyline.infrastructure;

import org.jooq.DSLContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Created by POJO on 5/29/16.
 */
@Component

public class JooqDSLInitializer {
    private final DSLContext create;

    @Autowired
    public JooqDSLInitializer(DSLContext dslContext) {
        this.create = dslContext;
    }
}
