package servlet;

import handler.StoHandler;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class StoDispatcherServlet extends HttpServlet {

    private Map<String, StoHandler> uriToHandlerMap = new HashMap<>();

    private ApplicationContext context;

    @Override
    public void init() throws ServletException {
        try {
            String contextClass = getInitParameter("contextClass");
            Class<?> configClass = Class.forName(contextClass);
            context = new AnnotationConfigApplicationContext(configClass);
            Collection<StoHandler> stoHandlers = context.getBeansOfType(StoHandler.class).values();
            for (StoHandler stoHandler : stoHandlers) {
                uriToHandlerMap.put(stoHandler.getHandleURI(), stoHandler);
            }
        } catch (ClassNotFoundException e) {
            throw new ServletException("Config class not found.", e);
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String requestURI = request.getRequestURI();
        StoHandler stoHandler = uriToHandlerMap.get(requestURI);
        if (stoHandler != null) {
            stoHandler.handleDoGet(request, response);
        } else {
            throw new UnsupportedOperationException("Not recognized request");
        }
    }


}