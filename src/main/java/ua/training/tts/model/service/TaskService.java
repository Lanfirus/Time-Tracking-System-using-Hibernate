package ua.training.tts.model.service;

import ua.training.tts.constant.ExceptionMessages;
import ua.training.tts.constant.General;
import ua.training.tts.constant.ReqSesParameters;
import ua.training.tts.constant.model.dao.TableParameters;
import ua.training.tts.model.dao.TaskDao;
import ua.training.tts.model.dao.factory.DaoFactory;
import ua.training.tts.model.entity.Task;
import ua.training.tts.model.exception.BadTaskDataException;
import ua.training.tts.model.util.builder.TaskBuilder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.*;
import java.util.regex.Pattern;

public class TaskService {

    private ResourceBundle regexpBundle;
    private TaskDao dao = DaoFactory.getInstance().createTaskDao();

    /**
     * Builds Task entity from the data passed within user's http servlet request from admin's task edit page.
     * So all fields used there should be present in this entity.
     * @param request       User's request from his browser
     * @return              Task entity built from user's data
     */
    public Task buildNewTaskForUpdateByAdmin(HttpServletRequest request){
        Task task = new TaskBuilder().setId(Integer.parseInt(request.getParameter(TableParameters.TASK_ID)))
                                     .setProjectId(Integer.parseInt(request.getParameter(TableParameters.TASK_PROJECT_ID)))
                                     .setEmployeeId(Integer.parseInt(request.getParameter(TableParameters.TASK_EMPLOYEE_ID)))
                                     .setName(request.getParameter(TableParameters.TASK_NAME))
                                     .setStatus(request.getParameter(TableParameters.TASK_STATUS))
                                     .setDeadline(request.getParameter(TableParameters.TASK_DEADLINE))
                                     .setSpentTime(Integer.parseInt(request.getParameter(TableParameters.TASK_SPENT_TIME)))
                                     .setApprovalState(request.getParameter(TableParameters.TASK_APPROVAL_STATE))
                                     .buildTask();
        return task;
    }

    /**
     * Builds Task entity from the data passed within user's http servlet request from employee's task edit page.
     * So all fields used there should be present in this entity.
     * @param request       User's request from his browser
     * @return              Task entity built from user's data
     */
    public Task buildTaskForUpdateByEmployee(HttpServletRequest request){
        Task task = new TaskBuilder().setId(Integer.parseInt(request.getParameter(TableParameters.TASK_ID)))
                                     .setStatus(request.getParameter(TableParameters.TASK_STATUS))
                                     .setSpentTime(Integer.parseInt(request.getParameter(TableParameters.TASK_SPENT_TIME)))
                                     .setApprovalState(Task.ApprovalState.NEW_REQUEST.name())
                                     .buildTaskForUpdate();
        return task;
    }

    /**
     * Builds Task entity from the data passed within user's http servlet request from admin's task create page.
     * So all fields used there should be present in this entity.
     * As it's a new task, there is no Id associated with it.
     * @param request       User's request from his browser
     * @return              Task entity built from user's data
     */
    public Task buildNewTaskByAdmin(HttpServletRequest request){
        Task task = new TaskBuilder().setProjectId(Integer.parseInt(request.getParameter(TableParameters.TASK_PROJECT_ID)))
                                     .setEmployeeId(Integer.parseInt(request.getParameter(TableParameters.TASK_EMPLOYEE_ID)))
                                     .setName(request.getParameter(TableParameters.TASK_NAME))
                                     .setStatus(request.getParameter(TableParameters.TASK_STATUS))
                                     .setDeadline(request.getParameter(TableParameters.TASK_DEADLINE))
                                     .setSpentTime(Integer.parseInt(request.getParameter(TableParameters.TASK_SPENT_TIME)))
                                     .setApprovalState(request.getParameter(TableParameters.TASK_APPROVAL_STATE))
                                     .buildTask();
            return task;
    }

    /**
     * Builds Task entity from the data passed within user's http servlet request from employee's task create page.
     * So all fields used there should be present in this entity.
     * @param request       User's request from his browser
     * @return              Task entity built from user's data
     */
    public Task buildNewTaskByEmployee(HttpServletRequest request, Integer id){
        Task task = new TaskBuilder().setProjectId(Integer.parseInt(request.getParameter(TableParameters.TASK_PROJECT_ID)))
                                     .setEmployeeId(id)
                                     .setName(request.getParameter(TableParameters.TASK_NAME))
                                     .setStatus(Task.Status.ASSIGNED.name().toLowerCase())
                                     .setDeadline(request.getParameter(TableParameters.TASK_DEADLINE))
                                     .setSpentTime(Integer.parseInt(request.getParameter(TableParameters.TASK_SPENT_TIME)))
                                     .setApprovalState(Task.ApprovalState.NEW_REQUEST.name().toLowerCase())
                                     .buildTask();
        return task;
    }

