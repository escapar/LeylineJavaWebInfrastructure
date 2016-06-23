package moe.src.leyline.framework.interfaces.vc;

import org.springframework.boot.autoconfigure.thymeleaf.ThymeleafAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.thymeleaf.dialect.springdata.SpringDataDialect;

/**
 * Created by POJO on 6/8/16.
 */
@Configuration
public class MasadoraPaginationThymeLeafDialect extends ThymeleafAutoConfiguration {
    @Bean
    public SpringDataDialect springDataDialect() {
        return new SpringDataDialect();
    }
}
