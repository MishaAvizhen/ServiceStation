package service;


import entity.RepairRecord;
import entity.User;

import java.util.List;

public interface UserService  {

    List<RepairRecord> getUserRepairRecordList(Long userId);
    User findUserByUsername(String username);

    List<User> findAllUsers();


}
