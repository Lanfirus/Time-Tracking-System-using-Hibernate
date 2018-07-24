package ua.training.tts.controller.listener;

import ua.training.tts.constant.General;
import ua.training.tts.controller.util.EmployeeDTO;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import javax.servlet.http.HttpSession;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@WebListener
public class ContextListener implements ServletContextListener{

    /**
     * Initializes map to store relationships between users and their sessions to prevent multi login to the
     * system.
     *
     * @param servletContextEvent   ServletContextEvent
     */
    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {
        Map<EmployeeDTO, HttpSession> logins = new ConcurrentHashMap<>();
        servletContextEvent.getServletContext().setAttribute(General.LOGINS, logins);
    }
}
