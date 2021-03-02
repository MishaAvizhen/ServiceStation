package service.impl;

import dao.RepairRequestDao;
import dao.UserDao;
import dao.impl.InMemoryRepairRequestDao;
import dao.impl.InMemoryUserDao;
import entity.RepairRequest;
import entity.User;
import entity.util.RepairRequestStatus;
import service.RepairRequestService;
import service.UserService;
import service.dto.RepairRequestRegistrationDto;

import java.util.ArrayList;
import java.util.List;

public class RepairRequestServiceImpl implements RepairRequestService {
    private UserService userService = UserServiceImpl.getInstance();
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
    public List<RepairRequest> getListOfActiveRepairRequestsOfUser(String username) {
        List<RepairRequest> resultList = new ArrayList<>();
        List<RepairRequest> allRepairRequests = repairRequestDao.findAll();
        for (RepairRequest request : allRepairRequests) {
            if (request.getUser().getUsername().equals(username)&&
                    request.getRepairRequestStatus().equals(RepairRequestStatus.IN_PROGRESS_STATUS)) {
                resultList.add(request);
            }
        }
        return resultList;
    }

    @Override
    public List<RepairRequest> getListOfAllActiveRepairRequests() {
        List<RepairRequest> resultList = new ArrayList<>();
        List<RepairRequest> allRepairRequests = repairRequestDao.findAll();
        for (RepairRequest request : allRepairRequests) {
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

    @Override
    public void createRepairRequest(RepairRequestRegistrationDto repairRequestRegistrationDto) {
        User userByUsername = userService.findUserByUsername(repairRequestRegistrationDto.getUsername());
        RepairRequest repairRequest = new RepairRequest();
        repairRequest.setDateOfRequest(repairRequestRegistrationDto.getDateOfRequest());
        repairRequest.setCarRemark(repairRequestRegistrationDto.getCarRemark());
        repairRequest.setRepairRequestStatus(RepairRequestStatus.IN_PROGRESS_STATUS);
        repairRequest.setRepairRequestDescription(repairRequestRegistrationDto.getRepairRequestDescription());
        repairRequest.setUser(userByUsername);
        repairRequestDao.save(repairRequest);
    }

    @Override
    public void deleteRepairRequestByUsernameAndRepairRequestDescription(String username, String repairRequestDescription) {
        List<RepairRequest> allRepairRequests = repairRequestService.findAllRepairRequests();
        for (RepairRequest request : allRepairRequests) {
            Long id = request.getId();
            if (request.getUser().getUsername().equals(username) &&
                    request.getRepairRequestDescription().equals(repairRequestDescription)) {
                repairRequestDao.deleteById(id);
            }
        }
    }


}
