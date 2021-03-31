package handler.impl;

import converters.impl.RepairRecordFromDtoToUpdateToRegistrationDtoConverter;
import dto.RepairRecordDtoToUpdate;
import entity.RepairRecord;
import handler.StoRestHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import service.RepairRecordService;
import service.dto.RepairRecordRegistrationDto;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class UpdateRepairRecordHandler extends StoRestHandler {

    private RepairRecordService repairRecordService;
    private RepairRecordFromDtoToUpdateToRegistrationDtoConverter repairRecordFromDtoToUpdateToRegistrationDtoConverter;

    @Autowired
    public UpdateRepairRecordHandler(RepairRecordService repairRecordService,
                                     RepairRecordFromDtoToUpdateToRegistrationDtoConverter repairRecordFromDtoToUpdateToRegistrationDtoConverter) {
        this.repairRecordService = repairRecordService;
        this.repairRecordFromDtoToUpdateToRegistrationDtoConverter = repairRecordFromDtoToUpdateToRegistrationDtoConverter;
    }

    @Override
    public void handleDoPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        RepairRecordDtoToUpdate repairRecordDtoToUpdate = readRequestEntity(RepairRecordDtoToUpdate.class, request);
        Long repairRecordId = repairRecordDtoToUpdate.getRepairRecordId();
        RepairRecord repairRecordById = repairRecordService.findRepairRecordById(repairRecordId);
        if (repairRecordById != null) {
            RepairRecordRegistrationDto repairRecordRegistrationDto = repairRecordFromDtoToUpdateToRegistrationDtoConverter.convertFromSourceDtoToTargetDto(repairRecordDtoToUpdate);
            RepairRecord repairRecordAfterUpdate = repairRecordService.updateRepairRecord(repairRecordRegistrationDto, repairRecordById);
            writeResponseAsJson(repairRecordAfterUpdate, response);

        }
    }

    @Override
    public String getHandledURI() {
        return "/api/repairRecords/updateRepairRecord";
    }
}
