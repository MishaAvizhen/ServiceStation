package service;

import entity.Appointment;
import service.dto.AppointmentSlotDto;

import java.util.List;

public interface AppointmentService {
    Appointment createAppointment(AppointmentSlotDto appointmentSlotDto, Long userId);

    List<Appointment> findAllAppointment();
}
