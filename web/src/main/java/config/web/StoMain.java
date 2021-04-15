package config.web;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
@ComponentScan("config.web")
@EnableSwagger2
public class StoMain {

    public static void main(String[] args) {
        SpringApplication.run(StoMain.class, args);
    }
}
