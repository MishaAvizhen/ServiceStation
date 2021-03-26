package handler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public abstract class StoHandlerAdapter implements StoHandler {

    @Override
    public void handleDoGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
    }

    @Override
    public void handleDoPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
    }
}
