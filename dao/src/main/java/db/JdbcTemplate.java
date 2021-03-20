package db;

import dao.common.DaoPropertyReader;
import org.apache.ibatis.jdbc.ScriptRunner;
import org.apache.log4j.Logger;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.Reader;
import java.sql.*;

public class JdbcTemplate {
    private static final Logger log = Logger.getLogger(JdbcTemplate.class);

    private DaoPropertyReader daoPropertyReader = new DaoPropertyReader();

    public JdbcTemplate() {
    }

    public <T> T executeSelect(String sqlQuery, ResultSetExtractor<T> resultSetExtractor) {
        try {
            Class.forName(daoPropertyReader.getDriver());
        } catch (ClassNotFoundException e) {
            log.error(String.format("Exception: {%s} ", e));
        }
        try (Connection connection = DriverManager.getConnection(daoPropertyReader.getUrl(), daoPropertyReader.getUsername(),
                daoPropertyReader.getPassword());
             Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery(sqlQuery);
            log.info(String.format("Sql query: {%s} ", sqlQuery));
            log.debug(String.format("Sql query: {%s} ", sqlQuery));
            return resultSetExtractor.extractFromResultSet(resultSet);
        } catch (Exception e) {
            log.error(String.format("Exception: {%s} ", e));
        }
        return null;
    }

    public void executeUpdate(String updateQuery) {
        try {
            Class.forName(daoPropertyReader.getDriver());
        } catch (ClassNotFoundException e) {
            log.error(String.format("Exception: {%s} ", e));
        }
        try (Connection connection = DriverManager.getConnection(daoPropertyReader.getUrl(), daoPropertyReader.getUsername(),
                daoPropertyReader.getPassword());
             Statement statement = connection.createStatement()) {
            statement.executeUpdate(updateQuery);
            log.info(String.format("Sql query: {%s} ", updateQuery));
            log.debug(String.format("Sql query: {%s} ", updateQuery));

        } catch (Exception e) {
            log.error(String.format("Exception: {%s} ", e));

        }
    }

    public void executeScript(String pathToScript) {
        try {

            Class.forName(daoPropertyReader.getDriver());
        } catch (ClassNotFoundException e) {
            log.error(String.format("Exception: {%s} ", e));
        }
        try (Connection connection = DriverManager.getConnection(daoPropertyReader.getUrl(), daoPropertyReader.getUsername(),
                daoPropertyReader.getPassword());
             Statement statement = connection.createStatement()) {
            ScriptRunner sr = new ScriptRunner(connection);
            //Creating a reader object
            Reader reader = new BufferedReader(new FileReader(pathToScript));
            //Running the script
            sr.runScript(reader);
        } catch (Exception e) {
            log.debug(String.format("path to script: {%s} ", pathToScript));
            log.error(String.format("Exception: {%s} ", e));
        }
    }

    public Long executeInsertAndReturnGeneratedId(String sqlQuery) {
        try {
            Class.forName(daoPropertyReader.getDriver());
        } catch (ClassNotFoundException e) {
            log.error(String.format("Exception: {%s} ", e));
        }
        try (Connection connection = DriverManager.getConnection(daoPropertyReader.getUrl(), daoPropertyReader.getUsername(),
                daoPropertyReader.getPassword());
             PreparedStatement preparedStatement = connection.prepareStatement(sqlQuery, Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.executeUpdate();
            ResultSet rs = preparedStatement.getGeneratedKeys();
            rs.next();
            return rs.getLong(1);

        } catch (Exception e) {
            log.info(String.format("Sql query: {%s} ", sqlQuery));
            log.debug(String.format("Sql query: {%s} ", sqlQuery));
            log.error(String.format("Exception: {%s} ", e));
        }
        return null;
    }

}
