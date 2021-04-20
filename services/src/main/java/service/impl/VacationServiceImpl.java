package service.impl;

import entity.RepairRequest;
import entity.User;
import entity.enums.RepairRequestStatus;
import entity.enums.SlotStatus;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import service.AppointmentSlotService;
import service.RepairRequestService;
import service.UserService;
import service.VacationService;
import service.dto.AppointmentSlotDto;
import service.dto.RepairRequestRegistrationDto;
import service.dto.VacationRegistrationDto;
import service.exceptions.ResourceNotFoundException;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class VacationServiceImpl implements VacationService {
    private static final Logger log = Logger.getLogger(VacationServiceImpl.class);

    private RepairRequestService repairRequestService;
    private AppointmentSlotService appointmentSlotService;
    private UserService userService;

    @Autowired
    public VacationServiceImpl(RepairRequestService repairRequestService, AppointmentSlotService appointmentSlotService, UserService userService) {
        this.repairRequestService = repairRequestService;
        this.appointmentSlotService = appointmentSlotService;
        this.userService = userService;
    }

    @Override
    public RepairRequest registerVacationRequest(VacationRegistrationDto vacationRegistrationDto) {
        String masterName = vacationRegistrationDto.getMasterName();
        Date startDate = vacationRegistrationDto.getStartDate();
        Date endDate = vacationRegistrationDto.getEndDate();
        User master = userService.findUserByUsername(masterName);
        if (master == null) {
            throw new ResourceNotFoundException("Master with username " + masterName + " not found");
        }
        List<AppointmentSlotDto> availableAppointmentSlotsByDates = getVacationSlots(masterName, startDate, endDate);
        RepairRequestRegistrationDto repairRequestRegistrationDto = new RepairRequestRegistrationDto.Builder()
                .setCarRemark("vacation")
                .setRepairRequestDescription("vacation")
                .setUsername(masterName)
                .setDateOfRequest(new Date())
                .setRepairRequestStatus(RepairRequestStatus.IN_PROGRESS)
                .build();
        RepairRequest repairRequest = repairRequestService.registerRepairRequest(repairRequestRegistrationDto, availableAppointmentSlotsByDates);
        log.info(String.format("Vacation for master: {%s}, start date: {%s} - end date: {%s} was created ",
                repairRequestRegistrationDto.getUsername(), startDate, endDate));
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
