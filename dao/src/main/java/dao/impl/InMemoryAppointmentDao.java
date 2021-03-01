package dao.impl;

import dao.AppointmentDao;
import dao.InMemoryCommonDao;
import entity.Appointment;

public class InMemoryAppointmentDao extends InMemoryCommonDao<Appointment> implements AppointmentDao {

    private static InMemoryAppointmentDao inMemoryAppointmentDao;

    private InMemoryAppointmentDao() {
    }

    public static InMemoryAppointmentDao getInstance() {
        if (inMemoryAppointmentDao == null) {
            inMemoryAppointmentDao = new InMemoryAppointmentDao();

        }
        return inMemoryAppointmentDao;
    }

}
