package moe.src.example.mall;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.orm.jpa.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import moe.src.leyline.framework.LeylineApp;

@EnableJpaRepositories(basePackages = "moe.src.example.mall.business")
@ComponentScan(basePackages = {"moe.src.example.mall.business","moe.src.example.mall.interfaces"})
@EntityScan(basePackages = "moe.src.example.mall.business.domain")

public class App extends LeylineApp{

    public static void main(String[] args) {
        SpringApplication.run(App.class, args);
    }
}
