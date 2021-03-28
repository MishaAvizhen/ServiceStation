package config.web;

import config.ServiceConfig;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@Import(ServiceConfig.class)
@ComponentScan({"handler", "converters.impl"})
public class WebConfig {

}
