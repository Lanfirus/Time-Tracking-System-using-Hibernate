package ua.training.tts.controller.command.redirect;

import ua.training.tts.constant.Pages;
import ua.training.tts.constant.ReqSesParameters;
import ua.training.tts.controller.command.Command;
import ua.training.tts.controller.util.AccessRights;
import ua.training.tts.model.entity.Employee;
import ua.training.tts.model.entity.Project;
import ua.training.tts.model.service.EmployeeService;
import ua.training.tts.model.service.ProjectService;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * Shows page where new task could be created. As task have to be assigned to project and employee, respective
 * lists of both are set into request scope.
 */
@AccessRights(acceptedRoles = {Employee.AccountRole.ADMIN}, isAvailableForGuests = false)
public class NewTaskFormAdmin implements Command {

    @Override
    public String execute(HttpServletRequest request) {
        ProjectService projectService = new ProjectService();
        EmployeeService employeeService = new EmployeeService();
        List<Project> projectData = projectService.findAllActive();
        List<Employee> employeeData = employeeService.findAll();
        request.setAttribute(ReqSesParameters.ACTIVE_PROJECTS, projectData);
        request.setAttribute(ReqSesParameters.EMPLOYEE_LIST, employeeData);

        return Pages.ADMIN_NEW_TASK;
    }
}