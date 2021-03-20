import config.ConsoleConfig;
import config.DaoConfig;
import config.ServiceConfig;
import org.apache.log4j.Logger;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import service.impl.UserServiceImpl;
import ui.menu.ConsoleMenu;

public class Main {
    public static void main(String[] args) {
        final Logger log = Logger.getLogger(Main.class);
        log.info(String.format(" Main start"));
        ApplicationContext context =
                new AnnotationConfigApplicationContext(DaoConfig.class, ServiceConfig.class, ConsoleConfig.class);
        ConsoleMenu bean = context.getBean(ConsoleMenu.class);

        bean.initMenuConsole();
        log.info(String.format(" Main end"));

        String[] beanDefinitionNames = context.getBeanDefinitionNames();
        for (String beanDefinitionName : beanDefinitionNames) {
            System.out.println(beanDefinitionName);
        }

    }
}
