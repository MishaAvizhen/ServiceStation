package handler.impl;

import handler.StoRestHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
@Component
public class IndexHandler extends StoRestHandler {
    @Override
    public void handleDoGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        writeResponseAsJson("Welcome to StoAvizhen", response);
    }

    @Override
    public String getHandledURI() {
        return "/";
    }
}
