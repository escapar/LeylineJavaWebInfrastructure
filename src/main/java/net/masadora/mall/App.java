package net.masadora.mall;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.orm.jpa.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import moe.src.leyline.framework.LeylineApp;

@EnableJpaRepositories(basePackages = "net.masadora.mall.business")
@ComponentScan(basePackages = {"net.masadora.mall.business","net.masadora.mall.interfaces"})
@EntityScan(basePackages = "net.masadora.mall.business.domain")

public class App extends LeylineApp{

    public static void main(String[] args) {
        SpringApplication.run(App.class, args);
    }
}
