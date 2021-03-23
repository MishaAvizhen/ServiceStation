package service.impl;


import entity.RepairRecord;
import entity.User;
import entity.consts.RepairRequestStatus;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import repository.RepairRecordRepository;
import repository.UserRepository;
import service.UserService;
import service.converters.impl.UserConverter;
import service.dto.UserRegistrationDto;

import java.util.Collections;
import java.util.List;

import static java.util.stream.Collectors.toList;

@Service
public class UserServiceImpl implements UserService {
    private static final Logger log = Logger.getLogger(UserServiceImpl.class);

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
        return repairRecordRepository.findAll().stream()
                .filter(record -> record.getRepairRequest().getRepairRequestStatus().equals(RepairRequestStatus.PROCESSED))
                .collect(toList());
    }

    @Override
    public User findUserByUsername(String username) {
        log.info(String.format("Find user with name:  {%s}", username));
        log.debug(String.format("Find user with name: {%s}", username));
        return userRepository.findByUsername(username);
    }

    @Override
    public User findUserById(Long userId) {
        log.info(String.format("Find user with id= {%s}", userId));
        log.debug(String.format("Find user with id= {%s}", userId));
        return userRepository.findOne(userId);
    }

    @Override
    public List<User> findAllUsers() {
        log.info(String.format("Find all users"));
        return userRepository.findAll();
    }

    @Override
    public void deleteUserById(Long userId) {
        log.info(String.format("Delete user with id=  {%s}", userId));
        log.debug(String.format("Delete user with id=  {%s}", userId));
        userRepository.delete(userId);

    }

    @Override
    public Long getSumWorkPriceAndDetailPrice(Long userId) {
        User userDaoById = userRepository.findOne(userId);
        String username = userDaoById.getUsername();
        return repairRecordRepository.findAll().stream()
                .filter(e -> e.getRepairRequest().getUser().getUsername().equals(username))
                .map(record -> record.getDetailPrice() + record.getWorkPrice())
                .reduce(0L, (sumPrice, repRecPrice) -> sumPrice + repRecPrice);
    }

    @Override
    public void registerUser(UserRegistrationDto userRegistrationDto) {
        log.info(String.format("user with info:{%s} was created ", userRegistrationDto.toString()));
        log.debug(String.format("user with info:{%s} was created ", userRegistrationDto.toString()));
        UserConverter userConverter = new UserConverter();
        User user = userConverter.convertToEntity(userRegistrationDto);
        userRepository.save(user);

    }

    @Override
    public void updateUser(UserRegistrationDto userRegistrationDto, User userToUpdate) {
        User byUsername = userRepository.findByUsername(userToUpdate.getUsername());
        if (byUsername == null) {
            log.error(String.format("user with username:{%s} not found ", userToUpdate.getUsername()));
            throw new RuntimeException("user not found");
        }
        log.info(String.format("user with info:{%s} was updated ", userToUpdate.toString()));
        log.debug(String.format("user with info:{%s} was updated ", userToUpdate.toString()));
        UserConverter userConverter = new UserConverter();
        User updatedUser = userConverter.convertToExistingEntity(userRegistrationDto, userToUpdate);
        userRepository.saveAndFlush(updatedUser);

    }

}