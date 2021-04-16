package converters.impl;

import converters.Converter;
import dto.RepairRecordRegistrationWebDto;
import org.springframework.stereotype.Component;
import service.dto.RepairRecordRegistrationDto;

@Component
// TODO сделать название короче
public class RepairRecordWebConverter implements
        Converter<RepairRecordRegistrationWebDto, RepairRecordRegistrationDto> {

    @Override
    public RepairRecordRegistrationDto convertToServiceDto(RepairRecordRegistrationWebDto webDto) {
        return new RepairRecordRegistrationDto.Builder()
                .setRepairRecordDescription(webDto.getRepairRecordDescription())
                .setRepairRequest(webDto.getRepairRequestId())
                .setDetailPrice(webDto.getDetailPrice())
                .setWorkPrice(webDto.getWorkPrice())
                .setOtherNotes(webDto.getOtherNotes())
                .build();
    }

    @Override
    public RepairRecordRegistrationWebDto convertToWebDto(RepairRecordRegistrationDto serviceDto) {
        throw new UnsupportedOperationException("Convertation is not supported");
    }
}
