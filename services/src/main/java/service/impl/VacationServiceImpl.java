package service.impl;

import entity.RepairRequest;
import entity.consts.RepairRequestStatus;
import entity.consts.SlotStatus;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import service.AppointmentSlotService;
import service.RepairRequestService;
import service.VacationService;
import service.dto.AppointmentSlotDto;
import service.dto.RepairRequestRegistrationDto;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class VacationServiceImpl implements VacationService {
    private static final Logger log = Logger.getLogger(VacationServiceImpl.class);

    private RepairRequestService repairRequestService;
    private AppointmentSlotService appointmentSlotService;


    @Autowired
    public VacationServiceImpl(RepairRequestService repairRequestService, AppointmentSlotService appointmentSlotService) {
        this.repairRequestService = repairRequestService;
        this.appointmentSlotService = appointmentSlotService;
    }

    @Override
    public RepairRequest registerVacationRequest(String masterUsername, Date start, Date end) {
        List<AppointmentSlotDto> availableAppointmentSlotsByDates = getVacationSlots(masterUsername, start, end);
        RepairRequestRegistrationDto repairRequestRegistrationDto = new RepairRequestRegistrationDto.Builder()
                .setCarRemark("vacation")
                .setRepairRequestDescription("vacation")
                .setUsername(masterUsername)
                .setDateOfRequest(new Date())
                .setRepairRequestStatus(RepairRequestStatus.IN_PROGRESS)
                .build();
        RepairRequest repairRequest = repairRequestService.registerRepairRequest(repairRequestRegistrationDto, availableAppointmentSlotsByDates);
        log.info(String.format("Vacation for master: {%s}, start date: {%s} - end date: {%s} was created ",
                repairRequestRegistrationDto.getUsername(), start, end));
        return repairRequest;
    }

    private List<AppointmentSlotDto> getVacationSlots(String masterUsername, Date start, Date end) {
        List<AppointmentSlotDto> result = new ArrayList<>();
        List<AppointmentSlotDto> appointmentSlotsByDates = appointmentSlotService.getAllAppointmentSlotsByDates(start, end);
        for (AppointmentSlotDto appointmentSlotsByDate : appointmentSlotsByDates) {
            if (masterUsername.equals(appointmentSlotsByDate.getMaster().getUsername())) {
                appointmentSlotsByDate.setSlotStatus(SlotStatus.NOT_AVAILABLE);
                result.add(appointmentSlotsByDate);
            }
        }
        return result;
    }

}
