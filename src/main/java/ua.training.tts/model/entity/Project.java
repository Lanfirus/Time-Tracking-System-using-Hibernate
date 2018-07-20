package ua.training.tts.model.entity;


import java.time.LocalDate;

public class Project {

    public enum Status {
        NEW, ASSIGNED, FINISHED, CANCELLED
    }

    private Integer id;
    private String name;
    private LocalDate deadline;
    private Status status;

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

    public LocalDate getDeadline() {
        return deadline;
    }

    public void setDeadline(LocalDate deadline) {
        this.deadline = deadline;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public Project(Integer id, String name, LocalDate deadline, Status status) {
        this.id = id;
        this.name = name;
        this.deadline = deadline;
        this.status = status;
    }

    public Project(String name, LocalDate deadline, Status status) {
        this.name = name;
        this.deadline = deadline;
        this.status = status;
    }
}
