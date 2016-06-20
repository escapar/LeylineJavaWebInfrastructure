package net.masadora.mall.framework;

import net.masadora.mall.framework.interfaces.vc.LeylineErrorController;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.data.rest.RepositoryRestMvcAutoConfiguration;
import org.springframework.boot.autoconfigure.jms.activemq.ActiveMQAutoConfiguration;
import org.springframework.boot.autoconfigure.jmx.JmxAutoConfiguration;
import org.springframework.boot.autoconfigure.websocket.WebSocketAutoConfiguration;
import org.springframework.boot.orm.jpa.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.web.config.EnableSpringDataWebSupport;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;

/**
 * Created by bytenoob on 6/9/16.
 */

@EnableSpringDataWebSupport
@EnableGlobalMethodSecurity(prePostEnabled = true)

@SpringBootApplication(exclude = {
        RepositoryRestMvcAutoConfiguration.class,
        JmxAutoConfiguration.class,
        WebSocketAutoConfiguration.class,
        ActiveMQAutoConfiguration.class,
        LeylineErrorController.class
})
@EntityScan("net.masaadora.mall.framework.domain")
@ComponentScan(basePackages = {"net.masadora.mall.framework.domain","net.masadora.mall.framework.infrastructure"})

public abstract class LeylineApp {

}
