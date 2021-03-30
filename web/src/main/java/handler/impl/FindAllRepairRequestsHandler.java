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
public class FindAllRepairRequestsHandler extends StoHandlerAdapter {
    private RepairRequestService repairRequestService;
    private RepairRequestToRepairRequestWebDtoConverter requestWebConverter;
    @Autowired
    public FindAllRepairRequestsHandler(RepairRequestService repairRequestService, RepairRequestToRepairRequestWebDtoConverter requestWebConverter) {
        this.repairRequestService = repairRequestService;
        this.requestWebConverter = requestWebConverter;
    }


    @Override
    public void handleDoGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<RepairRequest> allRepairRequests = repairRequestService.findAllRepairRequests();
        List<RepairRequestWebDto> requestWebDtos = new ArrayList<>();
        for (RepairRequest repairRequest : allRepairRequests) {
            RepairRequestWebDto repairRequestWebDto = requestWebConverter.convertToDto(repairRequest);
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
        return "/api/repairRequests";
    }
}
