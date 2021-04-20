package service.impl;


import com.google.common.base.Preconditions;
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
import service.exceptions.NotContentException;
import service.exceptions.ResourceNotFoundException;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {
    private static final Logger log = Logger.getLogger(UserServiceImpl.class);

    @Autowired
    private UserConverter userConverter;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RepairRecordRepository repairRecordRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public User findUserByUsername(String username) {
        log.info(String.format("Find user with name: {%s}", username));
        User user = userRepository.findByUsername(username);
        if (user == null) {
            throw new ResourceNotFoundException(username);
        }
        return userRepository.findByUsername(username);
    }

    @Override
    public User findUserById(Long userId) {
        log.info(String.format("Find user with id= {%s}", userId));
        Optional<User> optionalUser = userRepository.findById(userId);
        return optionalUser.orElseThrow(() -> new ResourceNotFoundException(userId.toString()));
    }

    @Override
    public List<User> findAllUsers() {
        log.info("Find all users");
        return userRepository.findAll();
    }

    @Override
    public void deleteUserById(Long userId) {
        User user = findUserById(userId);
        if (user == null) {
            throw new NotContentException(userId.toString());
        }
        log.info(String.format("Delete user with id=  {%s}", userId));
        userRepository.deleteById(userId);
    }

    @Override
    public Long getSumWorkPriceAndDetailPrice(Long userId) {
        Optional<User> userOptional = userRepository.findById(userId);
        User user = userOptional.orElse(null);
        if (user == null) {
            log.info(String.format("user with id :{%s} not found ", userId));
            throw new ResourceNotFoundException(userId.toString());
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
        validateInputParamsForUserRegistration(userRegistrationDto);
        User existUser = userRepository.findByUsername(userRegistrationDto.getUsername());
        if (existUser != null) {
            log.info(String.format("user with info:{%s} already exist ", userRegistrationDto.toString()));
            throw new IllegalArgumentException("User " + userRegistrationDto.getUsername() + " already exist");
        }
        userRegistrationDto.setPassword(encodePassword(userRegistrationDto.getPassword()));
        User user = userConverter.convertToEntity(userRegistrationDto);
        log.info(String.format("user with info:{%s} was created ", userRegistrationDto.toString()));
        return userRepository.save(user);
    }

    @Override
    public User updateUser(UserRegistrationDto userRegistrationDto, Long userId) {
        validateInputParamsForUserRegistration(userRegistrationDto);
        Preconditions.checkNotNull(userRegistrationDto.getUsername(), "User to update with id " + userId + " not found");
        userRegistrationDto.setPassword(encodePassword(userRegistrationDto.getPassword()));
        User userToUpdate = findUserById(userId);
        Preconditions.checkNotNull(userToUpdate, "User to update with id " + userId + " not found");
        User updatedUser = userConverter.convertToExistingEntity(userRegistrationDto, userToUpdate);
        User userInDb = userRepository.findByUsername(updatedUser.getUsername());
        if (userInDb != null && !userInDb.equals(updatedUser)) {
            log.info(String.format("user with info:{%s} already exist ", userRegistrationDto.toString()));
            throw new IllegalArgumentException("User " + userRegistrationDto.getUsername() + " already exist");
        }
        log.info(String.format("user with info:{%s} was updated ", userRegistrationDto.toString()));
        return userRepository.saveAndFlush(updatedUser);
    }

    private String encodePassword(String password) {
        return passwordEncoder.encode(password);
    }

    private void validateInputParamsForUserRegistration(UserRegistrationDto userRegistrationDto) {
        Preconditions.checkNotNull(userRegistrationDto.getUsername(), "Username is mandatory");
        Preconditions.checkNotNull(userRegistrationDto.getPassword(), "Password is mandatory");
        Preconditions.checkNotNull(userRegistrationDto.getPhoneNumber(), "PhoneNumber is mandatory");
    }
}
