package service.converters.impl;

import entity.Appointment;
import entity.enums.RepairRequestStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import repository.AppointmentRepository;
import repository.RepairRequestRepository;
import repository.UserRepository;
import service.dto.AppointmentSlotDto;
import service.dto.MasterWorkInDetails;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static service.common.LocalDateTimeOperations.convertLocalDateTimeToDate;

@Component
public class AppointmentConverter {
    private UserRepository userRepository;
    private RepairRequestRepository repairRequestRepository;
    private AppointmentRepository appointmentRepository;

    @Autowired
    public AppointmentConverter(UserRepository userRepository, RepairRequestRepository repairRequestRepository, AppointmentRepository appointmentRepository) {
        this.userRepository = userRepository;
        this.repairRequestRepository = repairRequestRepository;
        this.appointmentRepository = appointmentRepository;
    }

    public Appointment convertToEntity(AppointmentSlotDto appointmentSlotDto, Long userId, Long repairRequestId) {
        Appointment appointment = new Appointment();
        appointment.setMaster(appointmentSlotDto.getMaster());
        Date startDate = convertLocalDateTimeToDate(appointmentSlotDto.getStartDate());
        appointment.setStartDate(startDate);
        Date endDate = convertLocalDateTimeToDate(appointmentSlotDto.getEndDate());
        appointment.setEndDate(endDate);
        appointment.setSlotStatus(appointmentSlotDto.getSlotStatus());
        appointment.setClient(userRepository.getOne(userId));
        appointment.setNotes("appointments note");
        appointment.setRepairRequest(repairRequestRepository.getOne(repairRequestId));
        return appointment;
    }

    public List<MasterWorkInDetails> convertToEntity(String masterName) {
        List<MasterWorkInDetails> result = new ArrayList<>();
        List<Appointment> list = appointmentRepository.findAll();
        for (Appointment appointment : list) {
            if (appointment.getMaster().getUsername().equals(masterName) &&
                    appointment.getRepairRequest().getRepairRequestStatus().equals(RepairRequestStatus.PROCESSED)) {
                MasterWorkInDetails details = new MasterWorkInDetails();
                Date startDate = appointment.getStartDate();
                Date endDate = appointment.getEndDate();
                String carRemark = appointment.getRepairRequest().getCarRemark();
                Long detailPrice = appointment.getRepairRequest().getRepairRecord().getDetailPrice();
                Long workPrice = appointment.getRepairRequest().getRepairRecord().getWorkPrice();
                String repairRecordDescription = appointment.getRepairRequest().getRepairRecord().getRepairRecordDescription();

                details.setStartDate(startDate);
                details.setEndDate(endDate);
                details.setCarRemark(carRemark);
                details.setDetailPrice(detailPrice);
                details.setWorkPrice(workPrice);
                details.setRepairRecordDescription(repairRecordDescription);
                result.add(details);
            }
        }
        return result;
    }
}
