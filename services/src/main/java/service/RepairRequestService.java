package service;

import entity.RepairRequest;
import service.dto.RepairRequestRegistrationDto;

import java.util.List;

public interface RepairRequestService {

    List<RepairRequest> getListOfActiveRepairRequestsOfUser(String username);

    List<RepairRequest> getListOfAllActiveRepairRequests();

    List<RepairRequest> findAllRepairRequests();

    RepairRequest findRepairRequestByUsernameAndCarRemark(String username, String carRemark);

    void deleteRepairRequestByUsernameAndRepairRequestDescription(String username, String repairRequestDescription);

    void registerRepairRequest(RepairRequestRegistrationDto repairRequestRegistrationDto);

    void updateRepairRequest(RepairRequestRegistrationDto repairRequestRegistrationDto, RepairRequest repairRequestToUpdate);

    void deleteRepairRequestById(Long repairRequestId);

}
