package com.masadora.mall.framework;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.data.rest.RepositoryRestMvcAutoConfiguration;
import org.springframework.boot.autoconfigure.jms.activemq.ActiveMQAutoConfiguration;
import org.springframework.boot.autoconfigure.jmx.JmxAutoConfiguration;
import org.springframework.boot.autoconfigure.websocket.WebSocketAutoConfiguration;
import org.springframework.boot.orm.jpa.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;

@EnableJpaRepositories(basePackages = "com.masadora.mall.business")
@ComponentScan(basePackages = {"com.masadora.mall"})

@EntityScan(basePackages = "com.masadora.mall")
@EnableGlobalMethodSecurity
@EnableWebSecurity
@SpringBootApplication(exclude = {
        RepositoryRestMvcAutoConfiguration.class,
        JmxAutoConfiguration.class ,
        WebSocketAutoConfiguration.class,
        ActiveMQAutoConfiguration.class,
})
public class LeylineApplication {

	public static void main(String[] args) {
		SpringApplication.run(LeylineApplication.class, args);
	}
}