    /**
     * Checks validity of user's data then tries to put data into database
     * @param task      Task entity built from user's data
     * @param request       User's request from his browser
     */
    public void tryToPutTaskDataIntoDB(Task task, HttpServletRequest request){
        if (isDataCorrect(task, request)) {
                sendReadyTaskDataToDB(task);
        }
        else {
            throw new RuntimeException(ExceptionMessages.BAD_NEW_TASK_DATA);
        }
    }

    /**
     * Checks validity of admin update data then tries to put data into database
     * @param task      Task entity built from admin update data
     * @param request       User's request from his browser
     */
    public void tryToPutUpdatedByAdminTaskDataIntoDB(Task task, HttpServletRequest request){
        if (isDataCorrect(task, request)) {
            sendReadyUpdatedByAdminDataToDB(task);
        }
        else {
            throw new RuntimeException(ExceptionMessages.BAD_UPDATE_TASK_DATA);
        }
    }

    /**
     * Checks validity of employee update data then tries to put data into database
     * @param task      Task entity built from employee update data
     * @param request       User's request from his browser
     */
    public void tryToPutUpdatedByEmployeeTaskDataIntoDB(Task task, HttpServletRequest request)
                throws BadTaskDataException {
        if (isDataCorrect(task, request)) {
                sendReadyUpdatedByEmployeeDataToDB(task);
        }
        else {
            throw new BadTaskDataException();
        }
    }

    /**
     * Matches user's data with regexps.
     * @param task      Task entity built from user's data
     * @param request       User's request from his browser
     * @return      True if data is valid, false otherwise
     */
    private boolean isDataCorrect(Task task, HttpServletRequest request) {
        bundleInitialization(request);

        boolean check = (task.getId() == null || matchInputWithRegexp(String.valueOf(task.getId()),
                regexpBundle.getString(TableParameters.TASK_ID)));
        check &= (task.getProjectId() == null ||  matchInputWithRegexp(String.valueOf(task.getProjectId()),
                regexpBundle.getString(TableParameters.TASK_PROJECT_ID)));
        check &= (task.getEmployeeId() == null || matchInputWithRegexp(String.valueOf(task.getEmployeeId()),
                regexpBundle.getString(TableParameters.TASK_EMPLOYEE_ID)));
        check &= (task.getName() == null || matchInputWithRegexp(task.getName(),
                regexpBundle.getString(TableParameters.TASK_NAME)));
        check &= matchInputWithRegexp(task.getStatus().name(), regexpBundle.getString(TableParameters.TASK_STATUS));
        check &= (task.getDeadline() == null || matchInputWithRegexp(String.valueOf(task.getDeadline()),
                regexpBundle.getString(TableParameters.TASK_DEADLINE)));
        check &= (task.getSpentTime() == null || matchInputWithRegexp(String.valueOf(task.getSpentTime()),
                regexpBundle.getString(TableParameters.TASK_SPENT_TIME)));
        check &= matchInputWithRegexp(task.getApprovalState().name(),
                regexpBundle.getString(TableParameters.TASK_APPROVAL_STATE));
        return check;
    }

    /**
     * Initializes Resource Bundles with respective locale
     *
     * @param request       User's request from his browser
     */
    private void bundleInitialization(HttpServletRequest request) {
        HttpSession session = request.getSession();
        String locale = session.getAttribute(ReqSesParameters.LANGUAGE) == null ?
                General.ENGLISH : (String)session.getAttribute(ReqSesParameters.LANGUAGE);
        regexpBundle = ResourceBundle.getBundle(General.REGEXP_BUNDLE_NAME, new Locale(locale));
    }

    /**
     * Applies regexp to user input data to check its validity
     *
     * @param input     User data
     * @param regexp        Respective regexp to be applied to input data
     * @return      True if data pass regexp, false otherwise
     */
    private boolean matchInputWithRegexp(String input, String regexp) {
        return Pattern.matches(regexp, input);
    }

    /**
     * Sends checked data to be put into DB.
     * @param task Task entity built from user's data
     */
    private void sendReadyTaskDataToDB(Task task) {
        dao.create(task);
    }

    /**
     * Sends checked data to be put into DB.
     * @param task Task entity built from user's data
     */
    private void sendReadyUpdatedByAdminDataToDB(Task task) {
        dao.update(task);
    }

    /**
     * Sends checked data to be put into DB.
     * @param task Task entity built from user's data
     */
    private void sendReadyUpdatedByEmployeeDataToDB(Task task) {
        dao.updateTaskByEmployee(task);
    }

    public Task findById(Integer id){
        return dao.findById(id);
    }

    public List<Task> findAll(){
        return dao.findAll();
    }

    public List<Task> findAllArchived(){
        return dao.findAllArchived();
    }

    public List<Task> findAllById(Integer id){
        return dao.findAllByEmployeeId(id);
    }

    public List<Task> findAllByStatus(String status){
        return dao.findAllByStatus(status);
    }

    public List<Task> findAllByApprovalState(String approvalState){
        return dao.findAllByApprovalState(approvalState);
    }

    public void deleteById(Integer id){
        dao.delete(id);
    }
}