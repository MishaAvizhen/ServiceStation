package service.converters;

import entity.User;
import entity.util.Role;
import service.dto.RepairRequestRegistrationDto;
import service.dto.UserRegistrationDto;

public interface ConverterEntityDtoService {
    void createUser(UserRegistrationDto userRegistrationDto);
    void createRepairRequest(RepairRequestRegistrationDto repairRequestRegistrationDto);


}
