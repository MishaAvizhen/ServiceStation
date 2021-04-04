package service.impl;

import common.UserTestData;
import entity.User;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import repository.RepairRecordRepository;
import repository.UserRepository;
import service.dto.UserRegistrationDto;

import java.util.List;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class UserServiceImplTest {

    @Mock
    private UserRepository userRepository;
    @Mock
    private RepairRecordRepository repairRecordRepository;

    @InjectMocks
    private UserServiceImpl userService;

    private UserTestData userTestData = UserTestData.getInstance();

    @Before
    public void setUp() throws Exception {
        when(userRepository.findByUsername("user")).thenReturn(userTestData.getTestUserByUsername("user"));
        when(userRepository.findByUsername("notExistingUser")).thenReturn(null);
        when(userRepository.findAll()).thenReturn(userTestData.getAllTestUsers());
        when(userRepository.save(any((User.class)))).thenAnswer(i -> userTestData.saveTestUser((User) i.getArguments()[0]));
        when(userRepository.saveAndFlush(any((User.class)))).thenAnswer(i -> userTestData.updateTestUser((User) i.getArguments()[0]));
        doAnswer(i -> userTestData.deleteTestUserById((Long) i.getArguments()[0])).when(userRepository).delete(any(Long.class));
    }

    @Test
    public void findAllUsers() throws Exception {
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
        String registerUsername = "registerUser";
        UserRegistrationDto userRegistrationDto = new UserRegistrationDto.Builder()
                .setUsername(registerUsername).build();
        userService.registerUser(userRegistrationDto);
        User registeredUser = userTestData.getTestUserByUsername(registerUsername);
        Assert.assertNotNull(registeredUser);
        Assert.assertEquals(registeredUser.getUsername(), registerUsername);
    }

    @Test
    public void updateNotExistingUser() {
        try {
            User notExistingUser = new User();
            notExistingUser.setUsername("notExistingUser");
            userService.updateUser(new UserRegistrationDto.Builder().build(), notExistingUser);
        } catch (Exception e) {
            Assert.assertEquals("user not found", e.getMessage());
        }
    }

    @Test
    public void updateUser() {
        String usernameToUpdate = "userToDelete";
        when(userRepository.findByUsername(usernameToUpdate)).thenReturn(userTestData.getTestUserByUsername(usernameToUpdate));
        User userToUpdate = userTestData.getTestUserByUsername(usernameToUpdate);
        String email = userToUpdate.getEmail();
        String newEmail = "1" + email;
        String phoneNumber = userToUpdate.getPhoneNumber();
        String newPhoneNumber = "37522222222";
        userService.updateUser(new UserRegistrationDto.Builder()
                .setEmail(newEmail)
                .setPassword(userToUpdate.getPassword())
                .setUsername(userToUpdate.getUsername())
                .setPhoneNumber(newPhoneNumber)
                .setRole(userToUpdate.getRole())
                .build(), userToUpdate);
        User updatedUser = userTestData.getTestUserByUsername(usernameToUpdate);
        Assert.assertNotEquals("email wasn't update", email, updatedUser.getEmail());
        Assert.assertNotEquals("phoneNumber wasn't update", phoneNumber, updatedUser.getPhoneNumber());

    }

    @Test
    public void deleteUser() {
        User userToDelete = userTestData.getTestUserByUsername("userToDelete");
        Long userId = userToDelete.getId();
        userService.deleteUserById(userId);
        User userToDeleteAfterDelete = userTestData.getTestUserByUsername("userToDelete");
        Assert.assertNull("user was not delete", userToDeleteAfterDelete);
    }

}