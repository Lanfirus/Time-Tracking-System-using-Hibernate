package ua.training.tts.model.util.builder;

import ua.training.tts.model.entity.Task;

import java.time.LocalDate;

/**
 * Builder class for Employee entity
 */
public class TaskBuilder {

    private Integer id;
    private Integer projectId;
    private Integer employeeId;
    private String name;
    private Task.Status status;
    private LocalDate deadline;
    private Integer spentTime;
    private Task.ApprovalState approvalState;

    public TaskBuilder setId(Integer id) {
        this.id = id;
        return this;
    }

    public TaskBuilder setProjectId(Integer projectId) {
        this.projectId = projectId;
        return this;
    }

    public TaskBuilder setEmployeeId(Integer employeeId) {
        this.employeeId = employeeId;
        return this;
    }

    public TaskBuilder setName(String name) {
        this.name = name;
        return this;
    }

    public TaskBuilder setStatus(String status) {
        this.status = Task.Status.valueOf(status.toUpperCase());
        return this;
    }

    public TaskBuilder setDeadline(String deadline) {
        this.deadline = ("".equals(deadline) ) ? null : LocalDate.parse(deadline);
        return this;
    }

    public TaskBuilder setSpentTime(Integer spentTime) {
        this.spentTime = spentTime;
        return this;
    }

    public TaskBuilder setApprovalState(String approved) {
        this.approvalState = Task.ApprovalState.valueOf(approved.toUpperCase());
        return this;
    }

    public Task buildTask(){
        Task task = new Task();
        task.setId(id);
        task.setProjectId(projectId);
        task.setEmployeeId(employeeId);
        task.setName(name);
        task.setStatus(status);
        task.setDeadline(deadline);
        task.setSpentTime(spentTime);
        task.setApprovalState(approvalState);
        return task;
    }

    public Task buildTaskForUpdate(){
        Task task = new Task();
        task.setId(id);
        task.setStatus(status);
        task.setSpentTime(spentTime);
        task.setApprovalState(approvalState);
        return task;
    }
}
