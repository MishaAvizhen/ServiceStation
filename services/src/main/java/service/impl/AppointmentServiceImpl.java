package service.impl;

import dao.AppointmentDao;
import dao.impl.InMemoryAppointmentDao;
import entity.Appointment;
import service.AppointmentService;

import java.util.List;

public class AppointmentServiceImpl implements AppointmentService {
    private AppointmentDao appointmentDao = InMemoryAppointmentDao.getInstance();

    private static AppointmentServiceImpl appointmentService;

    private AppointmentServiceImpl() {
    }

    public static AppointmentServiceImpl getInstance() {
        if (appointmentService == null) {
            appointmentService = new AppointmentServiceImpl();
        }
        return appointmentService;
    }


    @Override
    public List<Appointment> findAllAppointment() {
        return appointmentDao.findAll();
    }

}
