package net.masadora.mall.business.service;

import net.masadora.mall.business.domain.common.property.Property;
import net.masadora.mall.business.domain.common.property.PropertyDetail;
import net.masadora.mall.business.domain.common.property.PropertyDetailRepo;
import net.masadora.mall.business.domain.common.property.PropertyRepo;
import net.masadora.mall.business.domain.product.ProductRepo;
import net.masadora.mall.framework.interfaces.dto.assembler.DTOAssembler;
import net.masadora.mall.framework.service.LeylineDomainService;
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
 * Created by POJO on 6/21/16.
 */

@Service
public class PropertyService extends LeylineDomainService<PropertyDetailRepo,Property> {
    @Autowired
    PropertyRepo propertyRepo;

    @Transactional(propagation = Propagation.SUPPORTS,readOnly=true)
    public Map<String,List<PropertyDetailDTO>> getPropertyDetailForPropertyFilter(Long categoryId){
        Map<String,List<PropertyDetailDTO>> resMap = new HashMap<>();

        propertyRepo.findByCategoryId(categoryId).forEach(i->{
                List r = repo.findByPropertyAndDisplayTrue(i);
                if(r.size()>0) resMap.put(i.getName(),
                        new DTOAssembler<PropertyDetail,PropertyDetailDTO>(PropertyDetail.class,PropertyDetailDTO.class).buildDTOList(
                                repo.findByPropertyAndDisplayTrue(i)));
            }
        );
        return resMap;
    }
}
