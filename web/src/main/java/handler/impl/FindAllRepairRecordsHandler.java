package handler.impl;

import converters.impl.RepairRecordToRepairRecordWebDtoConverter;
import dto.RepairRecordWebDto;
import entity.RepairRecord;
import handler.StoRestHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import service.RepairRecordService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class FindAllRepairRecordsHandler extends StoRestHandler {

    private RepairRecordService repairRecordService;
    private RepairRecordToRepairRecordWebDtoConverter repairRecordToRepairRecordWebDtoConverter;

    @Autowired
    public FindAllRepairRecordsHandler(RepairRecordService repairRecordService, RepairRecordToRepairRecordWebDtoConverter repairRecordToRepairRecordWebDtoConverter) {
        this.repairRecordService = repairRecordService;
        this.repairRecordToRepairRecordWebDtoConverter = repairRecordToRepairRecordWebDtoConverter;
    }

    @Override
    public void handleDoGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String username = request.getParameter("username");
        String carRemark = request.getParameter("carRemark");
        String repairRecordId = request.getParameter("repairRecordId");

        List<RepairRecord> allRepairRecords = repairRecordService.findAllRepairRecords();
        List<RepairRecord> filteredRecords = allRepairRecords.stream()
                .filter(repairRecord -> username == null || username.equals(repairRecord.getRepairRequest().getUser().getUsername()))
                .filter(repairRecord -> carRemark == null || carRemark.equals(repairRecord.getRepairRequest().getCarRemark()))
                .filter(repairRecord -> repairRecordId == null || repairRecordId.equals(repairRecord.getId().toString()))
                .collect(Collectors.toList());

        List<RepairRecordWebDto> recordWebDtoList = new ArrayList<>();
        for (RepairRecord record : filteredRecords) {
            RepairRecordWebDto repairRecordWebDto = repairRecordToRepairRecordWebDtoConverter.convertToDto(record);
            recordWebDtoList.add(repairRecordWebDto);

        }
        writeResponseAsJson(recordWebDtoList, response);

    }

    @Override
    public String getHandledURI() {
        return "/api/repairRecords";
    }
}
