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
 * Created by POJO on 6/21/16.
 */

@Service
public class PropertyService extends TransactionalService<PropertyDetailRepo,Property> {
    @Autowired
    PropertyRepo propertyRepo;
    DTOAssembler<PropertyDetail, PropertyDetailDTO> assembler =  new DTOAssembler<>(PropertyDetail.class, PropertyDetailDTO.class);

    @Transactional(propagation = Propagation.SUPPORTS,readOnly=true)
    @SuppressWarnings(value = "unchecked")
    public Map<String,List<PropertyDetailDTO>> getPropertyDetailForPropertyFilter(Long categoryId,List<Long> existing){
        Map<String,List<PropertyDetailDTO>> resMap = new HashMap<>();
        List existingProperties = findPropertyByPropertyIds(existing);
        propertyRepo.findByCategoryId(categoryId).parallelStream().filter(i->!existingProperties.contains(i))
                .forEach(i->{
                    List r = repo.findByPropertyAndDisplayTrue(i);
                    if (r.size() > 0) {
                        resMap.put(i.getName(), assembler.buildDTOList(repo.findByPropertyAndDisplayTrue(i)));
                    }
                });
        return resMap;
    }

    public String findDetailsByPropertyIds(List<Long> existing) {
        return Stream.of(existing.toArray())
                .map(i ->assembler.buildDTO(repo.get((long)i)))
                .map(PropertyDetailDTO::toString)
                .reduce((i,j)->i+j);

    }

    public List<Property> findPropertyByPropertyIds(List<Long> existing){
        return existing.parallelStream().map(i->repo.get(i).getProperty()).collect(Collectors.toList());
    }
}
