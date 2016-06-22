package net.masadora.mall.framework.domain;

import com.mysema.query.types.OrderSpecifier;
import com.mysema.query.types.Predicate;
import net.masadora.mall.framework.domain.AppDO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.QueryHints;
import org.springframework.data.querydsl.QueryDslPredicateExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

import javax.persistence.QueryHint;

/**
 * 不用缓存的Repo
 */
public interface CRUDRepo<T extends AppDO> extends PagingAndSortingRepository<T, Long>, QueryDslPredicateExecutor<T> {

}
