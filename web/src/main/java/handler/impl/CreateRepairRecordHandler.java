package handler.impl;

import converters.impl.RepairRecordFromRegistrationWebDtoToRegistrationDtoConverter;
import converters.impl.RepairRecordToRepairRecordWebDtoConverter;
import dto.RepairRecordRegistrationWebDto;
import entity.RepairRecord;
import handler.StoRestHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import service.RepairRecordService;
import service.RepairRequestService;
import service.dto.RepairRecordRegistrationDto;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class CreateRepairRecordHandler extends StoRestHandler {

    private RepairRecordService repairRecordService;
    private RepairRequestService repairRequestService;
    private RepairRecordToRepairRecordWebDtoConverter repairRecordToRepairRecordWebDtoConverter;
    private RepairRecordFromRegistrationWebDtoToRegistrationDtoConverter repairRecordFromRegistrationWebDtoToRegistrationDtoConverter;

    @Autowired
    public CreateRepairRecordHandler(RepairRecordService repairRecordService, RepairRequestService repairRequestService,
                                     RepairRecordToRepairRecordWebDtoConverter repairRecordToRepairRecordWebDtoConverter,
                                     RepairRecordFromRegistrationWebDtoToRegistrationDtoConverter repairRecordFromRegistrationWebDtoToRegistrationDtoConverter) {
        this.repairRecordService = repairRecordService;
        this.repairRequestService = repairRequestService;
        this.repairRecordToRepairRecordWebDtoConverter = repairRecordToRepairRecordWebDtoConverter;
        this.repairRecordFromRegistrationWebDtoToRegistrationDtoConverter = repairRecordFromRegistrationWebDtoToRegistrationDtoConverter;
    }

    @Override
    public void handleDoPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        RepairRecordRegistrationWebDto repairRecordRegistrationWebDto =
                readRequestEntity(RepairRecordRegistrationWebDto.class, request);

        RepairRecordRegistrationDto repairRecordRegistrationDto =
                repairRecordFromRegistrationWebDtoToRegistrationDtoConverter.convertFromSourceDtoToTargetDto(repairRecordRegistrationWebDto);

        RepairRecord repairRecord = repairRecordService.registerRepairRecord(repairRecordRegistrationDto);

        writeResponseAsJson(repairRecordToRepairRecordWebDtoConverter.convertToDto(repairRecord), response);

    }


    @Override
    public String getHandledURI() {
        return "/api/repairRecords/createRecord";
    }
}
