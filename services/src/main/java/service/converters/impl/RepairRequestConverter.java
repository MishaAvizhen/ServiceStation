package service.converters.impl;

import entity.RepairRequest;
import entity.User;
import service.UserService;
import service.converters.Converter;
import service.dto.RepairRequestRegistrationDto;
import service.impl.UserServiceImpl;

public class RepairRequestConverter implements Converter<RepairRequest, RepairRequestRegistrationDto> {
    private UserService userService = UserServiceImpl.getInstance();

    public RepairRequestConverter() {
    }

    @Override
    public RepairRequest convertToEntity(RepairRequestRegistrationDto dto) {
        RepairRequest repairRequest = new RepairRequest();
        return convertToExistingEntity(dto, repairRequest);

    }

    @Override
    public RepairRequest convertToExistingEntity(RepairRequestRegistrationDto dto, RepairRequest entity) {
        User userByUsername = userService.findUserByUsername(dto.getUsername());
        entity.setCarRemark(dto.getCarRemark());
        entity.setDateOfRequest(dto.getDateOfRequest());
        entity.setRepairRequestDescription(dto.getRepairRequestDescription());
        entity.setUser(userByUsername);
        entity.setRepairRequestStatus(dto.getRepairRequestStatus());

        return entity;
    }

    @Override
    public RepairRequestRegistrationDto convertToDto(RepairRequest entity) {
        return new RepairRequestRegistrationDto.Builder()
                .setDateOfRequest(entity.getDateOfRequest())
                .setRepairRequestDescription(entity.getRepairRequestDescription())
                .setRepairRequestStatus(entity.getRepairRequestStatus())
                .setCarRemark(entity.getCarRemark())
                .setUsername(entity.getUser().getUsername())
                .build();
    }

}
