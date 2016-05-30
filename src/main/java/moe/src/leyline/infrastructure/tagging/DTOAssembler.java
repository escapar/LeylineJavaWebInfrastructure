package moe.src.leyline.infrastructure.tagging;

import org.modelmapper.ModelMapper;
import java.lang.reflect.Type;
/**
 * Created by POJO on 5/30/16.
 */
public class DTOAssembler{
    public static DTO buildDTO(DO d,Type t){
        return new ModelMapper().map(d,t);
    }
    public static DO buildDO(DTO d,Type t){
        return new ModelMapper().map(d,t);
    }
    public static DO buildDO(DTO[] d,Type t){
        return new ModelMapper().map(d,t);
    }
}
