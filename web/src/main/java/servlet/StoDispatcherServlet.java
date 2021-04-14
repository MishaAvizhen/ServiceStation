package servlet;

import dto.errorHandling.ErrorDto;
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
                uriToHandlerMap.put(stoHandler.getHandledURI(), stoHandler);
            }
        } catch (ClassNotFoundException e) {
            throw new ServletException("Config class not found.", e);
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        getStoHandlerByRequest(request,response).handleDoGet(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        getStoHandlerByRequest(request,response).handleDoPost(request, response);
    }

    private StoHandler getStoHandlerByRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String requestURI = request.getRequestURI();
        StoHandler stoHandler = uriToHandlerMap.get(requestURI);
        if (stoHandler != null) {
            return stoHandler;
        } else {
            response.sendRedirect(request.getContextPath() + "/");
        }
         throw new UnsupportedOperationException("Not recognized request");
    }
}