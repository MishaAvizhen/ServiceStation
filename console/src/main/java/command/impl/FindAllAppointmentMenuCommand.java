package command.impl;

import command.MenuCommand;
import entity.Appointment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import service.AppointmentService;

import java.util.List;

@Component
public class FindAllAppointmentMenuCommand implements MenuCommand {
    @Autowired
    private AppointmentService appointmentService;

    @Override
    public void execute() {
        List<Appointment> allAppointment = appointmentService.findAllAppointment();
        if (allAppointment.size() != 0) {
            System.out.println(allAppointment);
        } else {
            System.out.println("Appointment not found");

        }
    }

    @Override
    public int getHandledMenuNumber() {
        return 6;
    }
}
