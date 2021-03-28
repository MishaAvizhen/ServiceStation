package converters.impl;

import converters.Converter;
import dto.RepairRequestWebDto;
import entity.RepairRequest;
import entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import service.UserService;

@Component
public class RepairRequestWebConverter implements Converter<RepairRequest, RepairRequestWebDto> {
    private UserService userService;

    @Autowired
    public RepairRequestWebConverter(UserService userService) {
        this.userService = userService;
    }

    public RepairRequestWebConverter() {
    }

    @Override
    public RepairRequestWebDto convertToDto(RepairRequest entity) {
        User userByUsername = userService.findUserByUsername(entity.getUser().getUsername());
        RepairRequestWebDto repairRequestWebDto = new RepairRequestWebDto();
        repairRequestWebDto.setDateOfRequest(entity.getDateOfRequest());
        repairRequestWebDto.setRepairRequestDescription(entity.getRepairRequestDescription());
        repairRequestWebDto.setCarRemark(entity.getCarRemark());
        repairRequestWebDto.setRepairRequestStatus(entity.getRepairRequestStatus());
        repairRequestWebDto.setRequestId(entity.getId());
        repairRequestWebDto.setUsername(userByUsername.getUsername());
        return repairRequestWebDto;
    }

    @Override
    public RepairRequest convertToEntity(RepairRequestWebDto dto) {
        User userByUsername = userService.findUserByUsername(dto.getUsername());
        RepairRequest repairRequest = new RepairRequest();
        repairRequest.setCarRemark(dto.getCarRemark());
        repairRequest.setDateOfRequest(dto.getDateOfRequest());
        repairRequest.setRepairRequestStatus(dto.getRepairRequestStatus());
        repairRequest.setRepairRequestDescription(dto.getRepairRequestDescription());
        repairRequest.setId(dto.getRequestId());
        repairRequest.setUser(userByUsername);
        return repairRequest;
    }
}
