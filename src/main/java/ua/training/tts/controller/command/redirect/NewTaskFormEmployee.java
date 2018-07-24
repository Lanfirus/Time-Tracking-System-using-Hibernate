package ua.training.tts.controller.command.redirect;

import ua.training.tts.constant.Pages;
import ua.training.tts.constant.ReqSesParameters;
import ua.training.tts.controller.command.Command;
import ua.training.tts.controller.util.AccessRights;
import ua.training.tts.controller.util.EmployeeDTO;
import ua.training.tts.model.entity.Employee;
import ua.training.tts.model.entity.full.FullTask;
import ua.training.tts.model.service.FullTaskService;
import ua.training.tts.model.service.TaskService;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * Shows page where new task could be created by Employee
 */
@AccessRights(acceptedRoles = {Employee.AccountRole.EMPLOYEE}, isAvailableForGuests = false)
public class NewTaskFormEmployee implements Command {

    private TaskService service;

    public NewTaskFormEmployee(TaskService service) {
        this.service = service;
    }

    @Override
    public String execute(HttpServletRequest request) {
        EmployeeDTO dto = (EmployeeDTO) request.getSession().getAttribute(ReqSesParameters.DTO);
        FullTaskService fullTaskService = new FullTaskService();
        List<FullTask> myProjects = fullTaskService.findAllProjectsByEmployeeId(dto.getId());
        request.setAttribute(ReqSesParameters.MY_PROJECTS, myProjects);

        return Pages.EMPLOYEE_REQUEST_NEW_TASK_PAGE;
    }
}