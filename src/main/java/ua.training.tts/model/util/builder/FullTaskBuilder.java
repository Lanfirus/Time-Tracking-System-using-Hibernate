package ua.training.tts.model.util.builder;

import ua.training.tts.model.entity.Employee;
import ua.training.tts.model.entity.Project;
import ua.training.tts.model.entity.Task;
import ua.training.tts.model.entity.full.FullTask;

import java.time.LocalDate;

/**
 * Builder class for FullTask entity
 */
public class FullTaskBuilder {

    private Integer projectId;
    private String projectName;
    private LocalDate projectDeadline;
    private Project.Status projectStatus;

    private Integer taskId;
    private String taskName;
    private LocalDate taskDeadline;
    private Integer taskSpentTime;
    private Task.Status taskStatus;
    private Task.ApprovalState taskState;

    private Integer employeeId;
    private String employeeLogin;
    private String employeePassword;
    private String employeeName;
    private String employeeSurname;
    private String employeePatronymic;
    private String employeeEmail;
    private String employeeMobilePhone;
    private String employeeComment;
    private Employee.AccountRole employeeAccountRole;

    public FullTaskBuilder setProjectId(Integer projectId) {
        this.projectId = projectId;
        return this;
    }

    public FullTaskBuilder setProjectName(String projectName) {
        this.projectName = projectName;
        return this;
    }

    public FullTaskBuilder setProjectDeadline(LocalDate projectDeadline) {
        this.projectDeadline = projectDeadline;
        return this;
    }

    public FullTaskBuilder setProjectStatus(String projectStatus) {
        this.projectStatus = Project.Status.valueOf(projectStatus.toUpperCase());
        return this;
    }

    public FullTaskBuilder setTaskId(Integer taskId) {
        this.taskId = taskId;
        return this;
    }

    public FullTaskBuilder setTaskName(String taskName) {
        this.taskName = taskName;
        return this;
    }

    public FullTaskBuilder setTaskDeadline(LocalDate taskDeadline) {
        this.taskDeadline = taskDeadline;
        return this;
    }

    public FullTaskBuilder setTaskSpentTime(Integer taskSpentTime) {
        this.taskSpentTime = taskSpentTime;
        return this;
    }

    public FullTaskBuilder setTaskStatus(String taskStatus) {
        this.taskStatus = Task.Status.valueOf(taskStatus.toUpperCase());
        return this;
    }

    public FullTaskBuilder setTaskState(String taskState) {
        this.taskState = Task.ApprovalState.valueOf(taskState.toUpperCase());
        return this;
    }

    public FullTaskBuilder setEmployeeId(Integer employeeId) {
        this.employeeId = employeeId;
        return this;
    }

    public FullTaskBuilder setEmployeeLogin(String employeeLogin) {
        this.employeeLogin = employeeLogin;
        return this;
    }

    public FullTaskBuilder setEmployeePassword(String employeePassword) {
        this.employeePassword = employeePassword;
        return this;
    }

    public FullTaskBuilder setEmployeeName(String employeeName) {
        this.employeeName = employeeName;
        return this;
    }

    public FullTaskBuilder setEmployeeSurname(String employeeSurname) {
        this.employeeSurname = employeeSurname;
        return this;
    }

    public FullTaskBuilder setEmployeePatronymic(String employeePatronymic) {
        this.employeePatronymic = employeePatronymic;
        return this;
    }

    public FullTaskBuilder setEmployeeEmail(String employeeEmail) {
        this.employeeEmail = employeeEmail;
        return this;
    }

    public FullTaskBuilder setEmployeeMobilePhone(String employeeMobilePhone) {
        this.employeeMobilePhone = employeeMobilePhone;
        return this;
    }

    public FullTaskBuilder setEmployeeComment(String employeeComment) {
        this.employeeComment = employeeComment;
        return this;
    }

    public FullTaskBuilder setEmployeeAccountRole(String employeeAccountRole) {
        this.employeeAccountRole = Employee.AccountRole.valueOf(employeeAccountRole.toUpperCase());
        return this;
    }

