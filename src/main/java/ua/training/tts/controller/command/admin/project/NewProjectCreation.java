package ua.training.tts.controller.command.admin.project;

import ua.training.tts.constant.controller.command.CommandParameters;
import ua.training.tts.controller.command.Command;
import ua.training.tts.controller.util.AccessRights;
import ua.training.tts.model.entity.Employee;
import ua.training.tts.model.entity.Project;
import ua.training.tts.model.service.ProjectService;

import javax.servlet.http.HttpServletRequest;

/**
 * Tries to put new project into DB according to available user data.
 */
@AccessRights(acceptedRoles = {Employee.AccountRole.ADMIN}, isAvailableForGuests = false)
public class NewProjectCreation implements Command {

    private ProjectService service;

    public NewProjectCreation(ProjectService service) {
        this.service = service;
    }

    @Override
    public String execute(HttpServletRequest request) {
        Project project = service.buildNewProject(request);
        service.tryToPutProjectDataIntoDB(project, request);
        return CommandParameters.REDIRECT + CommandParameters.ADMIN_ALL_PROJECTS;
    }
}