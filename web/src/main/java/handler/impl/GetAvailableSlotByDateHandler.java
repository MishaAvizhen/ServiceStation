package handler.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import converters.impl.AppointmentSlotDtoToAppointmentSlotWebDtoConverter;
import dto.AppointmentSlotWebDto;
import handler.StoRestHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import service.AppointmentSlotService;
import service.dto.AppointmentSlotDto;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Component
public class GetAvailableSlotByDateHandler extends StoRestHandler {
    private AppointmentSlotService appointmentSlotService;

    @Autowired
    public GetAvailableSlotByDateHandler(AppointmentSlotService appointmentSlotService) {
        this.appointmentSlotService = appointmentSlotService;
    }


    @Override
    public void handleDoGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String targetDate = request.getParameter("targetDate");
        DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Date date = null;
        try {
            date = format.parse(targetDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        List<AppointmentSlotDto> availableAppointmentSlotsByDate = appointmentSlotService.getAvailableAppointmentSlotsByDate(date);
        AppointmentSlotDtoToAppointmentSlotWebDtoConverter appointmentConverter = new AppointmentSlotDtoToAppointmentSlotWebDtoConverter();
        List<AppointmentSlotWebDto> webDtos = new ArrayList<>();
        for (AppointmentSlotDto appointmentSlotDto : availableAppointmentSlotsByDate) {
            webDtos.add(appointmentConverter.convertFromSourceDtoToTargetDto(appointmentSlotDto));
        }
        writeResponseAsJson(webDtos, response);
    }

    @Override
    public String getHandledURI() {
        return "/api/appointments";
    }
}
