package handler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public interface StoHandler {
    void handleDoGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException;

    String getHandleURI();
}