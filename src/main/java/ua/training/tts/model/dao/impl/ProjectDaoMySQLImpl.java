package ua.training.tts.model.dao.impl;

import org.hibernate.Session;
import ua.training.tts.constant.model.dao.TableParameters;
import ua.training.tts.controller.util.HibernateUtil;
import ua.training.tts.model.dao.ProjectDao;
import ua.training.tts.model.entity.Project;
import ua.training.tts.model.util.RequestBuilder;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.ParameterExpression;
import javax.persistence.criteria.Root;
import java.sql.*;
import java.time.LocalDate;
import java.util.List;

/**
 * MySQL implementation of Project entity DAO
 */
public class ProjectDaoMySQLImpl implements ProjectDao {

    @Override
    public Project findById(Integer id) {
        Session session = HibernateUtil.getSession();
        session.beginTransaction();
        Project project = session.load(Project.class, id);
        session.getTransaction().commit();
        session.close();
        return project;
    }

    /**
     * Builds Project entity from data gotten from database
     *
     * @param set       Result set with data from database
     * @return          Project entity filled with data from database
     * @throws SQLException
     */
    private Project extractDataFromResultSet(ResultSet set) throws SQLException{
        Integer id = set.getInt(TableParameters.PROJECT_ID);
        String name = set.getString(TableParameters.PROJECT_NAME);
        LocalDate deadline = set.getDate(TableParameters.PROJECT_DEADLINE).toLocalDate();
        Project.Status status = Project.Status.valueOf(set.getString(TableParameters.PROJECT_STATUS).toUpperCase());

        return new Project(id, name, deadline, status);
    }

    @Override
    public List<Project> findAll() {
        Session session = HibernateUtil.getSession();
        session.beginTransaction();
        CriteriaBuilder builder = session.getCriteriaBuilder();
        CriteriaQuery<Project> criteria = builder.createQuery(Project.class);
        Root<Project> projectRoot = criteria.from(Project.class);
        criteria.select(projectRoot);
        List<Project> projectList = session.createQuery(criteria).getResultList();
        session.getTransaction().commit();
        session.close();
        return projectList;
    }

    /**
     * Searches all active projects meaning ones with statuses New or Assigned
     *
     * @return      List of active projects
     */
    @Override
    public List<Project> findAllActive() {
        Session session = HibernateUtil.getSession();
        session.beginTransaction();
        CriteriaBuilder builder = session.getCriteriaBuilder();
        CriteriaQuery<Project> criteria = builder.createQuery(Project.class);
        Root<Project> projectRoot = criteria.from(Project.class);
        criteria.select(projectRoot);
        ParameterExpression<Project.Status> parameterExpression = builder.parameter(Project.Status.class);
        criteria.where(projectRoot.get("status").in(Project.Status.NEW, Project.Status.ASSIGNED));
        List<Project> projectList = session.createQuery(criteria).getResultList();
        session.getTransaction().commit();
        session.close();
        return projectList;
    }

    /**
     * Gathers information about all projects put into archive
     *
     * @return      List of archived projects
     */
    @Override
    public List<Project> findAllArchived() {
        Session session = HibernateUtil.getSession();
        session.beginTransaction();
        List<Project> projectList = session.createSQLQuery("select * from project_archive")
                .list();
        System.out.println(projectList);
        session.getTransaction().commit();
        session.close();
        return projectList;
    }

    /**
     * Gathers information about all projects that satisfy provided status
     *
     * @param status        Project status provided by user
     * @return              List of projects that satisfy provided project status
     */
    @Override
    public List<Project> findAllByStatus(String status) {
        Session session = HibernateUtil.getSession();
        session.beginTransaction();
        CriteriaBuilder builder = session.getCriteriaBuilder();
        CriteriaQuery<Project> criteria = builder.createQuery(Project.class);
        Root<Project> projectRoot = criteria.from(Project.class);
        criteria.select(projectRoot);
        criteria.where(projectRoot.get("status").in(Project.Status.valueOf(status.toUpperCase())));
        List<Project> projectList = session.createQuery(criteria).getResultList();
        session.getTransaction().commit();
        session.close();
        return projectList;
    }

    @Override
    public void delete(Integer id) {
        Session session = HibernateUtil.getSession();
        session.beginTransaction();
        session.createQuery("delete from Project where id= :id").setParameter("id", id)
                .executeUpdate();
        session.getTransaction().commit();
        session.close();
    }
}