package net.masadora.mall.business.service;

import javaslang.collection.Stream;
import net.masadora.mall.business.domain.common.property.Property;
import net.masadora.mall.business.domain.common.property.PropertyDetail;
import net.masadora.mall.business.domain.common.property.PropertyDetailRepo;
import net.masadora.mall.business.domain.common.property.PropertyRepo;
import net.masadora.mall.framework.interfaces.dto.assembler.DTOAssembler;
import net.masadora.mall.framework.service.TransactionalService;
import net.masadora.mall.interfaces.dto.property.PropertyDetailDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 商品属性服务
 */

@Service
public class PropertyService extends TransactionalService<PropertyDetailRepo,Property> {
    @Autowired
    PropertyRepo propertyRepo;

    DTOAssembler<PropertyDetail, PropertyDetailDTO> assembler =  new DTOAssembler<>(PropertyDetail.class, PropertyDetailDTO.class);

    /**
     * 获取筛选器所需的的商品分类的排序搜索筛选用属性
     * 第二个参数是已选的分类id
     **/
    @Transactional(propagation = Propagation.SUPPORTS,readOnly=true)
    @SuppressWarnings(value = "unchecked")
    public Map<String,List<PropertyDetailDTO>> getPropertyDetailForPropertyFilter(Long categoryId,List<Long> existing){
        Map<String,List<PropertyDetailDTO>> resMap = new HashMap<>();
        List existingProperties = findPropertyByPropertyIds(existing);
        propertyRepo.findByCategoryId(categoryId)
                .parallelStream()
                .filter(i->!existingProperties.contains(i)) // 过滤已经存在的
                .forEach(i->{ //把需要显示的放进Map
                    List r = repo.findByPropertyAndDisplayTrue(i);
                    if (r.size() > 0) {
                        resMap.put(i.getName(), assembler.buildDTOList(repo.findByPropertyAndDisplayTrue(i)));
                    }
                });
        return resMap;
    }

    /**
     * 已选的分类id字符串信息
     **/
    @Transactional(propagation = Propagation.SUPPORTS,readOnly=true)
    public String findDetailsStringByPropertyIds(List<Long> existing) {
        return Stream.of(existing.toArray())
                .map(i ->assembler.buildDTO(repo.get((long)i)))
                .map(PropertyDetailDTO::toString)
                .reduce((i,j)->i+j);

    }

    /**
     * 根据已选属性id列表找对应的属性实体类
     **/
    @Transactional(propagation = Propagation.SUPPORTS,readOnly=true)
    public List<Property> findPropertyByPropertyIds(List<Long> existing){
        return existing.parallelStream().map(i->repo.get(i).getProperty()).collect(Collectors.toList());
    }
}
