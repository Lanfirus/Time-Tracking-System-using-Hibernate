package ua.training.tts.controller.command.admin.task;

import ua.training.tts.constant.controller.command.CommandParameters;
import ua.training.tts.constant.model.dao.TableParameters;
import ua.training.tts.controller.command.Command;
import ua.training.tts.controller.util.AccessRights;
import ua.training.tts.model.entity.Employee;
import ua.training.tts.model.entity.Task;
import ua.training.tts.model.service.ProjectService;
import ua.training.tts.model.service.TaskService;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDate;
import java.util.Objects;

/**
 * Tries to edit/update selected task.
 * Checks task deadline and corrects it if needed.
 */
@AccessRights(acceptedRoles = {Employee.AccountRole.ADMIN}, isAvailableForGuests = false)
public class TaskEdit implements Command {

    private TaskService service;

    public TaskEdit(TaskService service) {
        this.service = service;
    }

    @Override
    public String execute(HttpServletRequest request) {
        Task task = service.buildNewTaskForUpdateByAdmin(request);
        if (Objects.nonNull(task.getDeadline())) {
            checkProjectDeadline(task, request);
            service.tryToPutUpdatedByAdminTaskDataIntoDB(task, request);
        }
        return CommandParameters.REDIRECT + CommandParameters.MAIN;
    }

    /**
     * Searches respective project's deadline and checks that user hasn't put task's deadline after project's one.
     * If it's the case - replaces task's deadline with project one.
     * @param task      Newly created task to be checked for deadline correctness
     * @param request   User's request from his browser
     */
    private void checkProjectDeadline(Task task, HttpServletRequest request){
        ProjectService service = new ProjectService();
        LocalDate projectDeadline = service.findById(Integer.parseInt(request.getParameter(TableParameters.TASK_PROJECT_ID)))
                .getDeadline();
        if (task.getDeadline().isAfter(projectDeadline)) {
            task.setDeadline(projectDeadline);
        }
    }
}