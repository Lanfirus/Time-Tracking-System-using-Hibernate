package ua.training.tts.model.util.builder;

import ua.training.tts.model.entity.Employee;

/**
 * Builder class for Employee entity
 */
public class EmployeeBuilder {

    private Integer id;
    private String login;
    private String password;
    private String name;
    private String surname;
    private String patronymic;
    private String email;
    private String mobilePhone;
    private String comment;
    private Employee.AccountRole accountRole;

    public EmployeeBuilder setId(Integer id) {
        this.id = id;
        return this;
    }

    public EmployeeBuilder setLogin(String login) {
        this.login = login;
        return this;
    }

    public EmployeeBuilder setPassword(String password) {
        this.password = password;
        return this;
    }

    public EmployeeBuilder setName(String name) {
        this.name = name;
        return this;
    }

    public EmployeeBuilder setSurname(String surname) {
        this.surname = surname;
        return this;
    }

    public EmployeeBuilder setPatronymic(String patronymic) {
        this.patronymic = patronymic;
        return this;
    }

    public EmployeeBuilder setEmail(String email) {
        this.email = email;
        return this;
    }

    public EmployeeBuilder setMobilePhone(String mobilePhone) {
        this.mobilePhone = mobilePhone;
        return this;
    }

    public EmployeeBuilder setComment(String comment) {
        this.comment = comment;
        return this;
    }

    public EmployeeBuilder setAccountRole(String accountRole) {
        this.accountRole = Employee.AccountRole.valueOf(accountRole.toUpperCase());
        return this;
    }

    /**
     * Builds full entity Employee with all available fields.
     * Designed to be used by admins to see Id and AccountRole of employees.
     * @return Employee
     */
    public Employee buildEmployeeFull(){
        Employee employee = new Employee();
        employee.setId(id);
        employee.setLogin(login);
        employee.setPassword(password);
        employee.setName(name);
        employee.setSurname(surname);
        employee.setPatronymic(patronymic);
        employee.setEmail(email);
        employee.setMobilePhone(mobilePhone);
        employee.setComment(comment);
        employee.setAccountRole(accountRole);
        return employee;
    }

    /**
     * Builds reduced entity Employee without Id and AccountRole fields.
     * Designed to be showed to employees in their profiles and also for registration of new user.
     * @return Employee
     */
    public Employee buildEmployee(){
        Employee employee = new Employee();
        employee.setLogin(login);
        employee.setPassword(password);
        employee.setName(name);
        employee.setSurname(surname);
        employee.setPatronymic(patronymic);
        employee.setEmail(email);
        employee.setMobilePhone(mobilePhone);
        employee.setComment(comment);
        return employee;
    }
}
