package service.converters.impl;

import entity.Appointment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import repository.RepairRequestRepository;
import repository.UserRepository;
import service.common.LocalDateTimeOperations;
import service.dto.AppointmentSlotDto;

import java.util.Date;

@Component
public class AppointmentConverter {
    private UserRepository userRepository;
    private RepairRequestRepository repairRequestRepository;

    @Autowired
    public AppointmentConverter(UserRepository userRepository, RepairRequestRepository repairRequestRepository) {
        this.userRepository = userRepository;
        this.repairRequestRepository = repairRequestRepository;
    }


    public Appointment convertToEntity(AppointmentSlotDto appointmentSlotDto, Long userId, Long repairRequestId) {
        Appointment appointment = new Appointment();
        appointment.setMaster(appointmentSlotDto.getMaster());
        Date startDate = LocalDateTimeOperations.convertLocalDateTimeToDate(appointmentSlotDto.getStartDate());
        appointment.setStartDate(startDate);
        Date endDate = LocalDateTimeOperations.convertLocalDateTimeToDate(appointmentSlotDto.getEndDate());
        appointment.setEndDate(endDate);
        appointment.setSlotStatus(appointmentSlotDto.getSlotStatus());
        appointment.setClient(userRepository.getOne(userId));
        appointment.setNotes("appointments note");
        appointment.setRepairRequest(repairRequestRepository.getOne(repairRequestId));
        return appointment;
    }
}
