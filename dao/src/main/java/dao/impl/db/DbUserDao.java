package dao.impl.db;

import dao.UserDao;
import db.JdbcTemplate;
import entity.User;
import entity.consts.Role;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DbUserDao implements UserDao {
    private JdbcTemplate jdbcTemplate = new JdbcTemplate();
    private static DbUserDao dbUserDao;

    private DbUserDao() {
    }

    public static DbUserDao getInstance() {
        if (dbUserDao == null) {
            dbUserDao = new DbUserDao();
        }
        return dbUserDao;
    }

    @Override
    public List<User> findAll() {
        String sqlQuery = "SELECT users.*" +
                "  FROM users";

        return jdbcTemplate.executeSelect(sqlQuery, resultSet -> {
            List<User> userList = new ArrayList<>();
            while (resultSet.next()) {
                User user = buildUserByResultSet(resultSet);
                userList.add(user);
            }
            return userList;
        });
    }

    @Override
    public User findById(Long id) {
        String sqlQuery = "SELECT users.* " +
                "FROM users " +
                "WHERE users.id =" + id;

        return jdbcTemplate.executeSelect(sqlQuery, resultSet -> {
            if (resultSet.next()) {
                return buildUserByResultSet(resultSet);
            }
            return null;
        });
    }

    private User buildUserByResultSet(ResultSet resultSet) throws SQLException {
        User user = new User();
        user.setId(resultSet.getLong("id"));
        user.setUsername(resultSet.getString("username"));
        user.setPhoneNumber(resultSet.getString("phone_number"));
        user.setEmail(resultSet.getString("email"));
        user.setPassword(resultSet.getString("password"));
        user.setRole(Role.defineRoleByRoleName(resultSet.getString("user_roles")));
        return user;
    }


    @Override
    public void deleteById(Long id) {
        String sqlQuery = "DELETE FROM users WHERE id = " + id;
        jdbcTemplate.executeUpdate(sqlQuery);
    }

    @Override
    public User update(User entity) {
        String sqlQuery = "UPDATE users " +
                "SET username = '" + entity.getUsername() +
                "', phone_number = '" + entity.getPhoneNumber() +
                "', email = '" + entity.getEmail() +
                "', password = '" + entity.getPassword() +
                "' WHERE id = " + entity.getId();
        jdbcTemplate.executeUpdate(sqlQuery);

        return entity;
    }

    @Override
    public User save(User entity) {
        String insertUserSqlQuery = "INSERT INTO `users` " +
                "VALUES (null,'" + entity.getUsername() + "', '"
                + entity.getPhoneNumber() + "', '" + entity.getEmail() + "', '"
                + entity.getPassword() + entity.getRole()+ "')";
        Long id = jdbcTemplate.executeInsertAndReturnGeneratedId(insertUserSqlQuery);
        entity.setId(id);
        return entity;
    }

}
