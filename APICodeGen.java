package moe.src.leyline.framework.infrastructure.common;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.google.common.collect.ImmutableMap;
import javaslang.Tuple2;
import javaslang.collection.Stream;
import lombok.AllArgsConstructor;
import lombok.Value;
import moe.src.leyline.framework.interfaces.rest.LeylineRestCRUD;
import net.masadora.mall.interfaces.rest.ProductAPI;
import org.jodah.typetools.TypeResolver;
import org.springframework.web.bind.annotation.PathVariable;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.lang.annotation.Annotation;
import java.lang.reflect.Modifier;
import java.lang.reflect.Parameter;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by POJO on 6/11/16.
 */
public class APICodeGen {
    private static final ObjectMapper om = new ObjectMapper().enable(SerializationFeature.INDENT_OUTPUT);

    public static void main(String[] args) {
        // To Implement A CLI!
        List l = Stream.of(
                genClassInfoWithDTO(ProductAPI.class)
        ).toJavaList();

        try {
            om.writeValue(new File("out.json"), l);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static APIInfo genClassInfoWithDTO(Class controller) {
        try {
            return new APIInfo(
                    processTypeString(controller.getName()),
                    generateClassInfoSet(controller, processEntryURL(controller)),
                    resolveDTOInstance(TypeResolver.resolveRawArguments(LeylineRestCRUD.class, controller)[1]));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private static String processEntryURL(Class c) {
        Map entryURLMap;
        List entryURLList = processDeclaredAnnotations(c.getAnnotations(), "");
        if (entryURLList.size() > 0) {
            entryURLMap = (Map) (entryURLList.get(0));
            return (String) entryURLMap.get("value");
        } else {
            return "";
        }
    }

    private static Set generateClassInfoSet(Class controller, String entryURL) {
        return //getAllMethods(controller,true,true)
                //   .stream()
                //   .filter(e -> e.getDeclaringClass().equals(controller))
                Stream.of(controller.getMethods())
                        .filter(e -> e.getModifiers() == Modifier.PUBLIC && !e.getName().equals("toString") && !e.getName().equals("equals"))
                        .map(e -> (
                                ImmutableMap.of(
                                        e.getName(),
                                        ImmutableMap.<String, Object>builder()
                                                .put("produces", processTypeString(e.getReturnType().getName().replace("AppDTO", processControllerDTO(controller))))
                                                .put("params", processParameterAnnotations(e.getParameters()))
                                                .put("req", processDeclaredAnnotations(e.getDeclaredAnnotations(), entryURL))
                                                .build()
                                ))).toJavaSet();
    }

    @SuppressWarnings(value = "unchecked")
    private static List processDeclaredAnnotations(Annotation[] src, String entry) {
        // MORE TO EXTEND
        return Stream.of(src)
                .filter(e -> e.annotationType().getName().contains("RequestMapping"))
                .map(e -> processReqString(e.toString(), entry))
                .toJavaList();
    }

    private static List processParameterAnnotations(Parameter[] src) {
        return Stream.of(src)
                .filter(e -> e.getAnnotation(PathVariable.class) != null)
                .map(e ->
                        processTypeString(e.toString())
                ).toJavaList();
    }

    private static String processTypeString(String javaType) {
        return javaType.replaceAll("(.*\\.)([^\\.]*$)", "$2").replaceAll("(.*)( .*)", "$1");
    }

    @SuppressWarnings(value = "unchecked")
    private static Map processReqString(String annotationType, String entryURL) {
        return Stream.of(
                annotationType
                        .replaceAll("(.*)\\((.*)\\)(.*)", "$2")
                        .replaceAll(" ", "").replaceAll("\\[", "").replaceAll("\\]", "")
                        .split(","))
                .filter(s -> s.contains("value") || !s.endsWith("=") && !s.isEmpty())
                .toJavaMap(
                        s -> s.contains("value") ?
                                processURIValue(s, entryURL) : new Tuple2(s.split("=")[0], s.split("=")[1])
                );
    }

    @SuppressWarnings(value = "unchecked")
    private static Tuple2 processURIValue(String s, String entryURL) {
        s = s.endsWith("=") ? s.concat("/") : s;

        return s.split("=")[1].indexOf('/') != 0 && entryURL.lastIndexOf('/') != entryURL.length() - 1 ?
                new Tuple2(s.split("=")[0], entryURL + '/' + s.split("=")[1]) :
                new Tuple2(s.split("=")[0], entryURL + s.split("=")[1]);
    }

    private static String processControllerDTO(Class controller) {
        Class<?>[] typeArgs = TypeResolver.resolveRawArguments(controller.getSuperclass(), controller);
        return typeArgs != null && typeArgs.length > 0 ? processTypeString(typeArgs[1].getName()) : "";
    }

    private static List resolveDTOInstance(Class dto) {
        return javaslang.collection.List.of(dto.getDeclaredFields())
                .map(e -> ImmutableMap.of(e.getName(), processTypeString(e.getType().getName())))
                .toJavaList();
    }

    @Value
    @AllArgsConstructor
    private static class APIInfo implements Serializable {
        private final String name;
        private final Set api;
        private final Object dto;
    }
}
