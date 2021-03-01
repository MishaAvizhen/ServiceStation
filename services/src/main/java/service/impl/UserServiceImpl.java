package service.impl;


import dao.RepairRecordDao;
import dao.UserDao;
import dao.impl.InMemoryRepairRecordDao;
import dao.impl.InMemoryUserDao;
import entity.RepairRecord;
import entity.User;
import entity.util.RepairRequestStatus;
import entity.util.Role;
import service.UserService;
import service.dto.UserRegistrationDto;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class UserServiceImpl implements UserService {
    private UserDao userDao = InMemoryUserDao.getInstance();
    private RepairRecordDao repairRecordDao = InMemoryRepairRecordDao.getInstance();

    private static UserServiceImpl userService;

    private UserServiceImpl() {
    }

    public static UserServiceImpl getInstance() {
        if (userService == null) {
            userService = new UserServiceImpl();
        }
        return userService;
    }

    @Override
    public List<RepairRecord> getUserRepairRecordList(Long userId) {
        User user = userDao.findById(userId);
        if (user == null) {
            return Collections.emptyList();
        }
        List<RepairRecord> resultList = new ArrayList<>();
        List<RepairRecord> repairRecordList = repairRecordDao.findAll();
        for (RepairRecord repairRecord : repairRecordList) {
            if (repairRecord.getRepairRequest().getRepairRequestStatus().equals(RepairRequestStatus.PROCESSED_STATUS)) {
                resultList.add(repairRecord);
            }
        }
        return resultList;
    }

    @Override
    public User findUserByUsername(String username) {
        for (User user : userDao.findAll()) {
            if (username.equals(user.getUsername())) {
                return user;
            }
        }
        return null;
    }

    @Override
    public List<User> findAllUsers() {
        return userDao.findAll();
    }

    @Override
    public void createUser(UserRegistrationDto userRegistrationDto) {
        User user = new User();
        user.setUsername(userRegistrationDto.getUsername());
        user.setPhoneNumber(userRegistrationDto.getPhoneNumber());
        user.setEmail(userRegistrationDto.getEmail());
        user.setRole(Role.USER_ROLE);
        user.setPassword(userRegistrationDto.getPassword());
        userDao.save(user);

    }

}