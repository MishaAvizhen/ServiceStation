package dao.impl.db;

import dao.RepairRecordDao;
import dao.RepairRequestDao;
import db.JdbcTemplate;
import entity.RepairRecord;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DbRepairRecordDao implements RepairRecordDao {
    JdbcTemplate jdbcTemplate = new JdbcTemplate();
    private RepairRequestDao dbRepairRequestDao = DbRepairRequestDao.getInstance();
    private static DbRepairRecordDao repairRecordDao;

    private DbRepairRecordDao() {
    }

    public static DbRepairRecordDao getInstance() {
        if (repairRecordDao == null) {
            repairRecordDao = new DbRepairRecordDao();
        }
        return repairRecordDao;
    }

    @Override
    public List<RepairRecord> findAll() {
        String sqlQuery = "SELECT repair_record.*, repair_request.id " +
                "  FROM repair_record, repair_request " +
                "WHERE repair_record.repair_request_id = repair_request.id";
        return jdbcTemplate.executeSelect(sqlQuery, resultSet -> {
            List<RepairRecord> repairRecordList = new ArrayList<>();
            while (resultSet.next()) {
                RepairRecord repairRecord = buildRepairRecordByResultSet(resultSet);
                repairRecordList.add(repairRecord);
            }
            return repairRecordList;
        });
    }

    private RepairRecord buildRepairRecordByResultSet(ResultSet resultSet) throws SQLException {
        RepairRecord repairRecord = new RepairRecord();
        repairRecord.setId(resultSet.getLong("id"));
        repairRecord.setRepairRecordDescription(resultSet.getString("repair_record_description"));
        repairRecord.setDetailPrice(resultSet.getLong("detail_price"));
        repairRecord.setWorkPrice(resultSet.getLong("work_price"));
        repairRecord.setOtherNotes(resultSet.getString("other_notes"));
        repairRecord.setRepairRequest(dbRepairRequestDao.findById(resultSet.getLong("repair_request_id")));
        return repairRecord;
    }

    @Override
    public RepairRecord findById(Long id) {
        String sqlQuery = "SELECT repair_record.*, repair_request.id " +
                "  FROM repair_record, repair_request " +
                "WHERE repair_record.id= " + id;
        return jdbcTemplate.executeSelect(sqlQuery, resultSet -> {
            if (resultSet.next()) {
                return buildRepairRecordByResultSet(resultSet);
            }
            return null;
        });
    }

    @Override
    public void deleteById(Long id) {
        String sqlQuery = "DELETE FROM repair_record WHERE id = " + id;
        jdbcTemplate.executeUpdate(sqlQuery);


    }

    @Override
    public RepairRecord update(RepairRecord entity) {
        String sqlQuery = "UPDATE repair_record " +
                "SET repair_record_description = '" + entity.getRepairRecordDescription() +
                "', detail_price = '" + entity.getDetailPrice() +
                "', work_price = '" + entity.getWorkPrice() +
                "', other_notes = '" + entity.getOtherNotes() +
                "', repair_request_id = '" + entity.getRepairRequest().getId() +
                "' WHERE id = " + entity.getId();

        jdbcTemplate.executeUpdate(sqlQuery);
        return entity;
    }

    @Override
    public RepairRecord save(RepairRecord entity) {
        String insertRepairRecordSqlQuery = "INSERT INTO `repair_record` " +
                "VALUES (null, '" + entity.getRepairRecordDescription() + "', '"
                + entity.getDetailPrice() + "', '"
                + entity.getWorkPrice() + "', '"
                + entity.getOtherNotes() + "', '"
                + entity.getRepairRequest().getId() + "')";
        Long id = jdbcTemplate.executeInsertAndReturnGeneratedId(insertRepairRecordSqlQuery);
        entity.setId(id);
        return entity;
    }
}