    /**
     * Builds full entity Employee with all available fields.
     * Designed to be used by admins to see Id and AccountRole of employees.
     * @return Employee
     */
    public FullTask buildTaskProject(){
        FullTask fullTask = new FullTask();
        fullTask.setTaskId(taskId);
        fullTask.setTaskName(taskName);
        fullTask.setTaskDeadline(taskDeadline);
        fullTask.setTaskSpentTime(taskSpentTime);
        fullTask.setTaskStatus(taskStatus);
        fullTask.setTaskApprovalState(taskState);
        fullTask.setProjectId(projectId);
        fullTask.setProjectName(projectName);
        fullTask.setProjectDeadline(projectDeadline);
        fullTask.setProjectStatus(projectStatus);
        return fullTask;
    }

    public FullTask buildTaskEmployee(){
        FullTask fullTask = new FullTask();
        fullTask.setTaskId(taskId);
        fullTask.setTaskName(taskName);
        fullTask.setTaskDeadline(taskDeadline);
        fullTask.setTaskSpentTime(taskSpentTime);
        fullTask.setTaskStatus(taskStatus);
        fullTask.setTaskApprovalState(taskState);
        fullTask.setEmployeeId(employeeId);
        fullTask.setEmployeeLogin(employeeLogin);
        fullTask.setEmployeePassword(employeePassword);
        fullTask.setEmployeeName(employeeName);
        fullTask.setEmployeeSurname(employeeSurname);
        fullTask.setEmployeePatronymic(employeePatronymic);
        fullTask.setEmployeeEmail(employeeEmail);
        fullTask.setEmployeeMobilePhone(employeeMobilePhone);
        fullTask.setEmployeeComment(employeeComment);
        fullTask.setEmployeeAccountRole(employeeAccountRole);
        return fullTask;
    }

    public FullTask buildProjectEmployee(){
        FullTask fullTask = new FullTask();
        fullTask.setProjectId(projectId);
        fullTask.setProjectName(projectName);
        fullTask.setProjectDeadline(projectDeadline);
        fullTask.setProjectStatus(projectStatus);
        fullTask.setEmployeeId(employeeId);
        fullTask.setEmployeeLogin(employeeLogin);
        fullTask.setEmployeePassword(employeePassword);
        fullTask.setEmployeeName(employeeName);
        fullTask.setEmployeeSurname(employeeSurname);
        fullTask.setEmployeePatronymic(employeePatronymic);
        fullTask.setEmployeeEmail(employeeEmail);
        fullTask.setEmployeeMobilePhone(employeeMobilePhone);
        fullTask.setEmployeeComment(employeeComment);
        fullTask.setEmployeeAccountRole(employeeAccountRole);
        return fullTask;
    }

    public FullTask buildFullTask(){
        FullTask fullTask = new FullTask();
        fullTask.setTaskId(taskId);
        fullTask.setTaskName(taskName);
        fullTask.setTaskDeadline(taskDeadline);
        fullTask.setTaskSpentTime(taskSpentTime);
        fullTask.setTaskStatus(taskStatus);
        fullTask.setTaskApprovalState(taskState);
        fullTask.setEmployeeId(employeeId);
        fullTask.setEmployeeLogin(employeeLogin);
        fullTask.setEmployeePassword(employeePassword);
        fullTask.setEmployeeName(employeeName);
        fullTask.setEmployeeSurname(employeeSurname);
        fullTask.setEmployeePatronymic(employeePatronymic);
        fullTask.setEmployeeEmail(employeeEmail);
        fullTask.setEmployeeMobilePhone(employeeMobilePhone);
        fullTask.setEmployeeComment(employeeComment);
        fullTask.setEmployeeAccountRole(employeeAccountRole);
        fullTask.setProjectId(projectId);
        fullTask.setProjectName(projectName);
        fullTask.setProjectDeadline(projectDeadline);
        fullTask.setProjectStatus(projectStatus);
        return fullTask;
    }

    public FullTask buildProject(){
        FullTask fullTask = new FullTask();
        fullTask.setProjectId(projectId);
        fullTask.setProjectName(projectName);
        fullTask.setProjectDeadline(projectDeadline);
        fullTask.setProjectStatus(projectStatus);
        return fullTask;
    }
}
