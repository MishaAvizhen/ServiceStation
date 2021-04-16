package service;

import entity.Appointment;
import service.dto.AppointmentFilterDto;
import service.dto.AppointmentSlotDto;
import service.dto.RepairRecordFilterDto;

import java.util.List;

public interface AppointmentService {

    List<Appointment> filterAppointments(AppointmentFilterDto filterDto);

    List<Appointment> findAllAppointments();

    Appointment createAppointment(AppointmentSlotDto appointmentSlotDto, Long userId, Long repairRequestId);

}
