package handler.impl;

import handler.StoHandlerAdapter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class CreateRepairRequestHandler extends StoHandlerAdapter {
    @Override
    public void handleDoPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        super.handleDoPost(request, response);
    }

    @Override
    public String getHandledURI() {
        return "/api/repairRequests/createRequest";
    }
}
