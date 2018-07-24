package ua.training.tts.controller.command;


import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;

public interface Command {

    Logger log = Logger.getRootLogger();

    String execute(HttpServletRequest request);
}
