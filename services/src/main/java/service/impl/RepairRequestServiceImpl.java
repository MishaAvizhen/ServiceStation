package service.impl;

import entity.Appointment;
import entity.RepairRequest;
import entity.consts.RepairRequestStatus;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import repository.RepairRequestRepository;
import service.AppointmentService;
import service.AppointmentSlotService;
import service.RepairRequestService;
import service.common.LocalDateTimeOperations;
import service.converters.impl.RepairRequestConverter;
import service.dto.AppointmentSlotDto;
import service.dto.RepairRequestRegistrationDto;

import java.util.*;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toList;

@Service
public class RepairRequestServiceImpl implements RepairRequestService {
    private static final Logger log = Logger.getLogger(RepairRequestServiceImpl.class);

    private RepairRequestRepository repairRequestRepository;

    private RepairRequestConverter repairRequestConverter;

    private AppointmentService appointmentService;

    private AppointmentSlotService appointmentSlotService;

    @Autowired
    public RepairRequestServiceImpl(RepairRequestRepository repairRequestRepository,
                                    RepairRequestConverter repairRequestConverter,
                                    AppointmentService appointmentService,
                                    AppointmentSlotService appointmentSlotService) {
        this.repairRequestRepository = repairRequestRepository;
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
        log.info(String.format("Find list of active repair request"));
        return repairRequestRepository.findAll().stream()
                .filter(request -> request.getRepairRequestStatus().equals(RepairRequestStatus.IN_PROGRESS))
                .collect(toList());

    }

    @Override
    public List<RepairRequest> findAllRepairRequests() {
        log.info(String.format("Find all repair request"));
        return repairRequestRepository.findAll();
    }

    @Override
    public List<RepairRequest> findAllRepairRequestsOfUser(String username) {
        log.info(String.format("Find  repair requests of user: {%s} ", username));
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

        return registerRepairRequest(repairRequestRegistrationDto, Collections.singletonList(repairRequestRegistrationDto.getAppointmentSlotDto()));
    }

    @Override
    public RepairRequest updateRepairRequest(RepairRequestRegistrationDto repairRequestRegistrationDto, RepairRequest repairRequestToUpdate) {
        RepairRequest repairRequest = repairRequestConverter.convertToExistingEntity(repairRequestRegistrationDto, repairRequestToUpdate);
        validateAppointmentSlotDateNotInPast(repairRequestRegistrationDto.getAppointmentSlotDto());
        log.info(String.format("repair request for {%s} with info : {%s} was updated ", repairRequestRegistrationDto.getUsername(), repairRequestRegistrationDto.getCarRemark()));
        return repairRequestRepository.save(repairRequest);
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
            throw new IllegalArgumentException("Slot is not available");
        }
    }

    private void validateIsAvailableAppointmentSlot(Collection<AppointmentSlotDto> appointmentSlotDtos) {
        for (AppointmentSlotDto appointmentSlotDto : appointmentSlotDtos) {
            validateIsAvailableAppointmentSlot(appointmentSlotDto);
        }
    }

    @Override
    public void deleteRepairRequestById(Long repairRequestId) {
        log.info(String.format("Delete repair Request with id=  {%s}", repairRequestId));
        repairRequestRepository.deleteById(repairRequestId);

    }

    @Override
    public RepairRequest findRepairRequestById(Long repairRequestId) {
        log.info(String.format("Find repair request  with id= {%s}", repairRequestId));
        Optional<RepairRequest> requestOptional = repairRequestRepository.findById(repairRequestId);
        return requestOptional.orElse(null);
    }
}
