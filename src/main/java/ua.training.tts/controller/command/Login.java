package ua.training.tts.controller.command;

import ua.training.tts.constant.ReqSesParameters;
import ua.training.tts.constant.controller.command.CommandParameters;
import ua.training.tts.controller.exception.DoubleLoginException;
import ua.training.tts.controller.util.AccessRights;
import ua.training.tts.controller.util.EmployeeDTO;
import ua.training.tts.model.entity.Employee;
import ua.training.tts.model.service.EmployeeService;
import ua.training.tts.util.LogMessageHolder;
import ua.training.tts.util.PasswordHashing;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@AccessRights(acceptedRoles = {Employee.AccountRole.UNKNOWN}, isAvailableForGuests = true)
public class Login implements Command {

    private EmployeeDTO dto;
    private EmployeeService service;

    public Login(EmployeeService service){
        this.service = service;
    }

    /**
     * Checks existing session whether it's new or not. If it's later, passes such user further.
     * If session is new, checks database whether such Employee exists. If exists, checks for double login from another
     * browser and/or computer to prevent multi-logins from the same user. In case double login detected user will be
     * notified that his old session has been finished and he could proceed with his newer one.
     * At the very end method sets role to "Unknown" if all previous steps failed.
     * @param request               User's request from his browser
     * @return String               Page where user will be redirected, it's main page of the site
     */
    @Override
    public String execute(HttpServletRequest request) {
        String login = request.getParameter(ReqSesParameters.LOGIN);
        String password = PasswordHashing.hashPassword(request.getParameter(ReqSesParameters.PASSWORD));
        HttpSession session = request.getSession();

        if (session.getAttribute(ReqSesParameters.DTO) != null) {
            dto = (EmployeeDTO) session.getAttribute(ReqSesParameters.DTO);
            log.info(LogMessageHolder.userReenter(login, password, dto.getRole().name()));
        }
        else if (service.isEmployeeExist(login, password)) {
            Employee employee = service.findByLogin(login);
            dto = new EmployeeDTO(employee.getId(), login, password, employee.getAccountRole());
            session.setAttribute(ReqSesParameters.DTO, dto);
            checkDoubleLogin();
        }
        else {
            dto = new EmployeeDTO(CommandParameters.ZERO, CommandParameters.EMPTY, CommandParameters.EMPTY,
                    Employee.AccountRole.UNKNOWN);
            session.setAttribute(ReqSesParameters.DTO, dto);
            log.info(LogMessageHolder.userLogin(login, password, Employee.AccountRole.UNKNOWN.name().toLowerCase()));
        }
        return CommandParameters.REDIRECT + CommandParameters.MAIN;
    }

    /**
     * Uses field of EmployeeDTO object to define whether double login occurred or not.
     * In case of double login, throws exception that will lead to special error page to notify user that his old
     * session has been finished.
     *
     * @throws DoubleLoginException
     */
    private void checkDoubleLogin(){
        if(dto.isAlreadyLoggedIn()) {
            log.warn(LogMessageHolder.userDoubleLogin(dto.getLogin()));
            throw new DoubleLoginException();
        }
        log.info(LogMessageHolder.userLogin(dto.getLogin(), dto.getPassword(), dto.getRole().name()));
    }
}