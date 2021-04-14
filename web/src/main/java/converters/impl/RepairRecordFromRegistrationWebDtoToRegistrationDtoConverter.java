package converters.impl;

import converters.ConverterFromFirstDtoToSecond;
import dto.RepairRecordRegistrationWebDto;
import org.springframework.stereotype.Component;
import service.dto.RepairRecordRegistrationDto;

@Component
public class RepairRecordFromRegistrationWebDtoToRegistrationDtoConverter implements
        ConverterFromFirstDtoToSecond<RepairRecordRegistrationWebDto, RepairRecordRegistrationDto>{


    @Override
    public RepairRecordRegistrationDto convertFromSourceDtoToTargetDto(RepairRecordRegistrationWebDto sourceDto) {
        return new RepairRecordRegistrationDto.Builder()
                .setRepairRecordDescription(sourceDto.getRepairRecordDescription())
                .setRepairRequest(sourceDto.getRepairRequestId())
                .setDetailPrice(sourceDto.getDetailPrice())
                .setWorkPrice(sourceDto.getWorkPrice())
                .setOtherNotes(sourceDto.getOtherNotes())
                .build();
    }
}
