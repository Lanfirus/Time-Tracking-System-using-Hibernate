package ua.training.tts.model.entity;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
public class Task {

    public enum Status {
        ASSIGNED, FINISHED, CANCELLED
    }

    public enum ApprovalState {
        APPROVED, NOT_APPROVED, NEW_REQUEST
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "task_id")
    private Integer id;
    @Column(name = "project_id")
    private Integer projectId;
    @Column(name = "employee_id")
    private Integer employeeId;
    @Column(name = "task_name")
    private String name;
    @Column(name = "task_status", columnDefinition = "enum")
    private Status status;
    @Column(name = "task_deadline")
    private LocalDate deadline;
    @Column(name = "task_spent_time")
    private Integer spentTime;
    @Column(name = "task_approval_state", columnDefinition = "enum")
    private ApprovalState approvalState;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getProjectId() {
        return projectId;
    }

    public void setProjectId(Integer projectId) {
        this.projectId = projectId;
    }

    public Integer getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(Integer employeeId) {
        this.employeeId = employeeId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public LocalDate getDeadline() {
        return deadline;
    }

    public void setDeadline(LocalDate deadline) {
        this.deadline = deadline;
    }

    public Integer getSpentTime() {
        return spentTime;
    }

    public void setSpentTime(Integer spentTime) {
        this.spentTime = spentTime;
    }

    public ApprovalState getApprovalState() {
        return approvalState;
    }

    public void setApprovalState(ApprovalState approvalState) {
        this.approvalState = approvalState;
    }
}
