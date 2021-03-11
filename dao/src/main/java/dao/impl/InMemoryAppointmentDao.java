package dao.impl;

import dao.AppointmentDao;
import dao.AbstractCRUDDao;
import entity.Appointment;

public class InMemoryAppointmentDao extends AbstractCRUDDao<Appointment> implements AppointmentDao {

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
