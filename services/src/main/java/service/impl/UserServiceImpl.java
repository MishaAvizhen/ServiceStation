package service.impl;


import dao.RepairRecordDao;
import dao.UserDao;
import dao.impl.InMemoryRepairRecordDao;
import dao.impl.InMemoryUserDao;
import entity.RepairRecord;
import entity.User;
import entity.constants.RepairRequestStatus;
import service.UserService;

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
            if (user == null) {
                System.out.println("User not found");
            } else if (username.equals(user.getUsername())) {
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
    public void deleteUserById(Long userId) {
        userDao.deleteById(userId);


    }

    @Override
    public Long getSumWorkPriceAndDetailPrice(Long userId) {
        User userDaoById = userDao.findById(userId);
        String username = userDaoById.getUsername();
        Long sumPrice = 0L;
        List<RepairRecord> repairRecordList = repairRecordDao.findAll();
        for (RepairRecord record : repairRecordList) {
            if (record.getRepairRequest().getUser().getUsername().equals(username)) {
                sumPrice += record.getDetailPrice() + record.getWorkPrice() ;

            }
        }
        return sumPrice;
    }

}