package moe.src.leyline.framework.interfaces.dto.assembler;

import moe.src.leyline.framework.domain.DO;
import moe.src.leyline.framework.interfaces.dto.DTO;
import org.modelmapper.ModelMapper;

import java.lang.reflect.Type;
import java.util.List;
import java.util.stream.Collectors;

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
}
