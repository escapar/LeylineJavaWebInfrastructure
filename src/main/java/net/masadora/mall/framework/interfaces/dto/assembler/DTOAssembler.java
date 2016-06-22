package net.masadora.mall.framework.interfaces.dto.assembler;

import net.masadora.mall.framework.domain.AppDO;
import net.masadora.mall.framework.interfaces.dto.AppDTO;
import org.jodah.typetools.TypeResolver;
import org.modelmapper.ModelMapper;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.domain.Page;

import java.lang.reflect.Type;
import java.util.List;
import java.util.stream.Collectors;

/**
 * DTO转DO,特别注意的是无参构造仅能用于继承的子类.
 * TODO: 在无参构造中拿到DO和DTO的type
 */
public class DTOAssembler<DO extends AppDO,DTO extends AppDTO> {
    public ModelMapper m = new ModelMapper();
    Type typeDO;
    Type typeDTO;
    public DTOAssembler() {
        Class<?>[] typeArgs=TypeResolver.resolveRawArguments(DTOAssembler.class, getClass());
        typeDO=getType(typeArgs[0]);
        typeDTO=getType(typeArgs[1]);
    }

    public DTOAssembler(Type DO , Type DTO) {
        typeDO = DO;
        typeDTO = DTO;
    }

    public DTOAssembler(Class<?> DO , Class<?> DTO) {
        typeDO = getType(DO);
        typeDTO = getType(DTO);
    }

    public DTO buildDTO(DO d) {
        return m.map(d, typeDTO);
    }

    public DO buildDO(DTO d) {
        return m.map(d, typeDO);
    }

    public List buildDOList(List<DTO> d) {
        return d.stream().map(e -> buildDO(e)).collect(Collectors.toList());
    }

    public List buildDTOList(List<DO> d) {
        return d.stream().map(e -> buildDTO(e)).collect(Collectors.toList());
    }


    public Page buildPageDTO(Page p) {
        return p.map(new DO2DTOConverter());
    }

    private class DO2DTOConverter implements Converter<DO, DTO> {
        public DTO convert(DO d) {
            return buildDTO(d);
        }
    }

    private static Type getType(Class c){
        return com.google.common.reflect.TypeToken.of(c).getType();
    }

}
