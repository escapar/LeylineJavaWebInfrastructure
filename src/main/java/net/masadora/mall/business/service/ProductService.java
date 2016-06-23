package net.masadora.mall.business.service;

import net.masadora.mall.business.domain.product.Product;
import net.masadora.mall.business.domain.product.ProductRepo;
import net.masadora.mall.framework.infrastructure.common.exceptions.PersistenceException;
import net.masadora.mall.framework.service.TransactionalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 商品服务
 */
@Service
public class ProductService extends TransactionalService<ProductRepo,Product> {

    @Autowired
    PropertyService propertyService;

    @Override
    public Product save(Product entity) throws PersistenceException {
        try {

            entity.setProperties(
                    entity.getProperties().parallelStream().map(i->propertyService.save(i)).collect(Collectors.toList()));


            return repo.save(entity);

        } catch (Exception e) {
            e.printStackTrace();
            throw new PersistenceException("InsertFailed");
        }
    }

    /**
     * 更新一个商品组
     * 要指定默认(根)商品和其他商品
     */
    public List<Product> saveBatchAdmin(Product root,List<Product> others) throws PersistenceException {
        try {
            Product resRoot = save(root);
            others.forEach(i -> {
                try {
                    save(i.setRootProduct(resRoot));
                } catch (PersistenceException e) {
                    e.printStackTrace();
                }
            });
            others.add(resRoot);
            return others;
        } catch (Exception e) {
            e.printStackTrace();
            throw new PersistenceException("InsertFailed");
        }
    }

    /**
     * 列出类目下所有商品带分页
     * @param categoryId
     * @param p
     * @return
     */
    @Transactional(propagation = Propagation.SUPPORTS,readOnly=true)
    private Page<Product> search(Long categoryId, Pageable p){
        return repo.findByCategories_IdAndRootProductIsNull(categoryId,p);
    }

    /**
     * 关键词搜索商品带分页
     * @param keyword
     * @param p
     * @return
     */
    @Transactional(propagation = Propagation.SUPPORTS,readOnly=true)
    private Page<Product> search(String keyword, Pageable p){
        return repo.findByNameLikeAndRootProductIsNull("%"+keyword+"%",p);
    }

    /**
     * 关键词搜索某个类目的商品带分页
     * @param keyword
     * @param p
     * @return
     */
    @Transactional(propagation = Propagation.SUPPORTS,readOnly=true)
    public Page<Product> search(String keyword, Long categoryId , Pageable p){
        if(categoryId == null){
            return search(keyword,p);
        }
        if(keyword == null){
            return search(categoryId,p);
        }
        return repo.findByNameLikeAndCategories_IdAndRootProductIsNull("%"+keyword+"%",categoryId,p);
    }

    /**
     * 关键词和属性id搜索某个类目的商品带分页
     * @param keyword
     * @param p
     * @return
     */
    @Transactional(propagation = Propagation.SUPPORTS,readOnly=true)
    public Page<Product> filter(String keyword, List<Long> propertyIds , Pageable p){
        return repo.findByNameLikeAndProperties_IdInAndRootProductIsNull("%"+keyword+"%",propertyIds,p);
    }

    /**
     * 属性id搜索某个类目的商品带分页
     * @param propertyIds
     * @param p
     * @return
     */
    @Transactional(propagation = Propagation.SUPPORTS,readOnly=true)
    public Page<Product> filter(List<Long> propertyIds , Pageable p){
        return repo.findByProperties_IdInAndRootProductIsNull(propertyIds,p);
    }

}
