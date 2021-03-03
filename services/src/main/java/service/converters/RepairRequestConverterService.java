package service.converters;

import service.dto.RepairRequestRegistrationDto;

public interface RepairRequestConverterService {
    void createRepairRequest(RepairRequestRegistrationDto repairRequestRegistrationDto);
}
