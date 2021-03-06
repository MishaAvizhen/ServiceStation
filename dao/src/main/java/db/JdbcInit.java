package db;

import com.mysql.cj.jdbc.*;
import org.apache.ibatis.jdbc.ScriptRunner;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.Reader;
import java.sql.*;

public class JdbcInit {
    public void initSql() {
        String url = "jdbc:mysql://localhost:3306/avizhen_nc_sto?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC";
        String username = "root";
        String password = "root";
        String sql = "SELECT * FROM repair_request";
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        try (Connection connection = DriverManager.getConnection(url, username, password);
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(sql)) {
            ScriptRunner sr = new ScriptRunner(connection);
            Reader reader = new BufferedReader(new FileReader("C:\\Users\\Александр\\Desktop\\AvizhenSto\\dao\\src\\main\\resources\\sql\\avizhenSto.sql"));
            sr.runScript(reader);

//
//            while (resultSet.next()) {
//                System.out.println("\t id:" + resultSet.getInt("id") +
//                        "\t car:" + resultSet.getString("car_remark"));
//            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
