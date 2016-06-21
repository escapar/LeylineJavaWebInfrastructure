package net.masadora.mall.business.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by POJO on 6/21/16.
 */
@Service
@Transactional(rollbackFor = Throwable.class,isolation = Isolation.REPEATABLE_READ)
public class AppService  {

}
