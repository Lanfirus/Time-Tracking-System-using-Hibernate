package ua.training.tts.model.entity;

import javax.persistence.*;

@Entity
public class Employee {

    public enum AccountRole {
        ADMIN, EMPLOYEE, UNKNOWN
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "employee_id")
    private Integer id;
    @Column(name = "employee_login")
    private String login;
    @Column(name = "employee_password")
    private String password;
    @Column(name = "employee_name")
    private String name;
    @Column(name = "employee_surname")
    private String surname;
    @Column(name = "employee_patronymic")
    private String patronymic;
    @Column(name = "employee_email")
    private String email;
    @Column(name = "employee_mobile_phone")
    private String mobilePhone;
    @Column(name = "employee_comment")
    private String comment;
    @Column(name = "employee_account_role", columnDefinition = "enum")
    //@Enumerated(EnumType.STRING)
    private AccountRole accountRole;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getPatronymic() {
        return patronymic;
    }

    public void setPatronymic(String patronymic) {
        this.patronymic = patronymic;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMobilePhone() {
        return mobilePhone;
    }

    public void setMobilePhone(String mobilePhone) {
        this.mobilePhone = mobilePhone;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public AccountRole getAccountRole() {
        return accountRole;
    }

    public void setAccountRole(AccountRole accountRole) {
        this.accountRole = accountRole;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}