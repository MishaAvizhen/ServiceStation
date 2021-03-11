package dao.impl;

import dao.AbstractCRUDDao;
import dao.UserDao;
import entity.User;

public class InMemoryUserDao extends AbstractCRUDDao<User> implements UserDao {

    private static InMemoryUserDao inMemoryUserDao;

    private InMemoryUserDao() {
    }

    public static InMemoryUserDao getInstance() {
        if (inMemoryUserDao == null) {
            inMemoryUserDao = new InMemoryUserDao();
        }
        return inMemoryUserDao;
    }
}