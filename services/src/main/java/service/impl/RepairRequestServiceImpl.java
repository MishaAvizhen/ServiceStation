package service.impl;

import com.google.common.base.Preconditions;
import entity.Appointment;
import entity.RepairRequest;
import entity.User;
import entity.consts.RepairRequestStatus;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import repository.RepairRequestRepository;
import service.AppointmentService;
import service.AppointmentSlotService;
import service.RepairRequestService;
import service.UserService;
import service.common.LocalDateTimeOperations;
import service.converters.impl.RepairRequestConverter;
import service.dto.AppointmentSlotDto;
import service.dto.RepairRequestFilterDto;
import service.dto.RepairRequestRegistrationDto;
import service.exceptions.NotContentException;
import service.exceptions.ResourceNotFoundException;

import java.util.*;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toList;

@Service
public class RepairRequestServiceImpl implements RepairRequestService {
    private static final Logger log = Logger.getLogger(RepairRequestServiceImpl.class);

    private RepairRequestRepository repairRequestRepository;

    private UserService userService;

    private RepairRequestConverter repairRequestConverter;

    private AppointmentService appointmentService;

    private AppointmentSlotService appointmentSlotService;

    @Autowired
    public RepairRequestServiceImpl(RepairRequestRepository repairRequestRepository,
                                    UserService userService, RepairRequestConverter repairRequestConverter,
                                    AppointmentService appointmentService,
                                    AppointmentSlotService appointmentSlotService) {
        this.repairRequestRepository = repairRequestRepository;
        this.userService = userService;
        this.repairRequestConverter = repairRequestConverter;
        this.appointmentService = appointmentService;
        this.appointmentSlotService = appointmentSlotService;
    }

    @Override
    public List<RepairRequest> getListOfActiveRepairRequestsOfUser(String username) {
        log.info(String.format("Find active repair request of user: {%s}", username));
        return repairRequestRepository.findAll().stream()
                .filter(request -> request.getUser().getUsername().equals(username) &&
                        request.getRepairRequestStatus().equals(RepairRequestStatus.IN_PROGRESS))
                .collect(Collectors.toList());
    }

    @Override
    public List<RepairRequest> getListOfAllActiveRepairRequests() {
        log.info("Find list of active repair request");
        return repairRequestRepository.findAll().stream()
                .filter(request -> request.getRepairRequestStatus().equals(RepairRequestStatus.IN_PROGRESS))
                .collect(toList());
    }

    @Override
    public List<RepairRequest> findAllRepairRequests() {
        log.info("Find all repair request");
        return repairRequestRepository.findAll();
    }

    @Override
    public List<RepairRequest> findAllRepairRequestsOfUser(String username) {
        log.info(String.format("Find  repair requests of user: {%s} ", username));
        User allRepairRequestsOfUser = userService.findUserByUsername(username);
        if (allRepairRequestsOfUser == null) {
            throw new ResourceNotFoundException("Requests of user " + username + " not found");
        }
        return repairRequestRepository.findAll().stream()
                .filter(request -> request.getUser().getUsername().equals(username))
                .collect(toList());
    }

    @Override
    public RepairRequest findRepairRequestByUsernameAndCarRemark(String username, String carRemark) {
        log.info(String.format("Find  repair request of user: {%s} with car: {%s}", username, carRemark));
        return repairRequestRepository.findAll().stream()
                .filter(request -> request.getUser().getUsername().equals(username) && request.getCarRemark().equals(carRemark))
                .findAny().orElse(null);
    }

    @Override
    public void deleteRepairRequestByUsernameAndRepairRequestDescription(String username, String repairRequestDescription) {

        RepairRequest repairRequest = repairRequestRepository.findAll().stream()
                .filter(request -> request.getUser().getUsername().equals(username) &&
                        request.getRepairRequestDescription().equals(repairRequestDescription))
                .findFirst().orElse(null);
        if (repairRequest != null) {
            repairRequestRepository.delete(repairRequest);
        }
    }

    @Override
    public RepairRequest registerRepairRequest(RepairRequestRegistrationDto repairRequestRegistrationDtoWOSlots,
                                               List<AppointmentSlotDto> appointmentSlotDtos) {
        if (appointmentSlotDtos.isEmpty()) {
            throw new IllegalArgumentException("Slots is empty!!! ");
        }
        validateAppointmentSlotDateNotInPast(appointmentSlotDtos);
        validateIsAvailableAppointmentSlot(appointmentSlotDtos);

        RepairRequest repairRequest = repairRequestConverter.convertToEntity(repairRequestRegistrationDtoWOSlots);
        RepairRequest createdRequest = repairRequestRepository.save(repairRequest);
        List<Appointment> createdAppointments = new ArrayList<>();
        for (AppointmentSlotDto appointmentSlotDto : appointmentSlotDtos) {
            Appointment createdAppointment = appointmentService.createAppointment(appointmentSlotDto,
                    repairRequest.getUser().getId(), createdRequest.getId());
            createdAppointments.add(createdAppointment);
        }
        createdRequest.setAppointments(createdAppointments);
        log.info(String.format("repair request with info : {%s} was created ", repairRequestRegistrationDtoWOSlots.getUsername()));
        return createdRequest;
    }

