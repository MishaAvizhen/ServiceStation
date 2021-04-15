package service.impl;

import entity.Appointment;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import repository.AppointmentRepository;
import repository.RepairRequestRepository;
import repository.UserRepository;
import service.AppointmentService;
import service.AppointmentSlotService;
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
    @Autowired
    private RepairRequestRepository repairRequestRepository;
    @Autowired
    private AppointmentSlotService appointmentSlotService;

    @Override
    // TODO добавить в контроллер
    public List<Appointment> findAllAppointment() {
        log.info("Find all appointments");
        return appointmentRepository.findAll();
    }

    @Override
    public Appointment createAppointment(AppointmentSlotDto appointmentSlotDto, Long userId, Long repairRequestId) {
        if (!appointmentSlotService.isAppointmentSlotAvailable(appointmentSlotDto)) {
            throw new IllegalArgumentException("Appointment slot is Busy");
        }
        Date startDate = LocalDateTimeOperations.convertLocalDateTimeToDate(appointmentSlotDto.getStartDate());
        Date endDate = LocalDateTimeOperations.convertLocalDateTimeToDate(appointmentSlotDto.getEndDate());
        if (startDate.before(new Date()) || endDate.before(new Date())) {
            log.info(String.format("Appointment was not created, Date {%s} - {%s} incorrect! Date in the  past ", startDate, endDate));
            throw new IllegalArgumentException("Date incorrect! Date in the  past");
        } else {
            // TODO перенести в конвертер
            Appointment appointment = new Appointment();
            appointment.setMaster(appointmentSlotDto.getMaster());
            appointment.setStartDate(startDate);
            appointment.setEndDate(endDate);
            appointment.setSlotStatus(appointmentSlotDto.getSlotStatus());
            appointment.setClient(userRepository.getOne(userId));
            appointment.setNotes(" notes...");
            appointment.setRepairRequest(repairRequestRepository.getOne(repairRequestId));
            log.info(String.format("Appointment with info: {%s}, {%s} was created for user with id: {%s} ",
                    appointmentSlotDto.getStartDate(), appointmentSlotDto.getEndDate(), userId));
            return appointmentRepository.save(appointment);
        }
    }
}
