package com.masadora.mall.framework.interfaces.dto.assembler;

import com.masadora.mall.framework.domain.DO;
import com.masadora.mall.framework.interfaces.dto.DTO;

import org.modelmapper.ModelMapper;
import java.lang.reflect.Type;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by POJO on 5/30/16.
 */
public class DTOAssembler{
    private static final ModelMapper m = new ModelMapper();

    public static DTO buildDTO(DO d, Type dtoT){
        return m.map(d,dtoT);
    }
    public static DO buildDO(DTO d,Type dtoT){
        return m.map(d,dtoT);
    }
    public static List buildDOList(List<? extends DTO> d,Type dtoT){
        return d.stream().map(e -> m.map(e,dtoT)).collect(Collectors.toList());
    }
}
