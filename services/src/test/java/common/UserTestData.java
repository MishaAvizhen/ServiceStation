package common;

import entity.User;
import entity.consts.Role;

import java.util.*;

public class UserTestData {
    private Map<String, User> usernameToTestUserMap = new HashMap<>();
    private static UserTestData INSTANCE = null;

    private UserTestData() {
        initUserTestData();
    }

    public static UserTestData getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new UserTestData();
        }
        return INSTANCE;
    }

    public List<User> getAllTestUsers() {
        return new ArrayList<>(usernameToTestUserMap.values());
    }

    public User deleteTestUserById(Long userId) {
        User userToDelete = getTestUserById(userId);
        if (userToDelete != null) {
            usernameToTestUserMap.remove(userToDelete.getUsername());
        }
        return userToDelete;

    }

    public User getTestUserById(Long userId) {
        for (User testUser : usernameToTestUserMap.values()) {
            if (testUser.getId().equals(userId)) {
                return testUser;
            }
        }
        return null;
    }

    private void initUserTestData() {
        saveTestUser(buildUserWithUserName("user", 1L));
        saveTestUser(buildUserWithUserName("userToUpdate", 2L));
        saveTestUser(buildUserWithUserName("userToDelete", 3L));
    }

    public User getTestUserByUsername(String username) {
        return usernameToTestUserMap.get(username);
    }

    public User updateTestUser(User testUser) {
        saveTestUser(testUser);
        return testUser;
    }

    public User saveTestUser(User user) {
        usernameToTestUserMap.put(user.getUsername(), user);
        return user;
    }

    private User buildUserWithUserName(String username, Long id) {
        User user = new User();
        user.setId(id);
        user.setUsername(username);
        user.setPhoneNumber("+3752994325029");
        user.setRole(Role.USER);
        user.setEmail(username + "@mail.ru");
        user.setPassword("1");
        return user;
    }
}
