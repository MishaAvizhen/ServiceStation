package service.impl;

import entity.Appointment;
import entity.consts.SlotStatus;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import repository.AppointmentRepository;
import repository.UserRepository;
import service.AppointmentService;
import service.dto.AppointmentSlotDto;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Date;
import java.util.List;
@Service

public class AppointmentServiceImpl implements AppointmentService {
    private static final Logger log = Logger.getLogger(AppointmentServiceImpl.class);
    @Autowired
    private AppointmentRepository appointmentRepository;
    @Autowired
    private UserRepository userRepository ;

    @Override
    public List<Appointment> findAllAppointment() {
        log.info(String.format("Find all appointments"));
        return appointmentRepository.findAll();
    }

    @Override
    public Appointment createAppointment(AppointmentSlotDto appointmentSlotDto, Long userId) {
        log.info(String.format("Appointment with info : {%s} was created \n for user with id: {%s} ", appointmentSlotDto.toString(), userId));
        log.debug(String.format("Appointment with info : {%s} was created \n for user with id: {%s} ", appointmentSlotDto.toString(), userId));
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
        appointment.setSlotStatus(SlotStatus.BUSY);
        appointment.setClient(userRepository.findOne(userId));
        appointment.setNotes(" notes...");
        return appointmentRepository.save(appointment);
    }

}
