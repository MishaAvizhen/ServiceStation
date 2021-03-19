package service.impl;

import entity.RepairRequest;
import entity.consts.RepairRequestStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import repository.RepairRequestRepository;
import service.RepairRequestService;
import service.converters.impl.RepairRequestConverter;
import service.dto.RepairRequestRegistrationDto;

import java.util.List;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toList;

@Service
public class RepairRequestServiceImpl implements RepairRequestService {
    @Autowired
    private RepairRequestRepository repairRequestRepository;

    @Autowired
    private RepairRequestConverter repairRequestConverter;

    @Override
    public List<RepairRequest> getListOfActiveRepairRequestsOfUser(String username) {
        return repairRequestRepository.findAll().stream()
                .filter(request -> request.getUser().getUsername().equals(username) &&
                        request.getRepairRequestStatus().equals(RepairRequestStatus.IN_PROGRESS))
                .collect(Collectors.toList());

    }

    @Override
    public List<RepairRequest> getListOfAllActiveRepairRequests() {
        return repairRequestRepository.findAll().stream()
                .filter(request -> request.getRepairRequestStatus().equals(RepairRequestStatus.IN_PROGRESS))
                .collect(toList());

    }

    @Override
    public List<RepairRequest> findAllRepairRequests() {
        return repairRequestRepository.findAll();
    }

    @Override
    public RepairRequest findRepairRequestByUsernameAndCarRemark(String username, String carRemark) {
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
    public void registerRepairRequest(RepairRequestRegistrationDto repairRequestRegistrationDto) {
        RepairRequest repairRequest = repairRequestConverter.convertToEntity(repairRequestRegistrationDto);
        repairRequestRepository.save(repairRequest);

    }

    @Override
    public void updateRepairRequest(RepairRequestRegistrationDto repairRequestRegistrationDto, RepairRequest repairRequestToUpdate) {
        RepairRequest repairRequest = repairRequestConverter.convertToExistingEntity(repairRequestRegistrationDto, repairRequestToUpdate);
        repairRequestRepository.save(repairRequest);
    }


}
