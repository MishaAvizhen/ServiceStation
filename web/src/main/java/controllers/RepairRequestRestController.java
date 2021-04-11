package controllers;

import converters.impl.AppointmentSlotDtoToAppointmentSlotWebDtoConverter;
import converters.impl.RepairRequestFromRegistrationWebDtoToRegistrationDtoConverter;
import converters.impl.RepairRequestFromWebDtoToRegistrationDtoConverter;
import dto.AppointmentSlotWebDto;
import dto.RepairRequestRegistrationWebDto;
import dto.RepairRequestWebDto;
import entity.RepairRequest;
import entity.User;
import exceptions.NotContentException;
import exceptions.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;
import service.AppointmentSlotService;
import service.RepairRequestService;
import service.UserService;
import service.VacationService;
import service.common.LocalDateTimeOperations;
import service.dto.AppointmentSlotDto;
import service.dto.RepairRequestRegistrationDto;
import service.dto.VacationRegistrationDto;

import java.security.Principal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import static service.common.LocalDateTimeOperations.convertLocalDateTimeToDate;

@RestController
@RequestMapping(value = "/api/requests")
public class RepairRequestRestController {
    private RepairRequestService repairRequestService;
    private UserService userService;
    private RepairRequestFromRegistrationWebDtoToRegistrationDtoConverter repairRequestFromRegistrationWebDtoToRegistrationDtoConverter;
    private AppointmentSlotService appointmentSlotService;
    private AppointmentSlotDtoToAppointmentSlotWebDtoConverter appointmentSlotDtoToAppointmentSlotWebDtoConverter;
    private RepairRequestFromWebDtoToRegistrationDtoConverter repairRequestFromWebDtoToRegistrationDtoConverter;
    private VacationService vacationService;

    @Autowired
    public RepairRequestRestController(RepairRequestService repairRequestService,
                                       UserService userService, RepairRequestFromRegistrationWebDtoToRegistrationDtoConverter repairRequestFromRegistrationWebDtoToRegistrationDtoConverter,
                                       AppointmentSlotService appointmentSlotService,
                                       AppointmentSlotDtoToAppointmentSlotWebDtoConverter appointmentSlotDtoToAppointmentSlotWebDtoConverter,
                                       RepairRequestFromWebDtoToRegistrationDtoConverter repairRequestFromWebDtoToRegistrationDtoConverter, VacationService vacationService) {
        this.repairRequestService = repairRequestService;
        this.userService = userService;
        this.repairRequestFromRegistrationWebDtoToRegistrationDtoConverter = repairRequestFromRegistrationWebDtoToRegistrationDtoConverter;
        this.appointmentSlotService = appointmentSlotService;
        this.appointmentSlotDtoToAppointmentSlotWebDtoConverter = appointmentSlotDtoToAppointmentSlotWebDtoConverter;
        this.repairRequestFromWebDtoToRegistrationDtoConverter = repairRequestFromWebDtoToRegistrationDtoConverter;
        this.vacationService = vacationService;
    }

    @GetMapping
    public List<RepairRequest> getAllRepairRequests(@RequestParam(value = "username", required = false) String username,
                                                    @RequestParam(value = "carRemark", required = false) String carRemark,
                                                    @RequestParam(value = "requestId", required = false) String requestId,
                                                    @RequestParam(value = "status", required = false) String status) {
        List<RepairRequest> allRepairRequests = repairRequestService.findAllRepairRequests();
        return allRepairRequests.stream()
                .filter(repairRequest -> username == null || username.equals(repairRequest.getUser().getUsername()))
                .filter(repairRequest -> carRemark == null || carRemark.equals(repairRequest.getCarRemark()))
                .filter(repairRequest -> requestId == null || requestId.equals(repairRequest.getId().toString()))
                .filter(repairRequest -> status == null || status.equals(repairRequest.getRepairRequestStatus().toString()))
                .collect(Collectors.toList());
    }

