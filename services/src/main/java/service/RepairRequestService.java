package service;

import entity.RepairRequest;

import java.util.List;

public interface RepairRequestService {

    List<RepairRequest> getListOfAllRepairRequestsOfUser(Long userId);
    List<RepairRequest> getListOfActiveRepairRequests();

    List<RepairRequest> findAllRepairRequests();
}
