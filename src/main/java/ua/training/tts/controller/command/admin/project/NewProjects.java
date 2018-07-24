package ua.training.tts.controller.command.admin.project;

import ua.training.tts.constant.Pages;
import ua.training.tts.constant.ReqSesParameters;
import ua.training.tts.controller.command.Command;
import ua.training.tts.controller.util.AccessRights;
import ua.training.tts.model.entity.Employee;
import ua.training.tts.model.entity.Project;
import ua.training.tts.model.service.ProjectService;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * Shows all projects with status "new" except for archived ones.
 */
@AccessRights(acceptedRoles = {Employee.AccountRole.ADMIN}, isAvailableForGuests = false)
public class NewProjects implements Command {

    private ProjectService service;

    public NewProjects(ProjectService service) {
        this.service = service;
    }

    @Override
    public String execute(HttpServletRequest request) {
        List<Project> list = service.findAllByStatus(Project.Status.NEW.name());
        request.setAttribute(ReqSesParameters.PROJECT_LIST, list);
        return Pages.ADMIN_NEW_PROJECTS_PAGE;
    }

}