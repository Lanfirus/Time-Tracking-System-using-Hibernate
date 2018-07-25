package ua.training.tts.model.dao.impl;

import org.hibernate.Session;
import ua.training.tts.controller.util.HibernateUtil;
import ua.training.tts.model.dao.EmployeeDao;
import ua.training.tts.model.entity.Employee;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;
import java.util.Objects;

/**
 * MySQL implementation of Project entity DAO
 */
public class EmployeeDaoMySQLImpl implements EmployeeDao {

    @Override
    public boolean isEntryExist(String login, String password) {
        Session session = HibernateUtil.getSession();
        session.beginTransaction();
        Employee employee = session.byNaturalId(Employee.class).using("login", login).load();
        session.getTransaction().commit();
        session.close();
        return Objects.nonNull(employee);
    }

    @Override
    public void setRoleById(Integer id, String role) {
        Session session = HibernateUtil.getSession();
        session.beginTransaction();
        session.createQuery("update Employee set accountRole = :role where id = :id")
                .setParameter("role", Employee.AccountRole.valueOf(role.toUpperCase()))
                .setParameter("id", id)
                .executeUpdate();
        session.getTransaction().commit();
        session.close();
    }

    @Override
    public Employee findByLogin(String login) {
        Session session = HibernateUtil.getSession();
        session.beginTransaction();
        Employee employee = session.byNaturalId(Employee.class).using("login", login).load();
        session.getTransaction().commit();
        session.close();
        return employee;
    }


    @Override
    public Employee findById(Integer id) {
        Session session = HibernateUtil.getSession();
        session.beginTransaction();
        Employee employee = session.load(Employee.class, id);
        session.getTransaction().commit();
        session.close();
        return employee;
    }

    @Override
    public List<Employee> findAll() {
        Session session = HibernateUtil.getSession();
        session.beginTransaction();
        CriteriaBuilder builder = session.getCriteriaBuilder();
        CriteriaQuery<Employee> criteria = builder.createQuery(Employee.class);
        Root<Employee> employeeRoot = criteria.from(Employee.class);
        criteria.select(employeeRoot);
        List<Employee> employeeList = session.createQuery(criteria).getResultList();
        session.getTransaction().commit();
        session.close();
        return employeeList;
    }

    @Override
    public void delete(Integer id) {
        Session session = HibernateUtil.getSession();
        session.beginTransaction();
        session.createQuery("delete from Employee where id= :id").setParameter("id", id)
                .executeUpdate();
        session.getTransaction().commit();
        session.close();
    }
}