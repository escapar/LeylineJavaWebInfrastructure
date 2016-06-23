package net.masadora.mall.business.service;

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
public class PropertyService extends TransactionalService<PropertyDetailRepo,PropertyDetail> {
    @Autowired
    PropertyRepo propertyRepo;

    @Autowired
    PropertyDetailRepo repo;

    DTOAssembler<PropertyDetail, PropertyDetailDTO> assembler =  new DTOAssembler<>(PropertyDetail.class, PropertyDetailDTO.class);


    /**
     * 只给个Property ID也能更新Property Detail
     **/

    @Override
    public PropertyDetail save(PropertyDetail p){
        PropertyDetail existing = repo.getByValueAndPropertyId(p.getValue(),p.getProperty().getId());
        return existing == null ? repo.save(p.setProperty(propertyRepo.get(p.getProperty().getId()))) : existing;
    }

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
                .stream()
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
        return existing.stream()
                .map(i ->assembler.buildDTO(repo.get(i)))
                .map(PropertyDetailDTO::toString)
                .collect(Collectors.joining(" "));
    }

    /**
     * 根据已选属性id列表找对应的属性实体类
     **/
    @Transactional(propagation = Propagation.SUPPORTS,readOnly=true)
    public List<Property> findPropertyByPropertyIds(List<Long> existing){
        return existing.stream().map(i->repo.get(i).getProperty()).collect(Collectors.toList());
    }
}
