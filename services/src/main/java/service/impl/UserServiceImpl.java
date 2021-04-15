package service.impl;


import entity.User;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import repository.RepairRecordRepository;
import repository.UserRepository;
import service.UserService;
import service.converters.impl.UserConverter;
import service.dto.UserRegistrationDto;

import java.util.List;
import java.util.Optional;

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
    public User findUserByUsername(String username) {
        log.info(String.format("Find user with name: {%s}", username));
        return userRepository.findByUsername(username);
    }

    @Override
    public User findUserById(Long userId) {
        log.info(String.format("Find user with id= {%s}", userId));
        Optional<User> optionalUser = userRepository.findById(userId);
        return optionalUser.orElse(null);
    }

    @Override
    public List<User> findAllUsers() {
        log.info("Find all users");
        return userRepository.findAll();
    }

    @Override
    public void deleteUserById(Long userId) {
        log.info(String.format("Delete user with id=  {%s}", userId));
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

        User existUser = userRepository.findByUsername(userRegistrationDto.getUsername());

        if (existUser != null) {
            log.info(String.format("user with info:{%s} already exist ", userRegistrationDto.toString()));
            throw new IllegalArgumentException("User " + userRegistrationDto.getUsername() + " already exist");
        }
        // TODO создавать классы через спринг
        UserConverter userConverter = new UserConverter();
        userRegistrationDto.setPassword(encodePassword(userRegistrationDto.getPassword()));
        User user = userConverter.convertToEntity(userRegistrationDto);
        log.info(String.format("user with info:{%s} was created ", userRegistrationDto.toString()));
        return userRepository.save(user);
    }

    @Override
    public User updateUser(UserRegistrationDto userRegistrationDto, User userToUpdate) {
        UserConverter userConverter = new UserConverter();
        userRegistrationDto.setPassword(encodePassword(userRegistrationDto.getPassword()));
        User updatedUser = userConverter.convertToExistingEntity(userRegistrationDto, userToUpdate);
        User userInDb = userRepository.findByUsername(updatedUser.getUsername());
        if (userInDb != null) {
            log.info(String.format("user with info:{%s} already exist ", userRegistrationDto.toString()));
            throw new IllegalArgumentException("User " + userRegistrationDto.getUsername() + " already exist");
        }
        log.info(String.format("user with info:{%s} was updated ", userRegistrationDto.toString()));
        return userRepository.saveAndFlush(updatedUser);
    }

    private String encodePassword(String password) {
        return passwordEncoder.encode(password);
    }
}
