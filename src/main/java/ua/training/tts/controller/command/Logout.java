package ua.training.tts.controller.command;

import ua.training.tts.constant.ReqSesParameters;
import ua.training.tts.constant.controller.command.CommandParameters;
import ua.training.tts.controller.util.AccessRights;
import ua.training.tts.controller.util.EmployeeDTO;
import ua.training.tts.model.entity.Employee;
import ua.training.tts.util.LogMessageHolder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@AccessRights(acceptedRoles = {Employee.AccountRole.ADMIN, Employee.AccountRole.EMPLOYEE}, isAvailableForGuests = false)
public class Logout implements Command {

    @Override
    public String execute(HttpServletRequest request) {
        HttpSession session = request.getSession();
        String login = ((EmployeeDTO)session.getAttribute(ReqSesParameters.DTO)).getLogin();
        session.invalidate();
        log.info(LogMessageHolder.userLogout(login));
        return CommandParameters.REDIRECT + CommandParameters.MAIN;
    }
}