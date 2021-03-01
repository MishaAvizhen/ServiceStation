package service.impl;

import dao.RepairRequestDao;
import dao.impl.InMemoryRepairRequestDao;
import entity.RepairRequest;
import entity.util.RepairRequestStatus;
import service.RepairRequestService;

import java.util.ArrayList;
import java.util.List;

public class RepairRequestServiceImpl implements RepairRequestService {

    private RepairRequestDao repairRequestDao = InMemoryRepairRequestDao.getInstance();

    private static RepairRequestServiceImpl repairRequestService;

    private RepairRequestServiceImpl() {
    }

    public static RepairRequestServiceImpl getInstance() {
        if (repairRequestService == null) {
            repairRequestService = new RepairRequestServiceImpl();

        }
        return repairRequestService;
    }

    @Override
    public List<RepairRequest> getListOfAllRepairRequestsOfUser(Long userId) {
        List<RepairRequest> resultList = new ArrayList<>();
        List<RepairRequest> allRepairRequests = repairRequestDao.findAll();
        for (RepairRequest request : allRepairRequests) {
            if (request.getUser().getId().equals(userId)) {
                resultList.add(request);
            }
        }
        return resultList;
    }

    @Override
    public List<RepairRequest> getListOfActiveRepairRequests() {
        List<RepairRequest> resultList = new ArrayList<>();
        List<RepairRequest> allRepairRequests = repairRequestDao.findAll();
        for (RepairRequest  request : allRepairRequests) {
            if (request.getRepairRequestStatus().equals(RepairRequestStatus.IN_PROGRESS_STATUS)) {
                resultList.add(request);
            }
        }
        return resultList;
    }

    @Override
    public List<RepairRequest> findAllRepairRequests() {
        return repairRequestDao.findAll();
    }
}
