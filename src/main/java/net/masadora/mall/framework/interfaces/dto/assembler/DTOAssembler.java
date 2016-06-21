package net.masadora.mall.framework.interfaces.dto.assembler;

import net.masadora.mall.framework.domain.LeylineDO;
import net.masadora.mall.framework.interfaces.dto.LeylineDTO;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.domain.Page;

import java.lang.reflect.Type;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by POJO on 5/30/16.
 */
public class DTOAssembler<DO extends LeylineDO,DTO extends LeylineDTO> {
    public ModelMapper m = new ModelMapper();


    public DTO buildDTO(DO d, Type dtoT) {
        return m.map(d, dtoT);
    }

    public DO buildDO(DTO d, Type dtoT) {
        return m.map(d, dtoT);
    }

    public List buildDOList(List<? extends LeylineDTO> d, Type doT) {
        return d.stream().map(e -> m.map(e, doT)).collect(Collectors.toList());
    }

    public List buildDTOList(List<? extends LeylineDO> d, Type dtoT) {
        return d.stream().map(e -> m.map(e, dtoT)).collect(Collectors.toList());
    }


    public Page buildPageDTO(Page p, Type d) {
        return p.map(new DO2DTOConverter().setT(d));
    }

    private class DO2DTOConverter implements Converter<DO, DTO> {
        Type t;

        public DTO convert(DO d) {
            return buildDTO(d, t);
        }

        public DO2DTOConverter setT(Type t) {
            this.t = t;
            return this;
        }
    }

}
