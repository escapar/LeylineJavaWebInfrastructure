package moe.src.leyline.framework.domain;

import org.springframework.data.querydsl.QueryDslPredicateExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

/**
 * Created by POJO on 6/2/16.
 */
public interface Repo<T extends DO> extends PagingAndSortingRepository<T, Long>, QueryDslPredicateExecutor {


}
