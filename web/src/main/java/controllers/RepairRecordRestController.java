package controllers;

import converters.impl.RepairRecordFromDtoToUpdateToRegistrationDtoConverter;
import converters.impl.RepairRecordFromRegistrationWebDtoToRegistrationDtoConverter;
import dto.RepairRecordDtoToUpdate;
import dto.RepairRecordRegistrationWebDto;
import entity.RepairRecord;
import org.springframework.web.bind.annotation.*;
import service.RepairRecordService;
import service.dto.RepairRecordRegistrationDto;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "/api")
public class RepairRecordRestController {
    private RepairRecordService repairRecordService;
    private RepairRecordFromRegistrationWebDtoToRegistrationDtoConverter repairRecordFromRegistrationWebDtoToRegistrationDtoConverter;
    private RepairRecordFromDtoToUpdateToRegistrationDtoConverter repairRecordFromDtoToUpdateToRegistrationDtoConverter;

    public RepairRecordRestController(RepairRecordService repairRecordService, RepairRecordFromRegistrationWebDtoToRegistrationDtoConverter repairRecordFromRegistrationWebDtoToRegistrationDtoConverter, RepairRecordFromDtoToUpdateToRegistrationDtoConverter repairRecordFromDtoToUpdateToRegistrationDtoConverter) {
        this.repairRecordService = repairRecordService;
        this.repairRecordFromRegistrationWebDtoToRegistrationDtoConverter = repairRecordFromRegistrationWebDtoToRegistrationDtoConverter;
        this.repairRecordFromDtoToUpdateToRegistrationDtoConverter = repairRecordFromDtoToUpdateToRegistrationDtoConverter;
    }

    @GetMapping("/records")
    public List<RepairRecord> getAllRepairRecords(@RequestParam(value = "username", required = false) String username,
                                                  @RequestParam(value = "carRemark", required = false) String carRemark,
                                                  @RequestParam(value = "repairRecordId", required = false) String repairRecordId) {

        List<RepairRecord> allRepairRecords = repairRecordService.findAllRepairRecords();
        return allRepairRecords.stream()
                .filter(repairRecord -> username == null || username.equals(repairRecord.getRepairRequest().getUser().getUsername()))
                .filter(repairRecord -> carRemark == null || carRemark.equals(repairRecord.getRepairRequest().getCarRemark()))
                .filter(repairRecord -> repairRecordId == null || repairRecordId.equals(repairRecord.getId().toString()))
                .collect(Collectors.toList());
    }

    @GetMapping("/records/{repairRecordId}")
    public RepairRecord getRepairRecord(@PathVariable Long repairRecordId) {
        return repairRecordService.findRepairRecordById(repairRecordId);
    }

    @GetMapping("/records/delete/{repairRecordId}")
    public void deleteRepairRecordById(@PathVariable Long repairRecordId) {
        repairRecordService.deleteRepairRecordById(repairRecordId);
    }

    @PostMapping("/records/{repairRecordId}/update")
    public RepairRecord getUpdatedRepairRecord(@RequestBody RepairRecordDtoToUpdate repairRecordDtoToUpdate,
                                               @PathVariable Long repairRecordId) {
        RepairRecord repairRecordToUpdate = repairRecordService.findRepairRecordById(repairRecordId);
        if (repairRecordToUpdate == null) {
            throw new UnsupportedOperationException("RepairRecord with id " + repairRecordDtoToUpdate.getRepairRecordId() + " not found");

        } else {

            RepairRecordRegistrationDto repairRecordRegistrationDto =
                    repairRecordFromDtoToUpdateToRegistrationDtoConverter.convertFromSourceDtoToTargetDto(repairRecordDtoToUpdate);
            return repairRecordService.updateRepairRecord(repairRecordRegistrationDto, repairRecordToUpdate);
        }
    }

    @PostMapping("/records/create")
    public RepairRecord getCreatedRepairRecord(@RequestBody RepairRecordRegistrationWebDto repairRecordRegistrationWebDto) {
        RepairRecordRegistrationDto repairRecordRegistrationDto =
                repairRecordFromRegistrationWebDtoToRegistrationDtoConverter.convertFromSourceDtoToTargetDto(repairRecordRegistrationWebDto);
        return repairRecordService.registerRepairRecord(repairRecordRegistrationDto);
    }
}