    @Override
    public RepairRequest registerRepairRequest(RepairRequestRegistrationDto repairRequestRegistrationDto) {
        String clientUsername = repairRequestRegistrationDto.getUsername();
        User client = userService.findUserByUsername(clientUsername);
        if (client == null) {
            throw new ResourceNotFoundException("Client with username " + clientUsername + " not found");
        }
        return registerRepairRequest(repairRequestRegistrationDto,
                Collections.singletonList(repairRequestRegistrationDto.getAppointmentSlotDto()));
    }

    @Override
    public RepairRequest updateRepairRequest(RepairRequestRegistrationDto repairRequestRegistrationDto, Long id) {
        RepairRequest repairRequestToUpdate = findRepairRequestById(id);
        Preconditions.checkNotNull(repairRequestToUpdate, "RepairRequest to update with id " + id + " not found");
        RepairRequest repairRequest = repairRequestConverter.convertToExistingEntity(repairRequestRegistrationDto,
                repairRequestToUpdate);
        validateAppointmentSlotDateNotInPast(repairRequestRegistrationDto.getAppointmentSlotDto());
        log.info(String.format("repair request for {%s} with info : {%s} was updated ",
                repairRequestRegistrationDto.getUsername(), repairRequestRegistrationDto.getCarRemark()));
        return repairRequestRepository.save(repairRequest);
    }

    @Override
    public RepairRequest findRepairRequestById(Long repairRequestId) {
        log.info(String.format("Find repair request  with id= {%s}", repairRequestId));
        Optional<RepairRequest> requestOptional = repairRequestRepository.findById(repairRequestId);
        return requestOptional.orElseThrow(() -> new ResourceNotFoundException(repairRequestId.toString()));
    }

    @Override
    public List<RepairRequest> filterRepairRequest(RepairRequestFilterDto filterDto) {
        List<RepairRequest> allRepairRequests = repairRequestRepository.findAll();
        return allRepairRequests.stream()
                .filter(repairRequest -> filterDto.getUsername() == null || filterDto.getUsername().equals(repairRequest.getUser().getUsername()))
                .filter(repairRequest -> filterDto.getCarRemark() == null || filterDto.getCarRemark().equals(repairRequest.getCarRemark()))
                .filter(repairRequest -> filterDto.getId() == null || filterDto.getId().equals(repairRequest.getId().toString()))
                .filter(repairRequest -> filterDto.getStatus() == null || filterDto.getStatus().equals(repairRequest.getRepairRequestStatus().toString()))
                .collect(Collectors.toList());
    }

    @Override
    public void deleteRepairRequestById(Long repairRequestId) {
        log.info(String.format("Delete repair Request with id=  {%s}", repairRequestId));
        RepairRequest request = findRepairRequestById(repairRequestId);
        if (request == null) {
            throw new NotContentException(repairRequestId.toString());
        }
        repairRequestRepository.deleteById(repairRequestId);
    }

    private void validateAppointmentSlotDateNotInPast(AppointmentSlotDto appointmentSlotDto) {
        Date startDate = LocalDateTimeOperations.convertLocalDateTimeToDate(appointmentSlotDto.getStartDate());
        Date endDate = LocalDateTimeOperations.convertLocalDateTimeToDate(appointmentSlotDto.getEndDate());
        if (startDate.before(new Date()) || endDate.before(new Date())) {
            throw new IllegalArgumentException("Date incorrect! Date in the past");
        }
    }

    private void validateAppointmentSlotDateNotInPast(Collection<AppointmentSlotDto> appointmentSlotDtos) {
        for (AppointmentSlotDto appointmentSlotDto : appointmentSlotDtos) {
            validateAppointmentSlotDateNotInPast(appointmentSlotDto);
        }
    }

    private void validateIsAvailableAppointmentSlot(AppointmentSlotDto appointmentSlotDto) {
        if (!appointmentSlotService.isAppointmentSlotAvailable(appointmentSlotDto)) {
            throw new IllegalArgumentException("Slot is not available " +
                    appointmentSlotDto.getStartDate() + ": " + appointmentSlotDto.getEndDate());
        }
    }

    private void validateIsAvailableAppointmentSlot(Collection<AppointmentSlotDto> appointmentSlotDtos) {
        for (AppointmentSlotDto appointmentSlotDto : appointmentSlotDtos) {
            validateIsAvailableAppointmentSlot(appointmentSlotDto);
        }
    }
}
