package handler.impl;

import handler.StoRestHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import service.RepairRequestService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class DeleteRepairRequestByIdHandler extends StoRestHandler {
    private RepairRequestService repairRequestService;

    @Autowired
    public DeleteRepairRequestByIdHandler(RepairRequestService repairRequestService) {
        this.repairRequestService = repairRequestService;
    }

    @Override
    public void handleDoGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Long repairRequestId = Long.valueOf(request.getParameter("repairRequestId"));
        repairRequestService.deleteRepairRequestById(repairRequestId);

        writeResponseAsJson("Repair Request with id: " + repairRequestId + " was delete", response);


    }

    @Override
    public String getHandledURI() {
        return "/api/repairRequests/deleteById";
    }
}
