package service.impl;

import entity.Appointment;
import entity.RepairRequest;
import entity.consts.RepairRequestStatus;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import repository.RepairRequestRepository;
import service.AppointmentService;
import service.RepairRequestService;
import service.converters.impl.RepairRequestConverter;
import service.dto.RepairRequestRegistrationDto;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toList;

@Service
public class RepairRequestServiceImpl implements RepairRequestService {
    private static final Logger log = Logger.getLogger(RepairRequestServiceImpl.class);

    private RepairRequestRepository repairRequestRepository;

    private RepairRequestConverter repairRequestConverter;

    private AppointmentService appointmentService;

    @Autowired
    public RepairRequestServiceImpl(RepairRequestRepository repairRequestRepository, RepairRequestConverter repairRequestConverter, AppointmentService appointmentService) {
        this.repairRequestRepository = repairRequestRepository;
        this.repairRequestConverter = repairRequestConverter;
        this.appointmentService = appointmentService;
    }

    @Override
    public List<RepairRequest> getListOfActiveRepairRequestsOfUser(String username) {
        log.info(String.format("Find active repair request of user: {%s}", username));
        log.debug(String.format("Find active repair request of user: {%s}", username));
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
    public RepairRequest findRepairRequestByUsernameAndCarRemark(String username, String carRemark) {
        log.info(String.format("Find  repair request of user: {%s} with car: {%s}", username, carRemark));
        log.debug(String.format("Find  repair request of user: {%s} with car: {%s}", username, carRemark));
        return repairRequestRepository.findAll().stream()
                .filter(request -> request.getUser().getUsername().equals(username) && request.getCarRemark().equals(carRemark))
                .findAny().orElse(null);
    }


    @Override
    public void deleteRepairRequestByUsernameAndRepairRequestDescription(String username, String repairRequestDescription) {
        log.info(String.format("delete  repair request of user: {%s} with repair request description: {%s}", username, repairRequestDescription));
        log.debug(String.format("delete  repair request of user: {%s} with repair request description: {%s}", username, repairRequestDescription));
        RepairRequest repairRequest = repairRequestRepository.findAll().stream()
                .filter(request -> request.getUser().getUsername().equals(username) &&
                        request.getRepairRequestDescription().equals(repairRequestDescription))
                .findFirst().orElse(null);
        if (repairRequest != null) {
            repairRequestRepository.delete(repairRequest);
        }
    }

    @Override
    public RepairRequest registerRepairRequest(RepairRequestRegistrationDto repairRequestRegistrationDto) {
        log.info(String.format("repair request with info : {%s} was created ", repairRequestRegistrationDto.getUsername()));
        log.debug(String.format("repair request with info : {%s} was created ", repairRequestRegistrationDto.getUsername()));
        RepairRequest repairRequest = repairRequestConverter.convertToEntity(repairRequestRegistrationDto);
        RepairRequest createdRequest = repairRequestRepository.save(repairRequest);
        Appointment createdAppointment = appointmentService.createAppointment(repairRequestRegistrationDto.getAppointmentSlotDto(),
                repairRequest.getUser().getId(), createdRequest.getId());
        createdRequest.setAppointments(Collections.singletonList(createdAppointment));
        return createdRequest;

    }

    @Override
    public RepairRequest updateRepairRequest(RepairRequestRegistrationDto repairRequestRegistrationDto, RepairRequest repairRequestToUpdate) {
        log.info(String.format("repair request for {%s} with info : {%s} was updated ", repairRequestRegistrationDto.getUsername(), repairRequestRegistrationDto.getCarRemark()));
        log.debug(String.format("repair request for {%s} with info : {%s} was updated ", repairRequestRegistrationDto.getUsername(), repairRequestRegistrationDto.getCarRemark()));
        RepairRequest repairRequest = repairRequestConverter.convertToExistingEntity(repairRequestRegistrationDto, repairRequestToUpdate);
        return repairRequestRepository.save(repairRequest);
    }

    @Override
    public void deleteRepairRequestById(Long repairRequestId) {
        log.info(String.format("Delete repair Request with id=  {%s}", repairRequestId));
        log.debug(String.format("Delete repair Request with id=  {%s}", repairRequestId));
        repairRequestRepository.delete(repairRequestId);

    }

    @Override
    public RepairRequest findRepairRequestById(Long repairRequestId) {
        log.info(String.format("Find repair request  with id= {%s}", repairRequestId));
        log.debug(String.format("Find repair request  with id= {%s}", repairRequestId));
        return repairRequestRepository.findOne(repairRequestId);
    }


}
