package service.beanUtils;

import org.springframework.context.ApplicationContext;
import service.UserService;

public class ServicesBeanUtils {
    private ApplicationContext applicationContext;
    private static ServicesBeanUtils instance = null;

    public ServicesBeanUtils(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
        instance = this;
    }

    public static ServicesBeanUtils getInstance() {
        if (instance == null) {
            throw new RuntimeException("ServicesBeanUtils was not initialized");
        }
        return instance;
    }

    public UserService getUserService() {
         return applicationContext.getBean(UserService.class);
    }
}
