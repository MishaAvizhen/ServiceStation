package service.impl;

import com.google.common.base.Preconditions;
import entity.Appointment;
import entity.User;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import repository.AppointmentRepository;
import service.AppointmentService;
import service.AppointmentSlotService;
import service.UserService;
import service.converters.impl.AppointmentConverter;
import service.dto.AppointmentFilterDto;
import service.dto.AppointmentSlotDto;
import service.dto.MasterWorkInDetails;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class AppointmentServiceImpl implements AppointmentService {
    private static final Logger log = Logger.getLogger(AppointmentServiceImpl.class);
    @Autowired
    private AppointmentRepository appointmentRepository;
    @Autowired
    private AppointmentSlotService appointmentSlotService;
    @Autowired
    private AppointmentConverter appointmentConverter;
    @Autowired
    private UserService userService;

    @Override
    public List<Appointment> filterAppointments(AppointmentFilterDto filterDto) {
        List<Appointment> list = appointmentRepository.findAll();
        return list.stream()
                .filter(appointment -> filterDto.getUsername() == null || filterDto.getUsername().equals(appointment.getClient().getUsername()))
                .filter(appointment -> filterDto.getMasterName() == null || filterDto.getMasterName().equals(appointment.getMaster().getUsername()))
                .filter(appointment -> filterDto.getStatus() == null || filterDto.getStatus().equals(appointment.getSlotStatus().toString()))
                .collect(Collectors.toList());
    }

    @Override
    public List<MasterWorkInDetails> getMasterRecords(String masterName) {
        log.info(String.format(" Find master with name  {%s} ", masterName));
        User master = userService.findUserByUsername(masterName);
        Preconditions.checkNotNull(master, "Master with name " + masterName + " not found");
        return appointmentConverter.convertToEntity(masterName);
    }

    @Override
    public Appointment createAppointment(AppointmentSlotDto appointmentSlotDto, Long userId, Long repairRequestId) {
        if (!appointmentSlotService.isAppointmentSlotAvailable(appointmentSlotDto)) {
            throw new IllegalArgumentException("Appointment slot is Busy");
        }
        LocalDateTime lt = LocalDateTime.now();
        LocalDateTime startDate = appointmentSlotDto.getStartDate();
        LocalDateTime endDate = appointmentSlotDto.getEndDate();
        if (startDate.isBefore(lt) || endDate.isBefore(lt)) {
            throw new IllegalArgumentException("Date incorrect! Date in the  past");
        } else {
            // TODO перенести в конвертер
            Appointment appointment = appointmentConverter.convertToEntity(appointmentSlotDto, userId, repairRequestId);
            log.info(String.format("Appointment with info: {%s}, {%s} was created for user with id: {%s} ",
                    startDate, endDate, userId));
            return appointmentRepository.save(appointment);
        }
    }

    @Override
    public List<Appointment> findAllAppointments() {
        log.info("Find all appointments");
        return appointmentRepository.findAll();
    }
}
