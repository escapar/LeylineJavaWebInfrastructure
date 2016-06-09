package moe.src.leyline.framework.interfaces.dto.assembler;

import moe.src.leyline.framework.domain.DO;
import moe.src.leyline.framework.interfaces.dto.DTO;
import moe.src.leyline.framework.interfaces.vc.PagingAndSortingController;

import org.jodah.typetools.TypeResolver;
import org.modelmapper.ModelMapper;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;
import java.util.stream.Collectors;

import com.google.common.reflect.TypeToken;

/**
 * Created by POJO on 5/30/16.
 */
public class DTOAssembler {
    private static final ModelMapper m = new ModelMapper();

    public static DTO buildDTO(DO d, Type dtoT) {
        return m.map(d, dtoT);
    }

    public static DO buildDO(DTO d, Type dtoT) {
        return m.map(d, dtoT);
    }

    public static List buildDOList(List<? extends DTO> d, Type doT) {
        return d.stream().map(e -> m.map(e, doT)).collect(Collectors.toList());
    }

    public static List buildDTOList(List<? extends DO> d, Type dtoT) {
        return d.stream().map(e -> m.map(e, dtoT)).collect(Collectors.toList());
    }


    public static Page buildPageDTO(Page p,Type d){
        return p.map(new DO2DTOConverter().setT(d));
    }

    private static class DO2DTOConverter implements Converter<DO,DTO>{
        Type t ;
        public DTO convert(DO d){
            return DTOAssembler.buildDTO(d,t);
        }

        public DO2DTOConverter setT(Type t){
            this.t = t;
            return this;
        }
    }
}
