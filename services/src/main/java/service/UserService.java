package service;


import entity.RepairRecord;
import entity.User;
import service.dto.UserRegistrationDto;

import java.util.List;

public interface UserService {

    List<RepairRecord> getUserRepairRecordList(Long userId);

    User findUserByUsername(String username);

    User findUserById(Long userId);

    List<User> findAllUsers();

    void deleteUserById(Long userId);

    Long getSumWorkPriceAndDetailPrice(Long userId);

    void registerUser(UserRegistrationDto userRegistrationDto);

    void updateUser(UserRegistrationDto userRegistrationDto, User userToUpdate);


}
