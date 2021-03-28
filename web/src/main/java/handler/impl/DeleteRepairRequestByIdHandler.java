package handler.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import handler.StoHandlerAdapter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import service.RepairRequestService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@Component
public class DeleteRepairRequestByIdHandler extends StoHandlerAdapter {
    private RepairRequestService repairRequestService;

    @Autowired
    public DeleteRepairRequestByIdHandler(RepairRequestService repairRequestService) {
        this.repairRequestService = repairRequestService;
    }

    @Override
    public void handleDoGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Long repairRequestId = Long.valueOf(request.getParameter("repairRequestId"));
        repairRequestService.deleteRepairRequestById(repairRequestId);

        PrintWriter out = response.getWriter();
        ObjectMapper objectMapper = new ObjectMapper();
        String jsonString = objectMapper.writeValueAsString("Repair Request with id: " + repairRequestId + " was delete");
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        out.print(jsonString);
        out.flush();

    }

    @Override
    public String getHandledURI() {
        return "/api/repairRequests/deleteById";
    }
}
