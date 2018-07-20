package ua.training.tts.controller.command.redirect;

import ua.training.tts.constant.Pages;
import ua.training.tts.constant.ReqSesParameters;
import ua.training.tts.constant.controller.command.CommandParameters;
import ua.training.tts.controller.command.Command;
import ua.training.tts.controller.util.AccessRights;
import ua.training.tts.model.entity.Employee;
import ua.training.tts.model.entity.Project;
import ua.training.tts.model.entity.Task;
import ua.training.tts.model.service.ProjectService;
import ua.training.tts.model.service.TaskService;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Objects;

/**
 * Shows task edit page filled with all required data
 */
@AccessRights(acceptedRoles = {Employee.AccountRole.ADMIN}, isAvailableForGuests = false)
public class TaskEditFormAdmin implements Command {

    private TaskService service;

    public TaskEditFormAdmin(TaskService service) {
        this.service = service;
    }

    @Override
    public String execute(HttpServletRequest request) {
        if (Objects.nonNull(request.getParameter(ReqSesParameters.TASK_ID))){
            Task task = service.findById(Integer.parseInt(request.getParameter(ReqSesParameters.TASK_ID)));
            request.setAttribute(ReqSesParameters.TASK, task);
            ProjectService projectService = new ProjectService();
            List<Project> activeProjects = projectService.findAllActive();
            request.setAttribute(ReqSesParameters.ACTIVE_PROJECTS, activeProjects);
            return Pages.ADMIN_TASK_EDIT_PAGE;
        }
        else {
            return CommandParameters.REDIRECT + CommandParameters.MAIN;
        }
    }
}