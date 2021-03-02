package service;

import entity.RepairRequest;
import service.dto.RepairRequestRegistrationDto;

import java.util.List;

public interface RepairRequestService {

    List<RepairRequest> getListOfActiveRepairRequestsOfUser(String username);

    List<RepairRequest> getListOfAllActiveRepairRequests();

    List<RepairRequest> findAllRepairRequests();

    void createRepairRequest(RepairRequestRegistrationDto repairRequestRegistrationDto);

    void deleteRepairRequestByUsernameAndRepairRequestDescription(String username, String repairRequestDescription);

}
