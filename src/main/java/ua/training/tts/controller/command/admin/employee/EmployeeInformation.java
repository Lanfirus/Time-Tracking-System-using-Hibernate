package ua.training.tts.controller.command.admin.employee;

import ua.training.tts.constant.Pages;
import ua.training.tts.constant.ReqSesParameters;
import ua.training.tts.controller.command.Command;
import ua.training.tts.controller.util.AccessRights;
import ua.training.tts.model.entity.Employee;
import ua.training.tts.model.service.EmployeeService;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * Shows information about all employees.
 */
@AccessRights(acceptedRoles = {Employee.AccountRole.ADMIN}, isAvailableForGuests = false)
public class EmployeeInformation implements Command {

    private EmployeeService service;

    public EmployeeInformation(EmployeeService service) {
        this.service = service;
    }

    @Override
    public String execute(HttpServletRequest request) {
        List<Employee> list = service.findAll();
        request.setAttribute(ReqSesParameters.EMPLOYEE_LIST, list);
        return Pages.ADMIN_EMPLOYEE_INFORMATION_PAGE;
    }
}
