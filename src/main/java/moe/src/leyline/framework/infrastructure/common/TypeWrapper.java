package moe.src.leyline.framework.infrastructure.common;

/**
 * Created by POJO on 5/30/16.
 */
public class TypeWrapper<T> {
    private final Class<T> type;

    public TypeWrapper(Class<T> type) {
        this.type = type;
    }

    public Class<T> getMyType() {
        return this.type;
    }
}
