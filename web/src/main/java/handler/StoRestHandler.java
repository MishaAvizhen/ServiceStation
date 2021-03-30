package handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import dto.RepairRequestRegistrationWebDto;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@Component
public abstract class StoRestHandler extends StoHandlerAdapter {

    private ObjectMapper objectMapper;

    @Autowired
    public void setObjectMapper(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    protected void writeResponseAsJson(Object responseObject, HttpServletResponse response) throws IOException {
        PrintWriter out = response.getWriter();
        String jsonString = objectMapper.writeValueAsString(responseObject);
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        out.print(jsonString);
        out.flush();
    }

    protected <T> T readRequestEntity(Class<T> requestEntityClass, HttpServletRequest request) throws IOException {
        return objectMapper.readValue(request.getInputStream(), requestEntityClass);
    }
}
