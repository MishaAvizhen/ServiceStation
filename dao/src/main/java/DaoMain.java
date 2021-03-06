import dao.UserDao;
import dao.impl.db.DbUserDao;
import db.JdbcInit;
import db.JdbcTemplate;
import entity.User;

import java.util.List;

public class DaoMain {

    public static void main(String[] args) {
        JdbcInit jdbcInit = new JdbcInit();
        jdbcInit.initDataBase();


    }
}
