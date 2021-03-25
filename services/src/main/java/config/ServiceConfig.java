package config;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import service.beanUtils.ServicesBeanUtils;

@Configuration
@Import(DaoConfig.class)
@ComponentScan("service")
public class ServiceConfig {
    @Bean
    public ServicesBeanUtils beanUtils(ApplicationContext context) {
        return new ServicesBeanUtils(context);
    }

}
