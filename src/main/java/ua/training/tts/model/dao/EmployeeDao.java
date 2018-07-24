package ua.training.tts.model.dao;

import ua.training.tts.model.entity.Employee;

public interface EmployeeDao extends GeneralDao<Employee, Integer> {

    boolean isEntryExist(String login, String password);

    void setRoleById(Integer id, String role);

    Employee findByLogin(String login);
}
