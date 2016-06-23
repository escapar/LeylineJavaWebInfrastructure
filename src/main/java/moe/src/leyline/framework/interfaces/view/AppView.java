package moe.src.leyline.framework.interfaces.view;

/**
 * 视图信息配置示例,列表页和详细页,用于配置DTO属性在对应页面JSON中是否显示
 */
public class AppView {
    public interface LIST {
    }

    public interface DETAIL extends LIST {
    }
}
