package moe.src.leyline.framework.domain;

import javax.persistence.QueryHint;

import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.Predicate;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.QueryHints;
import org.springframework.data.querydsl.QueryDslPredicateExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

import moe.src.leyline.framework.domain.LeylineDO;

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
