package service;

import entity.RepairRequest;
import service.dto.RepairRequestRegistrationDto;

import java.util.List;

public interface RepairRequestService {

    List<RepairRequest> getListOfActiveRepairRequestsOfUser(String username);

    List<RepairRequest> getListOfAllActiveRepairRequests();

    List<RepairRequest> findAllRepairRequests();

    List<RepairRequest> findAllRepairRequestsOfUser(String username);

    RepairRequest findRepairRequestByUsernameAndCarRemark(String username, String carRemark);

    void deleteRepairRequestByUsernameAndRepairRequestDescription(String username, String repairRequestDescription);

    RepairRequest registerRepairRequest(RepairRequestRegistrationDto repairRequestRegistrationDto);

    RepairRequest updateRepairRequest(RepairRequestRegistrationDto repairRequestRegistrationDto, RepairRequest repairRequestToUpdate);

    void deleteRepairRequestById(Long repairRequestId);

    RepairRequest findRepairRequestById(Long repairRequestId);

}
