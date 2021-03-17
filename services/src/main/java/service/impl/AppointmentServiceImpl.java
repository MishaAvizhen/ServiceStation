package service.impl;

import dao.AppointmentDao;
import dao.BeanManager;
import dao.UserDao;
import entity.Appointment;
import entity.consts.SlotStatus;
import service.AppointmentService;
import service.dto.AppointmentSlotDto;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Date;
import java.util.List;

public class AppointmentServiceImpl implements AppointmentService {
    private AppointmentDao appointmentDao = BeanManager.getInstance().getAppointment();
    private UserDao userDao = BeanManager.getInstance().getUserDao();

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

    @Override
    public Appointment createAppointment(AppointmentSlotDto appointmentSlotDto, Long userId) {
        LocalDateTime startDateInLocalDate = appointmentSlotDto.getStartDate();
        LocalDateTime endDateInLocalDate = appointmentSlotDto.getEndDate();
        ZonedDateTime zdt = startDateInLocalDate.atZone(ZoneId.systemDefault());
        ZonedDateTime zdt1 = endDateInLocalDate.atZone(ZoneId.systemDefault());
        Date startDateInDate = Date.from(zdt.toInstant());
        Date endDateInDate = Date.from(zdt1.toInstant());
        Appointment appointment = new Appointment();
        appointment.setMaster(appointmentSlotDto.getMaster());
        appointment.setStartDate(startDateInDate);
        appointment.setEndDate(endDateInDate);
        appointment.setSlotStatus(SlotStatus.BUSY_STATUS);
        appointment.setClient(userDao.findById(userId));
        appointment.setNotes(" notes...");
        return appointmentDao.save(appointment);
    }

}
