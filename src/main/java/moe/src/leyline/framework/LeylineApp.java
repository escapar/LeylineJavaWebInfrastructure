package moe.src.leyline.framework;

import moe.src.leyline.framework.interfaces.vc.LeylineErrorController;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.data.rest.RepositoryRestMvcAutoConfiguration;
import org.springframework.boot.autoconfigure.jms.activemq.ActiveMQAutoConfiguration;
import org.springframework.boot.autoconfigure.jmx.JmxAutoConfiguration;
import org.springframework.boot.autoconfigure.websocket.WebSocketAutoConfiguration;
import org.springframework.boot.orm.jpa.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.web.config.EnableSpringDataWebSupport;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;

/**
 * 一些和业务无关的最底层配置
 */

@EnableSpringDataWebSupport
@EnableGlobalMethodSecurity(prePostEnabled = true)
@EnableWebSecurity
@SpringBootApplication(exclude = {
        RepositoryRestMvcAutoConfiguration.class,
        JmxAutoConfiguration.class,
        WebSocketAutoConfiguration.class,
        ActiveMQAutoConfiguration.class,
        LeylineErrorController.class
})
@EntityScan("net.masaadora.mall.framework.domain")
@ComponentScan(basePackages = {"net.masadora.mall.framework"})

public abstract class LeylineApp {

}
