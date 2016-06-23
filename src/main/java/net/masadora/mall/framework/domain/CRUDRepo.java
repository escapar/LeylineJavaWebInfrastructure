package net.masadora.mall.framework.domain;

import org.springframework.data.querydsl.QueryDslPredicateExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

/**
 * 不用缓存的Repo
 */
public interface CRUDRepo<T extends AppDO> extends PagingAndSortingRepository<T, Long>, QueryDslPredicateExecutor<T> {

}
