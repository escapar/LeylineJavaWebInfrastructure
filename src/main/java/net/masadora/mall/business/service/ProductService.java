package net.masadora.mall.business.service;

import net.masadora.mall.framework.infrastructure.common.exceptions.PersistenceException;
import net.masadora.mall.framework.service.LeylineDomainService;
import net.masadora.mall.business.domain.product.Product;
import net.masadora.mall.business.domain.product.ProductRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by POJO on 6/3/16.
 */
@Service
public class ProductService extends LeylineDomainService<ProductRepo,Product> {

    @Autowired
    ProductRepo productRepo;
    /*
    {
  "id": 48,
  "createdAt": 1466475272418,
  "deletedAt": null,
  "description": "1231",
  "modifiedAt": null,
  "name": "yoooo3",
  "price": 450,
  "reservable": true,
  "soldCount": 0,
  "stock": 0,
  "weight": 777,
  "vendorId": 1,
  "vendorName": "masadora",
  "images": [],
  "categories": [{"id":1}],
  "properties":[{"value":"wow","propertyName":"doge"}]
}
     */
    /*
    @Override
    public Product save(Product entity) throws PersistenceException {
        try {
            entity.setImages(
                    entity.getImages().parallelStream().map(
                            i-> i.setProduct(entity))
                            .collect(Collectors.toList()));
            entity.setProperties(
                    entity.getProperties().parallelStream().map(
                            i-> i.setProduct(entity))
                            .collect(Collectors.toList()));


            return repo.save(entity);

        } catch (Exception e) {
            e.printStackTrace();
            throw new PersistenceException("InsertFailed");
        }
    }
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

    public Page<Product> search(Long categoryId, Pageable p){
        return productRepo.findByCategories_IdAndRootProductIsNull(categoryId,p);
    }

    public Page<Product> search(String keyword, Pageable p){
        return productRepo.findByNameLikeAndRootProductIsNull("%"+keyword+"%",p);
    }

    public Page<Product> search(String keyword, Long categoryId , Pageable p){
        return productRepo.findByNameLikeAndCategories_IdAndRootProductIsNull("%"+keyword+"%",categoryId,p);
    }

}
