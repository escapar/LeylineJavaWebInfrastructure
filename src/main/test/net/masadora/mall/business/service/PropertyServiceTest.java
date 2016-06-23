package net.masadora.mall.business.service;

import net.masadora.mall.App;
import net.masadora.mall.business.domain.common.property.Property;
import net.masadora.mall.business.domain.common.property.PropertyDetail;
import net.masadora.mall.business.domain.common.property.PropertyDetailRepo;
import net.masadora.mall.business.domain.common.property.PropertyRepo;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.boot.test.WebIntegrationTest;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.Assert.assertTrue;

/**
 * Created by POJO on 6/23/16.
 */
@RunWith(MockitoJUnitRunner.class)
@SpringApplicationConfiguration(App.class)
@WebIntegrationTest
public class PropertyServiceTest {
    @Mock
    PropertyRepo propertyRepo;

    @Mock
    PropertyDetailRepo propertyDetailRepo;

    @InjectMocks
    PropertyService propertyService;

    @Test
    public void testOutputDetailsString(){
        Property property = new Property().setName("prop");
        PropertyDetail propertyDetail1 = new PropertyDetail().setProperty(property).setDisplay(true).setId((long)1).setValue("valueOne");
        PropertyDetail propertyDetail2 = new PropertyDetail().setProperty(property).setDisplay(true).setId((long)2).setValue("valueTwo");
        List<Long> existing = Arrays.asList(1,2).stream().map(Long::valueOf).collect(Collectors.toList());
        Mockito.when(propertyDetailRepo.get((long) 1)).thenReturn(propertyDetail1);
        Mockito.when(propertyDetailRepo.get((long) 2)).thenReturn(propertyDetail2);
        String res = propertyService.findDetailsStringByPropertyIds(existing);
        assertTrue(res.contains(propertyDetail1.getValue()) &&
                res.contains(propertyDetail2.getValue()) &&
                res.contains(propertyDetail2.getProperty().getName()) &&
                res.contains(propertyDetail1.getProperty().getName()));

    }
}
