package service.impl;

import entity.RepairRequest;
import entity.consts.RepairRequestStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import repository.RepairRequestRepository;
import service.RepairRequestService;
import service.converters.impl.RepairRequestConverter;
import service.dto.RepairRequestRegistrationDto;

import java.util.ArrayList;
import java.util.List;

@Service
public class RepairRequestServiceImpl implements RepairRequestService {
    @Autowired
    private RepairRequestRepository repairRequestRepository;

    @Autowired
    private RepairRequestConverter repairRequestConverter;

    @Override
    public List<RepairRequest> getListOfActiveRepairRequestsOfUser(String username) {
        List<RepairRequest> resultList = new ArrayList<>();
        List<RepairRequest> allRepairRequests = repairRequestRepository.findAll();
        for (RepairRequest request : allRepairRequests) {
            if (request.getUser().getUsername().equals(username) &&
                    request.getRepairRequestStatus().equals(RepairRequestStatus.IN_PROGRESS_STATUS)) {
                resultList.add(request);
            }
        }
        return resultList;
    }

    @Override
    public List<RepairRequest> getListOfAllActiveRepairRequests() {
        List<RepairRequest> resultList = new ArrayList<>();
        List<RepairRequest> allRepairRequests = repairRequestRepository.findAll();
        for (RepairRequest request : allRepairRequests) {
            if (request.getRepairRequestStatus().equals(RepairRequestStatus.IN_PROGRESS_STATUS)) {
                resultList.add(request);
            }
        }
        return resultList;
    }

    @Override
    public List<RepairRequest> findAllRepairRequests() {
        return repairRequestRepository.findAll();
    }

    @Override
    public RepairRequest findRepairRequestByUsernameAndCarRemark(String username, String carRemark) {
        List<RepairRequest> allRepairRequests = repairRequestRepository.findAll();
        for (RepairRequest allRepairRequest : allRepairRequests) {
            if (allRepairRequest.getUser().getUsername().equals(username) && allRepairRequest.getCarRemark().equals(carRemark)) {
                return allRepairRequest;
            }
        }
        return null;
    }


    @Override
    public void deleteRepairRequestByUsernameAndRepairRequestDescription(String username, String repairRequestDescription) {
        List<RepairRequest> allRepairRequests = repairRequestRepository.findAll();
        for (RepairRequest request : allRepairRequests) {
            Long id = request.getId();
            if (request.getUser().getUsername().equals(username) &&
                    request.getRepairRequestDescription().equals(repairRequestDescription)) {
                repairRequestRepository.delete(id);
            }
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
