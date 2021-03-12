package service.impl;


import entity.RepairRecord;
import entity.User;
import entity.constants.RepairRequestStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import repository.RepairRecordRepository;
import repository.UserRepository;
import service.UserService;
import service.converters.impl.UserConverter;
import service.dto.UserRegistrationDto;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RepairRecordRepository repairRecordRepository;

    @Override
    public List<RepairRecord> getUserRepairRecordList(Long userId) {
        User user = userRepository.findOne(userId);
        if (user == null) {
            return Collections.emptyList();
        }
        List<RepairRecord> resultList = new ArrayList<>();
        List<RepairRecord> repairRecordList = repairRecordRepository.findAll();
        for (RepairRecord repairRecord : repairRecordList) {
            if (repairRecord.getRepairRequest().getRepairRequestStatus().equals(RepairRequestStatus.PROCESSED_STATUS)) {
                resultList.add(repairRecord);
            }
        }
        return resultList;
    }

    @Override
    public User findUserByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    @Override
    public User findUserById(Long userId) {
        return userRepository.findOne(userId);
    }

    @Override
    public List<User> findAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public void deleteUserById(Long userId) {
        userRepository.delete(userId);

    }

    @Override
    public Long getSumWorkPriceAndDetailPrice(Long userId) {
        User userDaoById = userRepository.findOne(userId);
        String username = userDaoById.getUsername();
        Long sumPrice = 0L;
        List<RepairRecord> repairRecordList = repairRecordRepository.findAll();
        for (RepairRecord record : repairRecordList) {
            if (record.getRepairRequest().getUser().getUsername().equals(username)) {
                sumPrice += record.getDetailPrice() + record.getWorkPrice();

            }
        }
        return sumPrice;
    }

    @Override
    public void registerUser(UserRegistrationDto userRegistrationDto) {
        UserConverter userConverter = new UserConverter();
        User user = userConverter.convertToEntity(userRegistrationDto);
        userRepository.save(user);

    }

    @Override
    public void updateUser(UserRegistrationDto userRegistrationDto, User userToUpdate) {
        UserConverter userConverter = new UserConverter();
        User updatedUser = userConverter.convertToExistingEntity(userRegistrationDto, userToUpdate);
        userRepository.saveAndFlush(updatedUser);

    }

}