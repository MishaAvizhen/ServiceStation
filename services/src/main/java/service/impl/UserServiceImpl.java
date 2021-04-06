package service.impl;


import entity.RepairRecord;
import entity.User;
import entity.consts.RepairRequestStatus;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import repository.RepairRecordRepository;
import repository.UserRepository;
import service.UserService;
import service.converters.impl.UserConverter;
import service.dto.UserRegistrationDto;

import java.util.List;
import java.util.Optional;

import static java.util.stream.Collectors.toList;

@Service
public class UserServiceImpl implements UserService {
    private static final Logger log = Logger.getLogger(UserServiceImpl.class);

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RepairRecordRepository repairRecordRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;



    @Override
    public List<RepairRecord> getUserRepairRecordList(Long userId) {
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
        Optional<User> optionalUser = userRepository.findById(userId);
        return optionalUser.orElse(null);
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
        userRepository.deleteById(userId);

    }

    @Override
    public Long getSumWorkPriceAndDetailPrice(Long userId) {
        Optional<User> userOptional = userRepository.findById(userId);
        User user = userOptional.orElse(null);
        if (user == null) {
            log.error(String.format("user with id :{%s} not found ", userId));
            throw new RuntimeException("user not found");
        } else {
            String username = user.getUsername();
            return repairRecordRepository.findAll().stream()
                    .filter(e -> e.getRepairRequest().getUser().getUsername().equals(username))
                    .map(record -> record.getDetailPrice() + record.getWorkPrice())
                    .reduce(0L, (sumPrice, repRecPrice) -> sumPrice + repRecPrice);
        }

    }

    @Override
    public User registerUser(UserRegistrationDto userRegistrationDto) {
        log.info(String.format("user with info:{%s} was created ", userRegistrationDto.toString()));
        log.debug(String.format("user with info:{%s} was created ", userRegistrationDto.toString()));
        User existUser = userRepository.findByUsername(userRegistrationDto.getUsername());

        if (existUser != null) {
            throw new IllegalArgumentException("User " + userRegistrationDto.getUsername()+" already exist");
        }
        UserConverter userConverter = new UserConverter();
        userRegistrationDto.setPassword(encodePassword(userRegistrationDto.getPassword()));
        User user = userConverter.convertToEntity(userRegistrationDto);
        return userRepository.save(user);

    }

    @Override
    public User updateUser(UserRegistrationDto userRegistrationDto, User userToUpdate) {

        log.info(String.format("user with info:{%s} was updated ", userRegistrationDto.toString()));
        log.debug(String.format("user with info:{%s} was updated ", userRegistrationDto.toString()));
        UserConverter userConverter = new UserConverter();
        userRegistrationDto.setPassword(encodePassword(userRegistrationDto.getPassword()));
        User updatedUser = userConverter.convertToExistingEntity(userRegistrationDto, userToUpdate);
        if (updatedUser.getUsername().equals(userRegistrationDto.getUsername())) {
            throw new IllegalArgumentException("User " + userRegistrationDto.getUsername()+" already exist");
        }
        return userRepository.saveAndFlush(updatedUser);
    }

     private String encodePassword (String password) {
         return passwordEncoder.encode(password);
     }



}