package ua.training.tts.model.entity.full;

import ua.training.tts.model.entity.Employee;
import ua.training.tts.model.entity.Project;
import ua.training.tts.model.entity.Task;

import java.time.LocalDate;

public class FullTask {

    private Integer projectId;
    private String projectName;
    private LocalDate projectDeadline;
    private Project.Status projectStatus;

    private Integer taskId;
    private String taskName;
    private LocalDate taskDeadline;
    private Integer taskSpentTime;
    private Task.Status taskStatus;
    private Task.ApprovalState taskApprovalState;

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

    public Integer getProjectId() {
        return projectId;
    }

    public void setProjectId(Integer projectId) {
        this.projectId = projectId;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public LocalDate getProjectDeadline() {
        return projectDeadline;
    }

    public void setProjectDeadline(LocalDate projectDeadline) {
        this.projectDeadline = projectDeadline;
    }

    public Project.Status getProjectStatus() {
        return projectStatus;
    }

    public void setProjectStatus(Project.Status projectStatus) {
        this.projectStatus = projectStatus;
    }

    public Integer getTaskId() {
        return taskId;
    }

    public void setTaskId(Integer taskId) {
        this.taskId = taskId;
    }

    public String getTaskName() {
        return taskName;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    public LocalDate getTaskDeadline() {
        return taskDeadline;
    }

    public void setTaskDeadline(LocalDate taskDeadline) {
        this.taskDeadline = taskDeadline;
    }

    public Integer getTaskSpentTime() {
        return taskSpentTime;
    }

    public void setTaskSpentTime(Integer taskSpentTime) {
        this.taskSpentTime = taskSpentTime;
    }

    public Task.Status getTaskStatus() {
        return taskStatus;
    }

    public void setTaskStatus(Task.Status taskStatus) {
        this.taskStatus = taskStatus;
    }

    public Task.ApprovalState getTaskApprovalState() {
        return taskApprovalState;
    }

    public void setTaskApprovalState(Task.ApprovalState taskApprovalState) {
        this.taskApprovalState = taskApprovalState;
    }

    public Integer getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(Integer employeeId) {
        this.employeeId = employeeId;
    }

    public String getEmployeeLogin() {
        return employeeLogin;
    }

    public void setEmployeeLogin(String employeeLogin) {
        this.employeeLogin = employeeLogin;
    }

    public String getEmployeePassword() {
        return employeePassword;
    }

    public void setEmployeePassword(String employeePassword) {
        this.employeePassword = employeePassword;
    }

    public String getEmployeeName() {
        return employeeName;
    }

    public void setEmployeeName(String employeeName) {
        this.employeeName = employeeName;
    }

    public String getEmployeeSurname() {
        return employeeSurname;
    }

    public void setEmployeeSurname(String employeeSurname) {
        this.employeeSurname = employeeSurname;
    }

    public String getEmployeePatronymic() {
        return employeePatronymic;
    }

    public void setEmployeePatronymic(String employeePatronymic) {
        this.employeePatronymic = employeePatronymic;
    }

    public String getEmployeeEmail() {
        return employeeEmail;
    }

    public void setEmployeeEmail(String employeeEmail) {
        this.employeeEmail = employeeEmail;
    }

    public String getEmployeeMobilePhone() {
        return employeeMobilePhone;
    }

    public void setEmployeeMobilePhone(String employeeMobilePhone) {
        this.employeeMobilePhone = employeeMobilePhone;
    }

    public String getEmployeeComment() {
        return employeeComment;
    }

    public void setEmployeeComment(String employeeComment) {
        this.employeeComment = employeeComment;
    }

    public Employee.AccountRole getEmployeeAccountRole() {
        return employeeAccountRole;
    }

    public void setEmployeeAccountRole(Employee.AccountRole employeeAccountRole) {
        this.employeeAccountRole = employeeAccountRole;
    }
}
