package service.impl;

import common.UserTestData;
import entity.User;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import repository.UserRepository;
import service.converters.impl.UserConverter;
import service.dto.UserRegistrationDto;

import java.util.List;
import java.util.Optional;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.Silent.class)
public class UserServiceImplTest {
    @Spy
    private BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();

    @Mock
    private UserRepository userRepository;

    @Spy
    private UserConverter userConverter = new UserConverter();
    @InjectMocks
    private UserServiceImpl userService;

    private UserTestData userTestData = UserTestData.getInstance();

    @Before
    public void setUp() throws Exception {
        when(userRepository.findByUsername("user")).thenReturn(userTestData.getTestUserByUsername("user"));
        when(userRepository.save(any((User.class)))).thenAnswer(i -> userTestData.saveTestUser((User) i.getArguments()[0]));
        when(userRepository.saveAndFlush(any((User.class)))).thenAnswer(i -> userTestData.updateTestUser((User) i.getArguments()[0]));
        doAnswer(i -> userTestData.deleteTestUserById((Long) i.getArguments()[0])).when(userRepository).deleteById(any(Long.class));
    }

    @Test
    public void findAllUsers() throws Exception {
        when(userRepository.findAll()).thenReturn(userTestData.getAllTestUsers());
        List<User> actualAllUsers = userService.findAllUsers();
        Assert.assertEquals(userTestData.getAllTestUsers().size(), actualAllUsers.size());
    }

    @Test
    public void findUserByUsername() throws Exception {
        String expectedUserName = "user";
        User user = userService.findUserByUsername(expectedUserName);
        Assert.assertEquals("Username not equals", expectedUserName, user.getUsername());
    }

    @Test
    public void registerUser() throws Exception {
        when(userRepository.findById(any(Long.class))).thenAnswer(i -> Optional.of(userTestData.getTestUserById((Long) i.getArguments()[0])));

        String registerUsername = "registerUser";
        String registerPassword = "3";
        String registerPhone = "+375xxxxxxxx";

        UserRegistrationDto userRegistrationDto = new UserRegistrationDto.Builder()
                .setUsername(registerUsername)
                .setPassword(bCryptPasswordEncoder.encode(registerPassword))
                .setPhoneNumber(registerPhone)
                .build();
        userService.registerUser(userRegistrationDto);
        User registeredUser = userTestData.getTestUserByUsername(registerUsername);
        Assert.assertNotNull(registeredUser);
        Assert.assertEquals(registeredUser.getUsername(), registerUsername);
    }

    @Test
    public void updateNotExistingUser() {
        when(userRepository.findByUsername("notExistingUser")).thenReturn(null);
        try {
            User notExistingUser = new User();
            notExistingUser.setUsername("notExistingUser");
            userService.updateUser(new UserRegistrationDto.Builder().build(), notExistingUser.getId());
        } catch (Exception e) {
            Assert.assertEquals("Username is mandatory", e.getMessage());
        }
    }

    @Test
    public void updateUser() {
        when(userRepository.findById(any(Long.class))).thenAnswer(i -> Optional.of(userTestData.getTestUserById((Long) i.getArguments()[0])));
        String usernameToUpdate = "userToUpdate";
        when(userRepository.findByUsername(usernameToUpdate)).thenReturn(userTestData.getTestUserByUsername(usernameToUpdate));
        User userToUpdate = userTestData.getTestUserByUsername(usernameToUpdate);
        String email = userToUpdate.getEmail();
        String newEmail = "1" + email;
        String phoneNumber = userToUpdate.getPhoneNumber();
        String newPhoneNumber = "37522222222";
        userService.updateUser(new UserRegistrationDto.Builder()
                .setEmail(newEmail)
                .setPassword(bCryptPasswordEncoder.encode(userToUpdate.getPassword()))
                .setUsername(userToUpdate.getUsername())
                .setPhoneNumber(newPhoneNumber)
                .setRole(userToUpdate.getRole())
                .build(), userToUpdate.getId());
        User updatedUser = userTestData.getTestUserByUsername(usernameToUpdate);
        Assert.assertNotEquals("email wasn't update", email, updatedUser.getEmail());
        Assert.assertNotEquals("phoneNumber wasn't update", phoneNumber, updatedUser.getPhoneNumber());
    }

    @Test
    public void deleteUser() {
        when(userRepository.findById(any(Long.class))).thenAnswer(i -> Optional.of(userTestData.getTestUserById((Long) i.getArguments()[0])));

        User userToDelete = userTestData.getTestUserByUsername("userToDelete");
        Long userId = userToDelete.getId();
        userService.deleteUserById(userId);
        User userToDeleteAfterDelete = userTestData.getTestUserByUsername("userToDelete");
        Assert.assertNull("user was not delete", userToDeleteAfterDelete);
    }
}