package dao.impl.db;

import dao.AppointmentDao;
import dao.UserDao;
import db.JdbcTemplate;
import db.ResultSetExtractor;
import entity.Appointment;
import entity.constants.SlotStatus;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DbAppointmentDao implements AppointmentDao {
    private JdbcTemplate jdbcTemplate = new JdbcTemplate();
    private UserDao dbUserDao = DbUserDao.getInstance();
    private static DbAppointmentDao dbAppointmentDao;

    private DbAppointmentDao() {
    }

    public static DbAppointmentDao getInstance() {
        if (dbAppointmentDao == null) {
            dbAppointmentDao = new DbAppointmentDao();
        }
        return dbAppointmentDao;
    }


    @Override
    public List<Appointment> findAll() {
        String sqlQuery = "SELECT appointment.*, users.id " +
                " FROM appointment, users " +
                " WHERE appointment.user_id = users.id";
        return jdbcTemplate.executeSelect(sqlQuery, resultSet -> {
            List<Appointment> appointmentList = new ArrayList<>();
            while (resultSet.next()) {
                Appointment appointment = buildAppointmentByResultSet(resultSet);
                appointmentList.add(appointment);
            }
            return appointmentList;
        });
    }

    private Appointment buildAppointmentByResultSet(ResultSet resultSet) throws SQLException {
        Appointment appointment = new Appointment();
        appointment.setId(resultSet.getLong("id"));
        appointment.setSlotStatus(SlotStatus.defineSlotStatusByName(resultSet.getString("slot_status")));
        appointment.setStartDate(resultSet.getDate("start_date"));
        appointment.setEndDate(resultSet.getDate("end_date"));
        appointment.setNotes(resultSet.getString("notes"));
        appointment.setClient(dbUserDao.findById(resultSet.getLong("client_id")));
        appointment.setMaster(dbUserDao.findById(resultSet.getLong("master_id")));
        return appointment;
    }

    @Override
    public Appointment findById(Long id) {
        String sqlQuery = "SELECT appointment.*, users.id " +
                " FROM appointment, users " +
                " WHERE appointment.id= " + id;
        return jdbcTemplate.executeSelect(sqlQuery, new ResultSetExtractor<Appointment>() {
            @Override
            public Appointment extractFromResultSet(ResultSet resultSet) throws SQLException {
                if (resultSet.next()) {
                    return buildAppointmentByResultSet(resultSet);
                }
                return null;

            }
        });
    }

        @Override
        public void deleteById (Long id){
            String sqlQuery = "DELETE FROM appointment WHERE id = " + id;
            jdbcTemplate.executeUpdate(sqlQuery);
        }

        @Override
        public Appointment update (Appointment entity){
            String sqlQuery = "UPDATE appointment " +
                    "SET slot_status = '" + entity.getSlotStatus() +
                    "', start_date = '" + entity.getStartDate() +
                    "', end_date = '" + entity.getEndDate() +
                    "', notes = '" + entity.getNotes() +
                    "', client_id = '" + entity.getClient().getId() +
                    "', master_id = '" + entity.getMaster().getId() +
                    "' WHERE id = " + entity.getId();
            jdbcTemplate.executeUpdate(sqlQuery);
            return entity;
        }

        @Override
        public Appointment save (Appointment entity){
            String insertAppointmentSqlQuery = "INSERT INTO `appointment` " +
                    "VALUES (null, '" + entity.getSlotStatus() + "', '"
                    + entity.getStartDate() + "', '"
                    + entity.getEndDate() + "', '"
                    + entity.getNotes() + "', '"
                    + entity.getClient().getId() + "', '"
                    + entity.getMaster().getId() + "')";
            Long id = jdbcTemplate.executeInsertAndReturnGeneratedId(insertAppointmentSqlQuery);
            entity.setId(id);
            return entity;
        }
    }
