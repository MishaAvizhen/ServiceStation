package dao.common;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class DaoPropertyReader {
    private String pathToProperty = "C:\\ProjectsAvizhen\\service-station\\dao\\src\\main\\resources\\application.properties";
    private Properties prop = new Properties();
    public DaoPropertyReader() {
        try (InputStream input = new FileInputStream(pathToProperty)) {
            prop.load(input);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String getPropertyValue(String key) {
        return prop.getProperty(key);
    }

    public String getDriver() {
        return getPropertyValue("db.driver");
    }

    public String getUrl() {
        return getPropertyValue("db.url");
    }

    public String getUsername() {
        return getPropertyValue("db.username");
    }

    public String getPassword() {
        return getPropertyValue("db.password");
    }

    public Boolean shouldRunInitSql() {
        return Boolean.parseBoolean(getPropertyValue("run.init.sql"));
    }

    public boolean shouldRunGenerateTestDataSql() {
        return Boolean.parseBoolean(getPropertyValue("run.generate.test.data.sql"));
    }
    public boolean useFileMemory() {
        return Boolean.parseBoolean(getPropertyValue("use.memory.file"));
    }

}

