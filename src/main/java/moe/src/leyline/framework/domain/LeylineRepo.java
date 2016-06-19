package moe.src.leyline.framework.domain;

import com.querydsl.core.types.Predicate;

import org.springframework.data.querydsl.QueryDslPredicateExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

/**
 * Created by POJO on 6/2/16.
 */
public interface LeylineRepo<T extends LeylineDO> extends PagingAndSortingRepository<T, Long>, QueryDslPredicateExecutor {
    default T get(Predicate predicate){
        return (T)findOne(predicate);
    }

    default T get(Long id){
        return findOne(id);
    }
}
