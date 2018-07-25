package ua.training.tts.model.entity;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.Proxy;
import ua.training.tts.model.util.TaskApprovalStateEnumConverter;
import ua.training.tts.model.util.TaskStatusEnumConverter;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Proxy(lazy = false)
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
    @Cascade(value = org.hibernate.annotations.CascadeType.DELETE)
    private Integer projectId;
    @Column(name = "employee_id")
    private Integer employeeId;
    /*@ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "project_id")
    private Project project;
    @ManyToOne
    @JoinColumn(name = "employee_id")
    private Employee employee;*/
    @Column(name = "task_name")
    private String name;
    @Column(name = "task_status", columnDefinition = "enum")
    @Convert(converter = TaskStatusEnumConverter.class)
    private Status status;
    @Column(name = "task_deadline")
    private LocalDate deadline;
    @Column(name = "task_spent_time")
    private Integer spentTime;
    @Column(name = "task_approval_state", columnDefinition = "enum")
    @Convert(converter = TaskApprovalStateEnumConverter.class)
    private ApprovalState approvalState;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    @Override
    public String toString() {
        return "Task{" +
                "id=" + id +
                ", projectId=" + projectId +
                ", employeeId=" + employeeId +
                ", name='" + name + '\'' +
                ", status=" + status +
                ", deadline=" + deadline +
                ", spentTime=" + spentTime +
                ", approvalState=" + approvalState +
                '}';
    }
}
