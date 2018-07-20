package ua.training.tts.controller.command.employee;

import ua.training.tts.constant.Pages;
import ua.training.tts.constant.ReqSesParameters;
import ua.training.tts.constant.model.dao.TableParameters;
import ua.training.tts.controller.command.Command;
import ua.training.tts.controller.util.AccessRights;
import ua.training.tts.controller.util.EmployeeDTO;
import ua.training.tts.model.entity.Employee;
import ua.training.tts.model.entity.Task;
import ua.training.tts.model.exception.BadTaskDataException;
import ua.training.tts.model.exception.DataChangeDetectedException;
import ua.training.tts.model.service.TaskService;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Objects;

/**
 * Shows all tasks assigned to specific Employee. Updates selected task if needed.
 */
@AccessRights(acceptedRoles = {Employee.AccountRole.EMPLOYEE}, isAvailableForGuests = false)
public class MyTasks implements Command {

    private TaskService service;

    public MyTasks(TaskService service) {
        this.service = service;
    }

    /**
     * If user has selected task for modification there will be it's Id in parameters. In this case task will be
     * modified and updated list of tasks will be shown to user.
     * @param request       User's request from his browser
     * @return              Page where user should be sent or redirected
     */
    @Override
    public String execute(HttpServletRequest request) {
        if (Objects.nonNull(request.getParameter(TableParameters.TASK_ID))) {
            updateTask(request);
        }

        EmployeeDTO dto = (EmployeeDTO) request.getSession().getAttribute(ReqSesParameters.DTO);
        List<Task> list = service.findAllById(dto.getId());
        request.setAttribute(ReqSesParameters.TASK_LIST, list);
        return Pages.EMPLOYEE_MY_TASKS_PAGE;
    }

    /**
     * Sends task update data to DB.
     * If user put incorrect data program will send BadTaskDataException.
     * If system detects recent changes in this task's data that information updated by user is outdated
     * DataChangeDetectedException will be thrown by program and catched here.
     * @param request
     */
    private void updateTask(HttpServletRequest request){
        Task task = service.buildTaskForUpdateByEmployee(request);
        try{
            service.tryToPutUpdatedByEmployeeTaskDataIntoDB(task, request);
            request.setAttribute(ReqSesParameters.TASK_UPDATE_OK, true);
        }
        catch (BadTaskDataException e){
            request.setAttribute(ReqSesParameters.BAD_TASK_UPDATE_DATA, true);
        }
        catch (DataChangeDetectedException e) {
            request.setAttribute(ReqSesParameters.TASK_DATA_HAS_BEEN_CHANGED, true);
        }
    }
}