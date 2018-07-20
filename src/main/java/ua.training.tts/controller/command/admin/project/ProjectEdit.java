package ua.training.tts.controller.command.admin.project;

import ua.training.tts.constant.controller.command.CommandParameters;
import ua.training.tts.controller.command.Command;
import ua.training.tts.controller.util.AccessRights;
import ua.training.tts.model.entity.Employee;
import ua.training.tts.model.entity.Project;
import ua.training.tts.model.service.ProjectService;

import javax.servlet.http.HttpServletRequest;

/**
 * Tries to edit/update selected project.
 */
@AccessRights(acceptedRoles = {Employee.AccountRole.ADMIN}, isAvailableForGuests = false)
public class ProjectEdit implements Command {

    private ProjectService service;

    public ProjectEdit(ProjectService service) {
        this.service = service;
    }

    @Override
    public String execute(HttpServletRequest request) {
            Project project = service.buildProject(request);
            service.tryToPutUpdatedProjectDataIntoDB(project, request);
        return CommandParameters.REDIRECT + CommandParameters.MAIN;
    }
}