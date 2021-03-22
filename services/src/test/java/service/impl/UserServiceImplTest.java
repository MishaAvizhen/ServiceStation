package service.impl;

import entity.User;
import entity.consts.Role;
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

import java.util.ArrayList;
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

    private List<User> usersForTest = new ArrayList<>();

    @Before
    public void setUp() throws Exception {
        initTestData();
        when(userRepository.findByUsername("user")).thenReturn(getTestUserByUsername("user"));
        when(userRepository.findByUsername("notExistingUser")).thenReturn(null);
        when(userRepository.findAll()).thenReturn(usersForTest);
        when(userRepository.save(any((User.class)))).thenAnswer(i -> saveTestUser((User) i.getArguments()[0]));
        when(userRepository.saveAndFlush(any((User.class)))).thenAnswer(i -> updateTestUser((User) i.getArguments()[0]));
        doAnswer(i -> deleteTestUserById((Long) i.getArguments()[0])).when(userRepository).delete(any(Long.class));
    }

    private void initTestData() {
        usersForTest.add(buildUserWithUserName("user", 1L));
        usersForTest.add(buildUserWithUserName("userToUpdate", 2L));
        usersForTest.add(buildUserWithUserName("userToDelete", 3L));
    }

    private User buildUserWithUserName(String username, Long id) {
        User user = new User();
        user.setId(id);
        user.setUsername(username);
        user.setPhoneNumber("+3752994325029");
        user.setRole(Role.USER);
        user.setPassword("1");
        return user;
    }

    private User deleteTestUserById(Long userId) {
        User userToDelete = getTestUserById(userId);
        if (userToDelete != null) {
            usersForTest.remove(userToDelete);
        }
        return userToDelete;

    }

    private User getTestUserById(Long userId) {
        for (User testUser : usersForTest) {
            if (testUser.getId().equals(userId)) {
                return testUser;
            }
        }
        return null;
    }


    private User updateTestUser(User testUser) {
        User testUserToUpdate = getTestUserByUsername(testUser.getUsername());
        if (testUserToUpdate == null) {
            usersForTest.add(testUser);
        } else {
            usersForTest.remove(testUserToUpdate);
            usersForTest.add(testUser);
        }
        return testUser;
    }

    private User getTestUserByUsername(String username) {
        for (User testUser : usersForTest) {
            if (username.equals(testUser.getUsername())) {
                return testUser;
            }
        }
        return null;
    }

    private User saveTestUser(User testUser) {
        usersForTest.add(testUser);
        return testUser;
    }


    @Test
    public void findAllUsers() throws Exception {
        List<User> actualAllUsers = userService.findAllUsers();
        Assert.assertEquals(usersForTest.size(), actualAllUsers.size());

    }

    @Test
    public void findUserByUsername() throws Exception {
        String expectedUserName = "user";
        User user = userService.findUserByUsername(expectedUserName);
        Assert.assertEquals("Username not equals", expectedUserName, user.getUsername());
    }

    @Test
    public void registerUser() throws Exception {
        String registerUser = "registerUser";
        UserRegistrationDto userRegistrationDto = new UserRegistrationDto.Builder()
                .setUsername(registerUser).build();
        userService.registerUser(userRegistrationDto);
        User registeredUser = getTestUserByUsername(registerUser);
        Assert.assertNotNull(registeredUser);
        Assert.assertEquals(registeredUser.getUsername(), registerUser);
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
        String usernameToUpdate = "userToUpdate";
        User userToUpdate = getTestUserByUsername(usernameToUpdate);
        String email = userToUpdate.getEmail();
        String newEmail = "1" + email;
        String phoneNumber = userToUpdate.getPhoneNumber();
        userService.updateUser(new UserRegistrationDto.Builder()
                .setEmail(newEmail)
                .setPassword(userToUpdate.getPassword())
                .setUsername(userToUpdate.getUsername())
                .setPhoneNumber(phoneNumber)
                .setRole(userToUpdate.getRole())
                .build(), userToUpdate);
        User updatedUser = getTestUserByUsername(usernameToUpdate);
        Assert.assertEquals("email wasn't update", newEmail, updatedUser.getEmail());
        Assert.assertNotEquals("phoneNumber was update", phoneNumber, updatedUser.getPhoneNumber());

    }

    @Test
    public void deleteUser() {
        User userToDelete = getTestUserByUsername("userToDelete");
        Long userId = userToDelete.getId();
        userService.deleteUserById(userId);
        User userToDeleteAfterDelete = getTestUserByUsername("userToDelete");
        Assert.assertNull("user was not delete", userToDeleteAfterDelete);


    }


}