package dao.impl;

import dao.AbstractCrudDao;
import dao.UserDao;
import entity.User;

public class InMemoryUserDao extends AbstractCrudDao<User> implements UserDao {

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