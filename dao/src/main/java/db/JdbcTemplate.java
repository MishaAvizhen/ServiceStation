package db;

import dao.common.DaoPropertyReader;
import org.apache.ibatis.jdbc.ScriptRunner;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.Reader;
import java.sql.*;

public class JdbcTemplate {

    private DaoPropertyReader daoPropertyReader = new DaoPropertyReader();

    public JdbcTemplate() {
    }

    public <T> T executeSelect(String sqlQuery, ResultSetExtractor<T> resultSetExtractor) {
        try {
            Class.forName(daoPropertyReader.getDriver());
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        try (Connection connection = DriverManager.getConnection(daoPropertyReader.getUrl(), daoPropertyReader.getUsername(),
                daoPropertyReader.getPassword());
             Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery(sqlQuery);
            return resultSetExtractor.extractFromResultSet(resultSet);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public void executeUpdate(String updateQuery) {
        try {
            Class.forName(daoPropertyReader.getDriver());
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        try (Connection connection = DriverManager.getConnection(daoPropertyReader.getUrl(), daoPropertyReader.getUsername(),
                daoPropertyReader.getPassword());
             Statement statement = connection.createStatement()) {
            statement.executeUpdate(updateQuery);
        } catch (Exception e) {
            System.err.println("Sql query: " + updateQuery);
            e.printStackTrace();
        }
    }

    public void executeScript(String pathToScript) {
        try {

            Class.forName(daoPropertyReader.getDriver());
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
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
            System.err.println("Path to script: " + pathToScript);
            e.printStackTrace();
        }
    }

    public Long executeInsertAndReturnGeneratedId(String sqlQuery) {
        try {
            Class.forName(daoPropertyReader.getDriver());
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        try (Connection connection = DriverManager.getConnection(daoPropertyReader.getUrl(), daoPropertyReader.getUsername(),
                daoPropertyReader.getPassword());
             PreparedStatement preparedStatement = connection.prepareStatement(sqlQuery, Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.executeUpdate();
            ResultSet rs = preparedStatement.getGeneratedKeys();
            rs.next();
            return rs.getLong(1);

        } catch (Exception e) {
            System.err.println("Sql query: " + sqlQuery);
            e.printStackTrace();
        }
        return null;
    }

}
