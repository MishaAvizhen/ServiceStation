import config.ServiceConfig;
import entity.Appointment;
import entity.RepairRecord;
import entity.RepairRequest;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import service.*;

import java.util.List;

public class ServicesMain {
    public static void main(String[] args) {
        ApplicationContext context =
                new AnnotationConfigApplicationContext(ServiceConfig.class);

        UserService userService = context.getBean(UserService.class);
        RepairRequestService repairRequestService = context.getBean(RepairRequestService.class);
        RepairRecordService repairRecordService = context.getBean(RepairRecordService.class);
        AppointmentService appointmentService = context.getBean(AppointmentService.class);
        Long sumWorkPriceAndDetailPrice = userService.getSumWorkPriceAndDetailPrice(5L);
        System.out.println("sasha: " + sumWorkPriceAndDetailPrice);
        List<RepairRequest> listOfAllActiveRepairRequests = repairRequestService.getListOfAllActiveRepairRequests();
        System.out.println("List active.3" + listOfAllActiveRepairRequests);
        List<Appointment> allAppointment = appointmentService.findAllAppointment();
        System.out.println("all appoint: " + allAppointment);
        RepairRecord repairRecordByUsernameAndRepairRecordDescription = repairRecordService.findRepairRecordByUsernameAndRepairRecordDescription("misha", "to");
        System.out.println(repairRecordByUsernameAndRepairRecordDescription);


    }

}