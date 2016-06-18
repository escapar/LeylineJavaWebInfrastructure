package moe.src.leyline.framework.infrastructure.common;

import java.util.List;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

import com.google.common.collect.Lists;

/**
 * Created by POJO on 6/15/16.
 */
public class Utils {
    public static List it2List(Iterable it){
        return it == null ? null : Lists.newArrayList(it);
    }

    public static Stream it2Stream(Iterable it){
        return it == null ? null : StreamSupport.stream(it.spliterator(), false);
    }
}
