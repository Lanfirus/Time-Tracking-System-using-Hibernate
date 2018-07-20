package ua.training.tts.controller.command.admin;

import ua.training.tts.constant.ReqSesParameters;
import ua.training.tts.constant.controller.command.CommandParameters;
import ua.training.tts.controller.command.Command;
import ua.training.tts.controller.util.AccessRights;
import ua.training.tts.model.entity.Employee;
import ua.training.tts.model.entity.Project;
import ua.training.tts.model.service.FullTaskService;
import ua.training.tts.model.service.ProjectService;

import javax.servlet.http.HttpServletRequest;
import java.util.Objects;

/**
 * Put project and all related tasks into archive.
 * Checks that project has been cancelled or finished before archiving.
 */
@AccessRights(acceptedRoles = {Employee.AccountRole.ADMIN}, isAvailableForGuests = false)
public class ProjectTaskArchive implements Command {

    private FullTaskService service;

    public ProjectTaskArchive(FullTaskService service) {
        this.service = service;
    }

    /**
     * Only cancenned and finished projects could be put into archive altogether with related tasks, so there is
     * respective check in this method.
     * @param request       User's request from his browser.
     * @return              Page where user should be sent or redirected
     */
    @Override
    public String execute(HttpServletRequest request) {
        if (Objects.nonNull(request.getParameter(ReqSesParameters.PROJECT_ID))){
            Integer id = Integer.parseInt(request.getParameter(ReqSesParameters.PROJECT_ID));
            ProjectService projectService = new ProjectService();
            Project project = projectService.findById(id);
            if (project.getStatus() == Project.Status.CANCELLED
                    || project.getStatus() == Project.Status.FINISHED) {
                service.archiveProject(id);
            }
        }
        return CommandParameters.REDIRECT + CommandParameters.MAIN;
    }
}