package service.converters.impl;

import entity.Appointment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import repository.UserRepository;
import service.converters.Converter;
import service.dto.AppointmentSlotDto;

@Component
public class AppointmentConverter implements Converter<Appointment, AppointmentSlotDto> {
    private UserRepository userRepository;

    @Autowired
    public AppointmentConverter(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public Appointment convertToEntity(AppointmentSlotDto dto) {
        Appointment appointment = new Appointment();
        return convertToExistingEntity(dto, appointment);
    }

    @Override
    public Appointment convertToExistingEntity(AppointmentSlotDto dto, Appointment entity) {
        return null;
    }

    @Override
    public AppointmentSlotDto convertToDto(Appointment entity) {
        return null;
    }
}
