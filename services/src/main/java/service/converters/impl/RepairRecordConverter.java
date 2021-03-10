package service.converters.impl;

import dao.RepairRequestDao;
import dao.impl.InMemoryRepairRequestDao;
import entity.RepairRecord;
import entity.RepairRequest;
import service.converters.Converter;
import service.dto.RepairRecordRegistrationDto;

public class RepairRecordConverter implements Converter<RepairRecord, RepairRecordRegistrationDto> {
    private RepairRequestDao repairRequestDao = InMemoryRepairRequestDao.getInstance();

    public RepairRecordConverter() {
    }

    @Override
    public RepairRecord convertToEntity(RepairRecordRegistrationDto dto) {

        RepairRecord repairRecord = new RepairRecord();

        return convertToExistingEntity(dto, repairRecord);
    }

    @Override
    public RepairRecord convertToExistingEntity(RepairRecordRegistrationDto dto, RepairRecord entity) {
        RepairRequest repairRequestById = repairRequestDao.findById(dto.getRepairRequestId());
        entity.setRepairRequest(repairRequestById);
        entity.setOtherNotes(dto.getOtherNotes());
        entity.setRepairRecordDescription(dto.getRepairRecordDescription());
        entity.setDetailPrice(dto.getDetailPrice());
        entity.setWorkPrice(dto.getWorkPrice());
        return entity;
    }

    @Override
    public RepairRecordRegistrationDto convertToDto(RepairRecord entity) {
        return new RepairRecordRegistrationDto.Builder()
                .setRepairRequest(entity.getRepairRequest().getId())
                .setOtherNotes(entity.getOtherNotes())
                .setRepairRecordDescription(entity.getRepairRecordDescription())
                .setWorkPrice(entity.getWorkPrice())
                .setDetailPrice(entity.getDetailPrice())
                .build();
    }

}