    @GetMapping("/username")
    public List<RepairRequest> getAllRepairRequestsOfUser(Principal principal) {
        String username = principal.getName();
        List<RepairRequest> allRepairRequestsOfUser = repairRequestService.findAllRepairRequestsOfUser(username);
        if (allRepairRequestsOfUser == null) {
            throw new ResourceNotFoundException("Requests of user " + username + " not found");
        }
        return allRepairRequestsOfUser;
    }

    @DeleteMapping("/{requestId}")
    public void deleteRepairRequestById(@PathVariable Long requestId) {
        RepairRequest requestToDelete = repairRequestService.findRepairRequestById(requestId);
        if (requestToDelete == null) {
            throw new NotContentException(requestId.toString());
        } else {
            repairRequestService.deleteRepairRequestById(requestId);

        }
    }

    @PutMapping("/{requestId}")
    public RepairRequest getUpdatedRepairRequest(@PathVariable Long requestId,
                                                 @RequestBody RepairRequestWebDto repairRequestWebDto) {
        RepairRequest repairRequestToUpdate = repairRequestService.findRepairRequestById(requestId);
        if (repairRequestToUpdate == null) {
            throw new ResourceNotFoundException("RepairRequest to update with id " + repairRequestWebDto.getRequestId() + " not found");

        } else {
            RepairRequestRegistrationDto repairRequestRegistrationDto =
                    repairRequestFromWebDtoToRegistrationDtoConverter.convertFromSourceDtoToTargetDto(repairRequestWebDto);
            return repairRequestService.updateRepairRequest(repairRequestRegistrationDto, repairRequestToUpdate);
        }
    }

    @PostMapping("/create")
    public RepairRequest getCreatedRepairRequest(@RequestBody RepairRequestRegistrationWebDto repairRequestRegistrationWebDto) {
        String clientUsername = repairRequestRegistrationWebDto.getClientUsername();
        User client = userService.findUserByUsername(clientUsername);
        if (client == null) {
            throw new ResourceNotFoundException("Client with username " + clientUsername + " not found");
        }
        RepairRequestRegistrationDto repairRequestRegistrationDto =
                repairRequestFromRegistrationWebDtoToRegistrationDtoConverter.convertFromSourceDtoToTargetDto(repairRequestRegistrationWebDto);
        return repairRequestService.registerRepairRequest(repairRequestRegistrationDto);
    }

    @PostMapping("/vacation")
    public RepairRequest getCreatedRepairRequestForVacation(@RequestBody VacationRegistrationDto vacationRegistrationDto) {
        String masterName = vacationRegistrationDto.getMasterName();
        Date startDate = vacationRegistrationDto.getStartDate();
        Date endDate = vacationRegistrationDto.getEndDate();
        User master = userService.findUserByUsername(masterName);
        if (master == null) {
            throw new ResourceNotFoundException("Master with username " + masterName + " not found");
        }

        return vacationService.registerVacationRequest(masterName, startDate, endDate);
    }

    @GetMapping("/slots")
    public List<AppointmentSlotWebDto> getAvailableSlotsOnTargetDate(@RequestParam(value = "targetDate")
                                                                 @DateTimeFormat(pattern = "yyyy-MM-dd") Date targetDate) {
        List<AppointmentSlotWebDto> slotWebDtos = new ArrayList<>();
        List<AppointmentSlotDto> availableAppointmentSlotsByDate = appointmentSlotService.getAvailableAppointmentSlotsByDate(targetDate);
        for (AppointmentSlotDto appointmentSlotDto : availableAppointmentSlotsByDate) {
            AppointmentSlotWebDto appointmentSlotWebDto =
                    appointmentSlotDtoToAppointmentSlotWebDtoConverter.convertFromSourceDtoToTargetDto(appointmentSlotDto);
            slotWebDtos.add(appointmentSlotWebDto);
        }
        return slotWebDtos;
    }

}
