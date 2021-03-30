package handler.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import converters.impl.RepairRequestToRepairRequestWebDtoConverter;
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
import java.util.ArrayList;
import java.util.List;

@Component
public class FindAllActiveRepairRequestsHandler extends StoHandlerAdapter {
    private RepairRequestService repairRequestService;
    private RepairRequestToRepairRequestWebDtoConverter webConverter;

    @Autowired
    public FindAllActiveRepairRequestsHandler(RepairRequestService repairRequestService, RepairRequestToRepairRequestWebDtoConverter webConverter) {
        this.repairRequestService = repairRequestService;
        this.webConverter = webConverter;
    }


    @Override
    public void handleDoGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<RepairRequest> listOfAllActiveRepairRequests = repairRequestService.getListOfAllActiveRepairRequests();
        List<RepairRequestWebDto> requestWebDtos = new ArrayList<>();
        for (RepairRequest activeRepairRequest : listOfAllActiveRepairRequests) {
            RepairRequestWebDto repairRequestWebDto = webConverter.convertToDto(activeRepairRequest);
            requestWebDtos.add(repairRequestWebDto);
        }
        PrintWriter out = response.getWriter();
        ObjectMapper objectMapper = new ObjectMapper();
        String jsonString = objectMapper.writeValueAsString(requestWebDtos);
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        out.print(jsonString);
        out.flush();
    }

    @Override
    public String getHandledURI() {
        return "/api/repairRequests/active";
    }
}
