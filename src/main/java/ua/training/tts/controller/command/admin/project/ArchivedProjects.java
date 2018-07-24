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
 * Shows all archived projects.
 */
@AccessRights(acceptedRoles = {Employee.AccountRole.ADMIN}, isAvailableForGuests = false)
public class ArchivedProjects implements Command {

    private ProjectService service;

    public ArchivedProjects(ProjectService service) {
        this.service = service;
    }

    @Override
    public String execute(HttpServletRequest request) {
        List<Project> list = service.findAllArchived();
        request.setAttribute(ReqSesParameters.PROJECT_LIST, list);
        return Pages.ADMIN_ARCHIVED_PROJECTS_PAGE;
    }

}