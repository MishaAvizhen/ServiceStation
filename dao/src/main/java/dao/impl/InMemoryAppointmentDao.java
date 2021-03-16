package dao.impl;

import dao.AppointmentDao;
import dao.AbstractCrudDao;
import entity.Appointment;

public class InMemoryAppointmentDao extends AbstractCrudDao<Appointment> implements AppointmentDao {

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
