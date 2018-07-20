package ua.training.tts.controller.command.admin.task;

import ua.training.tts.constant.ReqSesParameters;
import ua.training.tts.constant.controller.command.CommandParameters;
import ua.training.tts.controller.command.Command;
import ua.training.tts.controller.util.AccessRights;
import ua.training.tts.model.entity.Employee;
import ua.training.tts.model.service.TaskService;

import javax.servlet.http.HttpServletRequest;
import java.util.Objects;

/**
 * Tries to delete selected task.
 */
@AccessRights(acceptedRoles = {Employee.AccountRole.ADMIN}, isAvailableForGuests = false)
public class TaskDelete implements Command {

    private TaskService service;

    public TaskDelete(TaskService service) {
        this.service = service;
    }

    @Override
    public String execute(HttpServletRequest request) {
        if (Objects.nonNull(request.getParameter(ReqSesParameters.TASK_ID))) {
            Integer id = Integer.parseInt(request.getParameter(ReqSesParameters.TASK_ID));
            service.deleteById(id);
        }
        return CommandParameters.REDIRECT + CommandParameters.MAIN;
    }
}