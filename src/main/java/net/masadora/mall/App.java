package net.masadora.mall;

import net.masadora.mall.framework.FrameworkApp;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.orm.jpa.EntityScan;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;


@EnableJpaRepositories(basePackages = "net.masadora.mall.business")
@ComponentScan(basePackages = {"net.masadora.mall.business", "net.masadora.mall.interfaces"})
@EntityScan(basePackages = "net.masadora.mall.business.domain")
@EnableCaching

public class App extends FrameworkApp {


    public static void main(String[] args) {
        SpringApplication.run(App.class, args);
    }
}
