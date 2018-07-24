package ua.training.tts.controller.command.admin.task;

import ua.training.tts.constant.Pages;
import ua.training.tts.constant.ReqSesParameters;
import ua.training.tts.controller.command.Command;
import ua.training.tts.controller.util.AccessRights;
import ua.training.tts.model.entity.Employee;
import ua.training.tts.model.entity.Task;
import ua.training.tts.model.service.TaskService;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * Shows all not approved tasks except for archived ones.
 */
@AccessRights(acceptedRoles = {Employee.AccountRole.ADMIN}, isAvailableForGuests = false)
public class NotApprovedTasks implements Command {

    private TaskService service;

    public NotApprovedTasks(TaskService service) {
        this.service = service;
    }

    @Override
    public String execute(HttpServletRequest request) {
        List<Task> list = service.findAllByApprovalState(Task.ApprovalState.NOT_APPROVED.name());
        request.setAttribute(ReqSesParameters.TASK_LIST, list);
        return Pages.ADMIN_NOT_APPROVED_TASKS_PAGE;
    }
}