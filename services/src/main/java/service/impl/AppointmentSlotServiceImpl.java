package service.impl;

import entity.Appointment;
import entity.User;
import entity.enums.Role;
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

import static service.common.LocalDateTimeOperations.convertDateToLocalDateTime;
import static service.common.LocalDateTimeOperations.convertLocalDateTimeToDate;

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
        if (!isTargetDateNotInPast(date)) {
            throw new IllegalArgumentException("Error!!! Date in the past");
        }

        List<AppointmentSlotDto> appointmentSlotDtos = getAllAppointmentSlotsByDate(date);
        List<Appointment> allAppointments = appointmentRepository.findAll();
        return appointmentSlotDtos.stream()
                .filter(appointmentSlotDto ->
                        allAppointments.stream()
                                .noneMatch(appointment -> isAppointmentSlotEqualsToAppointment(appointmentSlotDto, appointment)))
                .collect(Collectors.toList());
    }

    private List<AppointmentSlotDto> getAllAppointmentSlotsByDate(Date date) {
        Date dateWOHours = truncateDateToDays(date);
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
        return appointmentSlotDtos;
    }

    private boolean isAppointmentSlotEqualsToAppointment(AppointmentSlotDto appointmentSlotDto, Appointment appointment) {
        return appointmentSlotDto.getMaster().equals(appointment.getMaster()) &&
                appointmentSlotDto.getStartDate().equals(convertDateToLocalDateTime(appointment.getStartDate())) &&
                appointmentSlotDto.getEndDate().equals(convertDateToLocalDateTime(appointment.getEndDate()));
    }

    private boolean isTargetDateNotInPast(Date targetDate) {
        Date yesterday = new Date(System.currentTimeMillis() - 24 * 60 * 60 * 1000);
        return targetDate.after(yesterday);
    }


    @Override
    public List<AppointmentSlotDto> getAllAppointmentSlotsByDates(Date start, Date end) {
        log.info(String.format("get available appointment slot to dates: {%s} - {%s}", start, end));
        Date currentDate = start;
        List<AppointmentSlotDto> result = new ArrayList<>();
        while (currentDate.before(end)) {
            result.addAll(getAllAppointmentSlotsByDate(currentDate));
            currentDate = DateUtils.addDays(currentDate, 1);
        }
        return result;
    }

    @Override
    public boolean isAppointmentSlotAvailable(AppointmentSlotDto appointmentSlotDto) {
        List<AppointmentSlotDto> availableAppointmentSlotsByDate =
                getAvailableAppointmentSlotsByDate(convertLocalDateTimeToDate(appointmentSlotDto.getStartDate()));
        return availableAppointmentSlotsByDate.contains(appointmentSlotDto);
    }

    private List<User> findAllMasters() {
        return userService.findAllUsers().stream()
                .filter(user -> user.getRole().equals(Role.MASTER))
                .collect(Collectors.toList());
    }

    private List<Map.Entry<LocalDateTime, LocalDateTime>> separateDayToSlots(LocalDateTime start, LocalDateTime end) {
        List<Map.Entry<LocalDateTime, LocalDateTime>> result = new ArrayList<>();
        while (start.isBefore(end)) {
            result.add(new AbstractMap.SimpleEntry<>(start, start.plusHours(1)));
            start = start.plusHours(1);
        }
        return result;
    }

    private Date truncateDateToDays(Date dateTime) {
        return DateUtils.truncate(dateTime, Calendar.DATE);
    }

    private WorkingHoursDto getWorkHoursByDate(Date date) {
        return new WorkingHoursDto(startWorkHour, endWorkHour);
    }
}
