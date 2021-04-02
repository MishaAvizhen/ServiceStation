package config.web;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan("config.web")
public class StoMain {

    public static void main(String[] args) {
        SpringApplication.run(StoMain.class, args);
    }
}
