package service.impl;

import entity.Appointment;
import entity.User;
import entity.consts.Role;
import org.apache.commons.lang3.time.DateUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import repository.AppointmentRepository;
import service.AppointmentSlotService;
import service.UserService;
import service.dto.AppointmentSlotDto;
import service.dto.WorkingHoursDto;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toList;
import static service.common.LocalDateTimeOperations.convertDateToLocalDateTime;

@Service
public class AppointmentSlotServiceImpl implements AppointmentSlotService {
    private static final Logger log = Logger.getLogger(AppointmentSlotServiceImpl.class);
    @Autowired
    private UserService userService;
    @Autowired
    private AppointmentRepository appointmentRepository;

    private final int startWorkHour = 8;
    private final int endWorkHour = 20;

    @Override
    public List<AppointmentSlotDto> getAvailableAppointmentSlotsByDate(Date date) {
        log.info(String.format("get available appointment slot to date: {%s}", date));

        Date dateWOHours = trancateDateToDays(date);
        WorkingHoursDto workHoursByDate = getWorkHoursByDate(dateWOHours);
        Date startWorkDate = DateUtils.addHours(dateWOHours, workHoursByDate.getStartWorkHour());
        Date endWorkDate = DateUtils.addHours(dateWOHours, workHoursByDate.getEndWorkHour());
        LocalDateTime convertedStartWorkDay = convertDateToLocalDateTime(startWorkDate);
        LocalDateTime convertedEndWorkDay = convertDateToLocalDateTime(endWorkDate);
        List<Map.Entry<LocalDateTime, LocalDateTime>> intervalsByHours = separateDayToSlots(convertedStartWorkDay, convertedEndWorkDay);
        List<User> allMasters = findAllMasters();
        List<AppointmentSlotDto> appointmentSlotDtos = new ArrayList<>();
        for (User master : allMasters) {
            for (Map.Entry<LocalDateTime, LocalDateTime> intervalsByHour : intervalsByHours) {
                appointmentSlotDtos.add(new AppointmentSlotDto(master, intervalsByHour.getKey(), intervalsByHour.getValue()));
            }
        }
        List<Appointment> allAppointments = appointmentRepository.findAll();
        return appointmentSlotDtos.stream()
                .filter(appointmentSlotDto ->
                        allAppointments.stream()
                                .noneMatch(appointment -> isAppointmentSlotEqualsToAppointment(appointmentSlotDto, appointment)))
                .collect(Collectors.toList());
    }

    private boolean isAppointmentSlotEqualsToAppointment(AppointmentSlotDto appointmentSlotDto, Appointment appointment) {
        if (appointmentSlotDto.getMaster().equals(appointment.getMaster()) &&
                appointmentSlotDto.getStartDate().equals(convertDateToLocalDateTime(appointment.getStartDate())) &&
                appointmentSlotDto.getEndDate().equals(convertDateToLocalDateTime(appointment.getEndDate()))) {
            return true;
        }
        return false;
    }


    @Override
    public List<AppointmentSlotDto> getAvailableAppointmentSlotsByDates(Date start, Date end) {
        Date currentDate = start;
        List<AppointmentSlotDto> result = new ArrayList<>();
        while (currentDate.before(end)) {
            result.addAll(getAvailableAppointmentSlotsByDate(currentDate));
            currentDate = DateUtils.addDays(currentDate, 1);
        }
        return result;
    }

    private List<User> findAllMasters() {
        return userService.findAllUsers().stream()
                .filter(user -> user.getRole().equals(Role.MASTER))
                .collect(toList());
    }

    private List<Map.Entry<LocalDateTime, LocalDateTime>> separateDayToSlots(LocalDateTime start, LocalDateTime end) {
        List<Map.Entry<LocalDateTime, LocalDateTime>> result = new ArrayList<>();
        while (start.isBefore(end)) {
            result.add(new AbstractMap.SimpleEntry<>(start, start.plusHours(1)));
            start = start.plusHours(1);
        }
        return result;
    }

    private Date trancateDateToDays(Date dateTime) {
        return DateUtils.truncate(dateTime, Calendar.DATE);
    }

    private WorkingHoursDto getWorkHoursByDate(Date date) {
        return new WorkingHoursDto(startWorkHour, endWorkHour);
    }
}
