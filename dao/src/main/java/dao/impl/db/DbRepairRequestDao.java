package dao.impl.db;

import dao.RepairRequestDao;
import dao.UserDao;
import db.JdbcTemplate;
import entity.RepairRequest;
import entity.consts.RepairRequestStatus;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DbRepairRequestDao implements RepairRequestDao {
    private UserDao dbUserDao = DbUserDao.getInstance();
    JdbcTemplate jdbcTemplate = new JdbcTemplate();
    private static DbRepairRequestDao dbRepairRequestDao;

    private DbRepairRequestDao() {
    }

    public static DbRepairRequestDao getInstance() {
        if (dbRepairRequestDao == null) {
            dbRepairRequestDao = new DbRepairRequestDao();
        }
        return dbRepairRequestDao;
    }

    @Override
    public List<RepairRequest> findAll() {
        String sqlQuery = "SELECT repair_request.*, users.id " +
                "  FROM repair_request, users " +
                "WHERE repair_request.user_id = users.id";
        return jdbcTemplate.executeSelect(sqlQuery, resultSet -> {
            List<RepairRequest> repairRequestList = new ArrayList<>();
            while (resultSet.next()) {
                RepairRequest repairRequest = buildRepairRequestByResultSet(resultSet);
                repairRequestList.add(repairRequest);
            }
            return repairRequestList;
        });

    }

    private RepairRequest buildRepairRequestByResultSet(ResultSet resultSet) throws SQLException {
        RepairRequest request = new RepairRequest();

        request.setId(resultSet.getLong("id"));
        request.setCarRemark(resultSet.getString("car_remark"));
        request.setDateOfRequest(resultSet.getDate("date_of_repair"));
        request.setRepairRequestStatus(RepairRequestStatus.defineRepairRequestStatusByName(resultSet.getString("status")));
        request.setRepairRequestDescription(resultSet.getString("repair_request_description"));
        request.setUser(dbUserDao.findById(resultSet.getLong("user_id")));
        return request;
    }

    @Override
    public RepairRequest findById(Long id) {
        String sqlQuery = "SELECT repair_request.*, users.id " +
                "  FROM repair_request, users " +
                "WHERE repair_request.id = " + id;

        return jdbcTemplate.executeSelect(sqlQuery, resultSet -> {
            if (resultSet.next()) {
                return buildRepairRequestByResultSet(resultSet);
            }
            return null;
        });

    }

    @Override
    public void deleteById(Long id) {
        String sqlQuery = "DELETE FROM repair_request WHERE id = " + id;
        jdbcTemplate.executeUpdate(sqlQuery);

    }

    @Override
    public RepairRequest update(RepairRequest entity) {
        String sqlQuery = "UPDATE repair_request " +
                "SET date_of_repair = '" + entity.getDateOfRequest() +
                "', status = '" + entity.getRepairRequestStatus() +
                "', car_remark = '" + entity.getCarRemark() +
                "', repair_request_description = '" + entity.getRepairRequestDescription() +
                "', user_id = '" + entity.getUser().getId() +
                "' WHERE id = " + entity.getId();
        jdbcTemplate.executeUpdate(sqlQuery);
        return entity;
    }

    @Override
    public RepairRequest save(RepairRequest entity) {
        String insertRepairRequestSqlQuery = "INSERT INTO `repair_request` " +
                "VALUES (null, '" + entity.getDateOfRequest() + "', '"
                + entity.getRepairRequestStatus() + "', '"
                + entity.getCarRemark() + "', '"
                + entity.getRepairRequestDescription() + "', '"
                + entity.getUser().getId() + "')";
        Long id = jdbcTemplate.executeInsertAndReturnGeneratedId(insertRepairRequestSqlQuery);
        entity.setId(id);
        return entity;
    }
}
