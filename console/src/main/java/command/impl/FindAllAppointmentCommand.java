package command.impl;

import command.MenuCommand;
import entity.Appointment;
import service.AppointmentService;
import service.impl.AppointmentServiceImpl;

import java.util.List;

public class FindAllAppointmentCommand implements MenuCommand {
    private AppointmentService appointmentService = AppointmentServiceImpl.getInstance();

    @Override
    public void execute() {
        List<Appointment> allAppointment = appointmentService.findAllAppointment();
        if (allAppointment.size() != 0) {
            System.out.println(allAppointment);
        } else {
            System.out.println("Appointment not found");

        }
    }
}
