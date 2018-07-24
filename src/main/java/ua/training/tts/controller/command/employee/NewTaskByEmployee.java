package ua.training.tts.controller.command.employee;

import ua.training.tts.constant.ExceptionMessages;
import ua.training.tts.constant.Pages;
import ua.training.tts.constant.ReqSesParameters;
import ua.training.tts.constant.model.dao.TableParameters;
import ua.training.tts.controller.command.Command;
import ua.training.tts.controller.util.AccessRights;
import ua.training.tts.controller.util.EmployeeDTO;
import ua.training.tts.model.entity.Employee;
import ua.training.tts.model.entity.Task;
import ua.training.tts.model.entity.full.FullTask;
import ua.training.tts.model.exception.DataChangeDetectedException;
import ua.training.tts.model.service.FullTaskService;
import ua.training.tts.model.service.ProjectService;
import ua.training.tts.model.service.TaskService;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDate;
import java.util.List;

/**
 * Creates new task request originated by Employee.
 */
@AccessRights(acceptedRoles = {Employee.AccountRole.EMPLOYEE}, isAvailableForGuests = false)
public class NewTaskByEmployee implements Command {

    private TaskService service;

    public NewTaskByEmployee(TaskService service) {
        this.service = service;
    }

    @Override
    public String execute(HttpServletRequest request) {
        EmployeeDTO dto = (EmployeeDTO) request.getSession().getAttribute(ReqSesParameters.DTO);
        Task task = service.buildNewTaskByEmployee(request, dto.getId());
        checkProjectDeadline(task, request);
        try {
            service.tryToPutTaskDataIntoDB(task, request);
            request.setAttribute(ReqSesParameters.TASK_REQUEST_OK, true);
        }
        catch (DataChangeDetectedException e) {
            request.setAttribute(ReqSesParameters.TASK_DATA_HAS_BEEN_CHANGED, true);
        }
        FullTaskService fullTaskService = new FullTaskService();
        List<FullTask> myProjects = fullTaskService.findAllProjectsByEmployeeId(dto.getId());
        request.setAttribute(ReqSesParameters.MY_PROJECTS, myProjects);
        return Pages.EMPLOYEE_REQUEST_NEW_TASK_PAGE;
    }

    /**
     * Searches respective project's deadline and checks that user hasn't put task's deadline after project's one.
     * If it's the case - replaces task's deadline with project one.
     * @param task      Newly created task to be checked for deadline correctness
     * @param request   User's request from his browser
     */
    private void checkProjectDeadline(Task task, HttpServletRequest request){
        ProjectService service = new ProjectService();
        LocalDate projectDeadline;
        try {
            projectDeadline = service.findById(Integer.parseInt(request.getParameter(TableParameters.TASK_PROJECT_ID)))
                    .getDeadline();
        }
        catch (RuntimeException e){
            if (e.getMessage().contains(ExceptionMessages.EMPTY_RESULT_SET)) {
                throw new RuntimeException(ExceptionMessages.PROJECT_IS_ABSCENT);
            }
            else {
                throw new RuntimeException(e.getMessage());
            }
        }
        if (task.getDeadline().isAfter(projectDeadline)) {
            task.setDeadline(projectDeadline);
        }
    }
}