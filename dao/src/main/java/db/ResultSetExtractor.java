package db;

import java.sql.ResultSet;
import java.sql.SQLException;

public interface ResultSetExtractor<T> {

    T extractFromResultSet(ResultSet resultSet) throws SQLException;
}
