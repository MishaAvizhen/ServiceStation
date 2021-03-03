package service.impl;

import dao.AppointmentDao;
import dao.UserDao;
import dao.impl.InMemoryAppointmentDao;
import dao.impl.InMemoryUserDao;
import entity.Appointment;
import entity.util.SlotStatus;
import service.AppointmentService;
import service.dto.AppointmentSlotDto;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Date;
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
