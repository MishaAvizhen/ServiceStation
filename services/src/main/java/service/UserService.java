package service;


import entity.RepairRecord;
import entity.User;
import service.dto.UserRegistrationDto;

import java.util.List;

public interface UserService {

    List<RepairRecord> getUserRepairRecordList(Long userId);

    User findUserByUsername(String username);

    List<User> findAllUsers();

    void deleteUserById(Long userId);

    Long getSumWorkPriceAndDetailPrice(Long userId);



}
