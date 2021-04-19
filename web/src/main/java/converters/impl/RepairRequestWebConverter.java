package converters.impl;

import converters.Converter;
import dto.RepairRequestRegistrationWebDto;
import entity.enums.RepairRequestStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import service.dto.AppointmentSlotDto;
import service.dto.RepairRequestRegistrationDto;

import java.util.Date;

@Component
public class RepairRequestWebConverter implements
        Converter<RepairRequestRegistrationWebDto, RepairRequestRegistrationDto> {

    private AppointmentSlotWebConverter slotWebConverter;

    @Autowired
    public RepairRequestWebConverter(AppointmentSlotWebConverter slotWebConverter) {
        this.slotWebConverter = slotWebConverter;
    }

    @Override
    public RepairRequestRegistrationDto convertToServiceDto(RepairRequestRegistrationWebDto webDto) {
        AppointmentSlotDto appointmentSlotDto = slotWebConverter
                .convertToWebDto(webDto.getAppointmentSlotWebDto());
        return new RepairRequestRegistrationDto.Builder()
                .setDateOfRequest(new Date())
                .setRepairRequestDescription(webDto.getRepairRequestDescription())
                .setRepairRequestStatus(RepairRequestStatus.IN_PROGRESS)
                .setCarRemark(webDto.getCarRemark())
                .setUsername(webDto.getClientUsername())
                .setAppointmentSlotDto(appointmentSlotDto)
                .build();
    }

    @Override
    public RepairRequestRegistrationWebDto convertToWebDto(RepairRequestRegistrationDto serviceDto) {
        throw new UnsupportedOperationException("Convertation is not supported");
    }
}
