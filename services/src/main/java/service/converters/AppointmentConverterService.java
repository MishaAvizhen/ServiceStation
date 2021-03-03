package service.converters;

import entity.Appointment;
import service.dto.AppointmentSlotDto;

public interface AppointmentConverterService {
    Appointment createAppointment(AppointmentSlotDto appointmentSlotDto, Long userId);
}
