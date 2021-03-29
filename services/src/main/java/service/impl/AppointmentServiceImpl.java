package service.impl;

import entity.Appointment;
import entity.consts.SlotStatus;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import repository.AppointmentRepository;
import repository.UserRepository;
import service.AppointmentService;
import service.common.LocalDateTimeOperations;
import service.dto.AppointmentSlotDto;

import java.util.Date;
import java.util.List;

@Service

public class AppointmentServiceImpl implements AppointmentService {
    private static final Logger log = Logger.getLogger(AppointmentServiceImpl.class);
    @Autowired
    private AppointmentRepository appointmentRepository;
    @Autowired
    private UserRepository userRepository;

    @Override
    public List<Appointment> findAllAppointment() {
        log.info(String.format("Find all appointments"));
        return appointmentRepository.findAll();
    }

    @Override
    public Appointment createAppointment(AppointmentSlotDto appointmentSlotDto, Long userId) {
        log.info(String.format("Appointment with info : {%s} was created \n for user with id: {%s} ", appointmentSlotDto.toString(), userId));
        log.debug(String.format("Appointment with info : {%s} was created \n for user with id: {%s} ", appointmentSlotDto.toString(), userId));
        Date startDateInDate = LocalDateTimeOperations.convertLocalDateTimeToDate(appointmentSlotDto.getStartDate());
        Date endDateInDate = LocalDateTimeOperations.convertLocalDateTimeToDate(appointmentSlotDto.getEndDate());
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
