package moe.src.leyline;

import moe.src.leyline.framework.LeylineApp;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.orm.jpa.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@EnableJpaRepositories(basePackages = "moe.src.leyline.business")
@ComponentScan(basePackages = {"moe.src.leyline.business","moe.src.leyline.interfaces"})
@EntityScan(basePackages = "moe.src.leyline.business.domain")

public class App extends LeylineApp{
    public static void main(String[] args) {
        SpringApplication.run(App.class, args);
    }
}
