package service.converters.impl;

import dao.RepairRequestDao;
import dao.impl.InMemoryRepairRequestDao;
import entity.RepairRequest;
import entity.User;
import entity.constants.RepairRequestStatus;
import service.UserService;
import service.converters.RepairRequestConverterService;
import service.dto.RepairRequestRegistrationDto;
import service.impl.UserServiceImpl;

public class RepairRequestConverterImpl implements RepairRequestConverterService {
    private UserService userService = UserServiceImpl.getInstance();
    private RepairRequestDao repairRequestDao = InMemoryRepairRequestDao.getInstance();

    private static RepairRequestConverterImpl repairRequestConverter;

    private RepairRequestConverterImpl() {
    }

    public static RepairRequestConverterImpl getInstance() {
        if (repairRequestConverter == null) {
            repairRequestConverter = new RepairRequestConverterImpl();
        }
        return repairRequestConverter;
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
}
