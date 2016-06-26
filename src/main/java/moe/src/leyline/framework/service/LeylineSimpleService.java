package moe.src.leyline.framework.service;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import moe.src.leyline.framework.domain.LeylineCacheableRepo;
import moe.src.leyline.framework.domain.LeylineDO;

/**
 * Created by POJO on 5/29/16.
 */
@Service
@Transactional(rollbackFor = Throwable.class, isolation = Isolation.REPEATABLE_READ)
public class LeylineSimpleService<T extends LeylineCacheableRepo, E extends LeylineDO> extends LeylineTransactionalService<T,E>{

}
