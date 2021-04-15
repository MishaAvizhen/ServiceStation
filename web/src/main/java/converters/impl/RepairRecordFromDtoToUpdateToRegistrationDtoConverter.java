package converters.impl;

import converters.ConverterFromFirstDtoToSecond;
import dto.RepairRecordDtoToUpdate;
import org.springframework.stereotype.Component;
import service.dto.RepairRecordRegistrationDto;

@Component
public class RepairRecordFromDtoToUpdateToRegistrationDtoConverter implements
        ConverterFromFirstDtoToSecond<RepairRecordDtoToUpdate, RepairRecordRegistrationDto> {

    @Override
    public RepairRecordRegistrationDto convertFromSourceDtoToTargetDto(RepairRecordDtoToUpdate sourceDto) {
        return new RepairRecordRegistrationDto.Builder()
                .setRepairRecordDescription(sourceDto.getRepairRecordDescription())
                .setWorkPrice(sourceDto.getWorkPrice())
                .setOtherNotes(sourceDto.getOtherNotes())
                .setDetailPrice(sourceDto.getDetailPrice())
                .setRepairRequest(sourceDto.getRepairRequestId())
                .build();
    }
}
