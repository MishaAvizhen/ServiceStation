import config.ConsoleConfig;
import config.DaoConfig;
import config.ServiceConfig;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import repository.UserRepository;
import ui.menu.ConsoleMenu;

public class Main {
    public static void main(String[] args) {

        ApplicationContext context =
                new AnnotationConfigApplicationContext(DaoConfig.class, ServiceConfig.class, ConsoleConfig.class);
        ConsoleMenu bean = context.getBean(ConsoleMenu.class);

        bean.initMenuConsole();

        String[] beanDefinitionNames = context.getBeanDefinitionNames();
        for (String beanDefinitionName : beanDefinitionNames) {
            System.out.println(beanDefinitionName);
        }

    }
}
