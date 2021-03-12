import config.DaoConfig;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import repository.AppointmentRepository;
import repository.RepairRecordRepository;
import repository.RepairRequestRepository;
import repository.UserRepository;

public class DaoMain {

    public static void main(String[] args) {
        ApplicationContext context =
                new AnnotationConfigApplicationContext(DaoConfig.class);
        UserRepository userRepository = context.getBean(UserRepository.class);
        RepairRequestRepository repairRequestRepository = (RepairRequestRepository) context.getBean("boom");
        RepairRecordRepository repairRecordRepository = context.getBean(RepairRecordRepository.class, "record");
        AppointmentRepository appointmentRepository = context.getBean(AppointmentRepository.class);

        System.out.println(userRepository.findByUsername("sasha"));
        System.out.println("-----");
        System.out.println(repairRequestRepository.findAll());

        System.out.println("-----");
        System.out.println(repairRecordRepository.findAll());

        System.out.println("-----");
        System.out.println(appointmentRepository.findAll());


//        String[] beanDefinitionNames = context.getBeanDefinitionNames();
//        for (String beanDefinitionName : beanDefinitionNames) {
//            System.out.println(beanDefinitionName);
//        }


    }
}
