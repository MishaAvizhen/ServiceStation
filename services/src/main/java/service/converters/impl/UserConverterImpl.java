package service.converters.impl;

import dao.UserDao;
import dao.impl.InMemoryUserDao;
import entity.User;
import entity.constants.Role;
import service.converters.UserConverterService;
import service.dto.UserRegistrationDto;

public class UserConverterImpl implements UserConverterService {
    private UserDao userDao = InMemoryUserDao.getInstance();

    private static UserConverterImpl userConverter;

    private UserConverterImpl() {
    }

    public static UserConverterImpl getInstance() {
        if (userConverter == null) {
            userConverter = new UserConverterImpl();
        }
        return userConverter;
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
