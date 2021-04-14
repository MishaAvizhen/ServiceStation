package service;

import entity.Appointment;
import service.dto.AppointmentSlotDto;

import java.util.List;

public interface AppointmentService {

    List<Appointment> findAllAppointment();

    Appointment createAppointment(AppointmentSlotDto appointmentSlotDto, Long userId, Long repairRequestId);

    Appointment findAppointmentByRepairRequestId(Long repairRequestId);
}
