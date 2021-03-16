package service.impl;

import dao.RepairRequestDao;
import dao.impl.InMemoryRepairRequestDao;
import entity.RepairRequest;
import entity.constants.RepairRequestStatus;
import service.RepairRequestService;
import service.converters.impl.RepairRequestConverter;
import service.dto.RepairRequestRegistrationDto;

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
    public List<RepairRequest> getListOfActiveRepairRequestsOfUser(String username) {
        List<RepairRequest> resultList = new ArrayList<>();
        List<RepairRequest> allRepairRequests = repairRequestDao.findAll();
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
    public RepairRequest findRepairRequestByUsernameAndCarRemark(String username, String carRemark) {
        List<RepairRequest> allRepairRequests = repairRequestService.findAllRepairRequests();
        for (RepairRequest allRepairRequest : allRepairRequests) {
            if (allRepairRequest.getUser().getUsername().equals(username) && allRepairRequest.getCarRemark().equals(carRemark)) {
                return allRepairRequest;
            }
        }
        return null;
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

    @Override
    public void registerRepairRequest(RepairRequestRegistrationDto repairRequestRegistrationDto) {
        RepairRequestConverter repairRequestConverter = new RepairRequestConverter();
        RepairRequest repairRequest = repairRequestConverter.convertToEntity(repairRequestRegistrationDto);
        repairRequestDao.save(repairRequest);

    }

    @Override
    public void updateRepairRequest(RepairRequestRegistrationDto repairRequestRegistrationDto, RepairRequest repairRequestToUpdate) {
        RepairRequestConverter repairRequestConverter = new RepairRequestConverter();
        RepairRequest repairRequest = repairRequestConverter.convertToExistingEntity(repairRequestRegistrationDto, repairRequestToUpdate);
        repairRequestDao.update(repairRequest);
    }


}
