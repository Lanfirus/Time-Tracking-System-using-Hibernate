package ua.training.tts.controller.command.admin.project;

import ua.training.tts.constant.Pages;
import ua.training.tts.constant.ReqSesParameters;
import ua.training.tts.constant.controller.command.CommandParameters;
import ua.training.tts.controller.command.Command;
import ua.training.tts.controller.util.AccessRights;
import ua.training.tts.model.entity.Employee;
import ua.training.tts.model.entity.Project;
import ua.training.tts.model.service.ProjectService;

import javax.servlet.http.HttpServletRequest;
import java.util.Objects;

@AccessRights(acceptedRoles = {Employee.AccountRole.ADMIN}, isAvailableForGuests = false)
public class ProjectEditFormAdmin implements Command {

    private ProjectService service;


    public ProjectEditFormAdmin(ProjectService service) {
        this.service = service;
    }

    @Override
    public String execute(HttpServletRequest request) {
        if (Objects.nonNull(request.getParameter(ReqSesParameters.PROJECT_ID))) {
            Project project = service.findById(Integer.parseInt(request.getParameter(ReqSesParameters.PROJECT_ID)));
            request.setAttribute(ReqSesParameters.PROJECT, project);
            return Pages.ADMIN_PROJECT_EDIT_PAGE;
        }
        else {
            return CommandParameters.REDIRECT + CommandParameters.MAIN;
        }
    }
}