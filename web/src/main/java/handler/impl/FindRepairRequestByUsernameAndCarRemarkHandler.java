package handler.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import converters.impl.RepairRequestWebConverter;
import dto.RepairRequestWebDto;
import entity.RepairRequest;
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
public class FindRepairRequestByUsernameAndCarRemarkHandler extends StoHandlerAdapter {

    private RepairRequestService repairRequestService;
    private RepairRequestWebConverter requestWebConverter;

    @Autowired
    public FindRepairRequestByUsernameAndCarRemarkHandler(RepairRequestService repairRequestService, RepairRequestWebConverter requestWebConverter) {
        this.repairRequestService = repairRequestService;
        this.requestWebConverter = requestWebConverter;
    }

    @Override
    public void handleDoGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String username = request.getParameter("username");
        String carRemark = request.getParameter("carRemark");
        RepairRequest repairRequestByUsernameAndCarRemark = repairRequestService.findRepairRequestByUsernameAndCarRemark(username, carRemark);
        RepairRequestWebDto repairRequestWebDto = requestWebConverter.convertToDto(repairRequestByUsernameAndCarRemark);

        PrintWriter out = response.getWriter();
        ObjectMapper objectMapper = new ObjectMapper();
        String jsonString = objectMapper.writeValueAsString(repairRequestWebDto);
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        out.print(jsonString);
        out.flush();

    }

    @Override
    public String getHandledURI() {
        return "/api/repairRequests/byUsernameAndCarRemark";
    }
}