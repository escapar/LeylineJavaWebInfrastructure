package moe.src.leyline.framework.interfaces.dto.assembler;

import moe.src.leyline.framework.domain.LeylineDO;
import moe.src.leyline.framework.interfaces.dto.LeylineDTO;
import org.modelmapper.ModelMapper;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.domain.Page;

import java.lang.reflect.Type;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by POJO on 5/30/16.
 */
public class DTOAssembler {
    private static final ModelMapper m = new ModelMapper();

    public static LeylineDTO buildDTO(LeylineDO d, Type dtoT) {
        return m.map(d, dtoT);
    }

    public static LeylineDO buildDO(LeylineDTO d, Type dtoT) {
        return m.map(d, dtoT);
    }

    public static List buildDOList(List<? extends LeylineDTO> d, Type doT) {
        return d.stream().map(e -> m.map(e, doT)).collect(Collectors.toList());
    }

    public static List buildDTOList(List<? extends LeylineDO> d, Type dtoT) {
        return d.stream().map(e -> m.map(e, dtoT)).collect(Collectors.toList());
    }


    public static Page buildPageDTO(Page p, Type d) {
        return p.map(new DO2DTOConverter().setT(d));
    }

    private static class DO2DTOConverter implements Converter<LeylineDO, LeylineDTO> {
        Type t;

        public LeylineDTO convert(LeylineDO d) {
            return DTOAssembler.buildDTO(d, t);
        }

        public DO2DTOConverter setT(Type t) {
            this.t = t;
            return this;
        }
    }
}
