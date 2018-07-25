package ua.training.tts.model.entity;


import org.hibernate.annotations.Proxy;
import ua.training.tts.model.util.StatusEnumConverter;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Proxy(lazy = false)
public class Project{

    public enum Status {
        NEW, ASSIGNED, FINISHED, CANCELLED
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "project_id")
    private Integer id;
    @Column(name = "project_name")
    private String name;
    @Column(name = "project_deadline")
    private LocalDate deadline;
    @Column(name = "project_status", columnDefinition = "enum", insertable = false)
    @Convert(converter = StatusEnumConverter.class)
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

    public Project() {
    }

    @Override
    public String toString() {
        return "Project{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", deadline=" + deadline +
                ", status=" + status +
                '}';
    }
}