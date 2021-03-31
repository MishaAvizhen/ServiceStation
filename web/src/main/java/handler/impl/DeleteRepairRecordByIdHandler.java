package handler.impl;

import handler.StoRestHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import service.RepairRecordService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class DeleteRepairRecordByIdHandler extends StoRestHandler {

    private RepairRecordService repairRecordService;

    @Autowired
    public DeleteRepairRecordByIdHandler(RepairRecordService repairRecordService) {
        this.repairRecordService = repairRecordService;
    }

    @Override
    public void handleDoGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Long recordId = Long.valueOf(request.getParameter("recordId"));
        repairRecordService.deleteRepairRecordById(recordId);
        writeResponseAsJson("Repair record with id: " + recordId + " was delete", response);

    }

    @Override
    public String getHandledURI() {
        return "/api/repairRecords/deleteById";
    }
}
