package db;

import dao.common.DaoPropertyReader;
import entity.User;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class JdbcInit {
    private JdbcTemplate jdbcTemplate = new JdbcTemplate();
    private DaoPropertyReader daoPropertyReader = new DaoPropertyReader();
    private static final String pathToInitSql = "C:\\Users\\Александр\\Desktop\\AvizhenSto\\dao\\src\\main\\resources\\sql\\initAvizhenSto.sql";
    private static final String pathToGenerateTestDataSql = "C:\\Users\\Александр\\Desktop\\AvizhenSto\\dao\\src\\main\\resources\\sql\\generateTestDataAvizhenSto.sql";


    public void initDataBase() {
        if (daoPropertyReader.shouldRunInitSql()) {
            jdbcTemplate.executeScript(pathToInitSql);
        }
        if (daoPropertyReader.shouldRunGenerateTestDataSql()) {
            jdbcTemplate.executeScript(pathToGenerateTestDataSql);
        }
    }
}
