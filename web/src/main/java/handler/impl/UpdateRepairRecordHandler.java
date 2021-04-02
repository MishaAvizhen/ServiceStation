package handler.impl;

import converters.impl.RepairRecordFromDtoToUpdateToRegistrationDtoConverter;
import dto.RepairRecordDtoToUpdate;
import dto.errorHandling.ErrorDto;
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
import java.util.HashMap;
import java.util.Map;

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
        try {
            RepairRecordDtoToUpdate repairRecordDtoToUpdate = readRequestEntity(RepairRecordDtoToUpdate.class, request);
            Long repairRecordId = repairRecordDtoToUpdate.getRepairRecordId();
            RepairRecord repairRecordById = repairRecordService.findRepairRecordById(repairRecordId);
            if (repairRecordById != null) {
                RepairRecordRegistrationDto repairRecordRegistrationDto =
                        repairRecordFromDtoToUpdateToRegistrationDtoConverter.convertFromSourceDtoToTargetDto(repairRecordDtoToUpdate);
                RepairRecord repairRecordAfterUpdate = repairRecordService.updateRepairRecord(repairRecordRegistrationDto, repairRecordById);
                writeResponseAsJson(repairRecordAfterUpdate, response);
            } else {
                throw new UnsupportedOperationException("RepairRecord with id " + repairRecordById.getId() + " not found");

            }
        } catch (UnsupportedOperationException e) {
            ErrorDto errorDto = new ErrorDto(e.getMessage(), "admin@mail.ru");
            writeErrorResponseAsJson(errorDto, response, HttpServletResponse.SC_NOT_FOUND);
        } catch (RuntimeException e) {
            ErrorDto errorDto = new ErrorDto(e.getMessage(), "admin@mail.ru");
            writeErrorResponseAsJson(errorDto, response, HttpServletResponse.SC_BAD_REQUEST);
        } catch (Exception e) {
            Map<String, String> errorDto = new HashMap<>();
            errorDto.put("message", e.getMessage());
            writeErrorResponseAsJson(errorDto, response, HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public String getHandledURI() {
        return "/api/repairRecords/updateRepairRecord";
    }
}
