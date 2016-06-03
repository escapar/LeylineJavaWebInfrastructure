package moe.src.leyline.infrastructure.persistence.daos;

import javaslang.collection.Stream;
import moe.src.leyline.domain.DO;
import org.springframework.data.querydsl.QueryDslPredicateExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

/**
 * Created by POJO on 6/2/16.
 */
public interface DAO<T extends DO> extends PagingAndSortingRepository<T , Long>,QueryDslPredicateExecutor {


}
