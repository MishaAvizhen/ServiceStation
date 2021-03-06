package service.converters.impl;

import dao.AppointmentDao;
import dao.UserDao;
import dao.impl.InMemoryAppointmentDao;
import dao.impl.InMemoryUserDao;
import entity.Appointment;
import entity.constants.SlotStatus;
import service.converters.AppointmentConverterService;
import service.dto.AppointmentSlotDto;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Date;

public class AppointmentConverterImpl implements AppointmentConverterService {
    private AppointmentDao appointmentDao = InMemoryAppointmentDao.getInstance();
    private UserDao userDao = InMemoryUserDao.getInstance();

    private static AppointmentConverterImpl appointmentConverter;

    private AppointmentConverterImpl() {
    }

    public static AppointmentConverterImpl getInstance() {
        if (appointmentConverter == null) {
            appointmentConverter = new AppointmentConverterImpl();
        }
        return appointmentConverter;
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