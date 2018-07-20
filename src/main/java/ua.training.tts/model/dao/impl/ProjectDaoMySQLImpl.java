package ua.training.tts.model.dao.impl;

import ua.training.tts.constant.ExceptionMessages;
import ua.training.tts.constant.model.dao.TableParameters;
import ua.training.tts.model.dao.ProjectDao;
import ua.training.tts.model.dao.connectionpool.ConnectionPool;
import ua.training.tts.model.entity.Project;
import ua.training.tts.model.util.RequestBuilder;
import ua.training.tts.util.LogMessageHolder;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * MySQL implementation of Project entity DAO
 */
public class ProjectDaoMySQLImpl implements ProjectDao {

    private RequestBuilder builder;
    private String savedStatement;

    public ProjectDaoMySQLImpl(RequestBuilder builder){
        this.builder = builder;
    }

    @Override
    public void create(Project project) {
        builder.clear();
        String request = builder.insertIntoTable(TableParameters.PROJECT_TABLE_NAME)
                                .insertValueNames(getFieldNames())
                                .build();
        try (Connection connection = ConnectionPool.getConnection();
                PreparedStatement statement = connection.prepareStatement(request)) {
            statement.setString(1, project.getName());
            statement.setDate(2, Date.valueOf(project.getDeadline()));
            statement.setString(3, project.getStatus().name().toLowerCase());
            savedStatement = statement.toString();
            statement.executeUpdate();
        } catch (SQLException e) {
            log.error(LogMessageHolder.recordInsertionToTableProblem(TableParameters.PROJECT_TABLE_NAME,
                                                                                            savedStatement), e);
            throw new RuntimeException(e.getMessage());
        }
    }

    @Override
    public Project findById(Integer id) {
        builder.clear();
        String request = builder.selectAllFromTable(TableParameters.PROJECT_TABLE_NAME)
                                .where(TableParameters.PROJECT_ID)
                                .build();
        try (Connection connection = ConnectionPool.getConnection();
                PreparedStatement statement = connection.prepareStatement(request)) {
            statement.setInt(1,id);
            savedStatement = statement.toString();
            ResultSet set = statement.executeQuery();
            set.next();
            return extractDataFromResultSet(set);
        }
        catch (SQLException e) {
            log.error(LogMessageHolder.recordSearchingInTableProblem(TableParameters.PROJECT_TABLE_NAME,
                                                                                            savedStatement), e);
            if (e.getMessage().contains(ExceptionMessages.EMPTY_RESULT_SET)) {
                throw new RuntimeException(e.getMessage());
            }
            throw new RuntimeException(ExceptionMessages.SQL_GENERAL_PROBLEM);
        }
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
        builder.clear();
        List<Project> resultList = new ArrayList<>();
        String request = builder.selectAllFromTable(TableParameters.PROJECT_TABLE_NAME)
                                .build();
        try (Connection connection = ConnectionPool.getConnection();
                PreparedStatement statement = connection.prepareStatement(request)){
            savedStatement = statement.toString();
            ResultSet set = statement.executeQuery();
            while (set.next()){
                Project result = extractDataFromResultSet(set);
                resultList.add(result);
            }
        } catch (SQLException e) {
            log.error(LogMessageHolder.recordSearchingInTableProblem(TableParameters.PROJECT_TABLE_NAME,
                                                                                        savedStatement), e);
            throw new RuntimeException(ExceptionMessages.SQL_GENERAL_PROBLEM);
        }
        return resultList;
    }

    /**
     * Searches all active projects meaning ones with statuses New or Assigned
     *
     * @return      List of active projects
     */
    @Override
    public List<Project> findAllActive() {
        builder.clear();
        List<Project> resultList = new ArrayList<>();
        String request = builder.selectAllFromTable(TableParameters.PROJECT_TABLE_NAME)
                                .where(TableParameters.PROJECT_STATUS)
                                .or(TableParameters.PROJECT_STATUS)
                                .build();
        try (Connection connection = ConnectionPool.getConnection();
                PreparedStatement statement = connection.prepareStatement(request)){
            statement.setString(1, Project.Status.ASSIGNED.name().toLowerCase());
            statement.setString(2, Project.Status.NEW.name().toLowerCase());
            savedStatement = statement.toString();
            ResultSet set = statement.executeQuery();
            while (set.next()){
                Project result = extractDataFromResultSet(set);
                resultList.add(result);
            }
        } catch (SQLException e) {
            log.error(LogMessageHolder.recordSearchingInTableProblem(TableParameters.PROJECT_TABLE_NAME,
                                                                                        savedStatement), e);
            throw new RuntimeException(ExceptionMessages.SQL_GENERAL_PROBLEM);
        }
        return resultList;
    }

    /**
     * Gathers information about all projects put into archive
     *
     * @return      List of archived projects
     */
    @Override
    public List<Project> findAllArchived() {
        builder.clear();
        List<Project> resultList = new ArrayList<>();
        String request = builder.selectAllFromTable(TableParameters.PROJECT_ARCHIVE_TABLE_NAME)
                                .build();
        try (Connection connection = ConnectionPool.getConnection();
                PreparedStatement statement = connection.prepareStatement(request)){
            savedStatement = statement.toString();
            ResultSet set = statement.executeQuery();
            while (set.next()){
                Project result = extractDataFromResultSet(set);
                resultList.add(result);
            }
        } catch (SQLException e) {
            log.error(LogMessageHolder.recordSearchingInTableProblem(TableParameters.PROJECT_TABLE_NAME,
                                                                                        savedStatement), e);
            throw new RuntimeException(ExceptionMessages.SQL_GENERAL_PROBLEM);
        }
        return resultList;
    }

    /**
     * Gathers information about all projects that satisfy provided status
     *
     * @param status        Project status provided by user
     * @return              List of projects that satisfy provided project status
     */
    @Override
    public List<Project> findAllByStatus(String status) {
        builder.clear();
        List<Project> resultList = new ArrayList<>();
        String request = builder.selectAllFromTable(TableParameters.PROJECT_TABLE_NAME)
                                .where(TableParameters.PROJECT_STATUS)
                                .build();
        try (Connection connection = ConnectionPool.getConnection();
                PreparedStatement statement = connection.prepareStatement(request)){
            statement.setString(1, status);
            savedStatement = statement.toString();
            ResultSet set = statement.executeQuery();
            while (set.next()){
                Project result = extractDataFromResultSet(set);
                resultList.add(result);
            }
        } catch (SQLException e) {
            log.error(LogMessageHolder.recordSearchingInTableProblem(TableParameters.PROJECT_TABLE_NAME,
                                                                                            savedStatement), e);
            throw new RuntimeException(ExceptionMessages.SQL_GENERAL_PROBLEM);
        }
        return resultList;
    }

    @Override
    public void update(Project project) {
        builder.clear();
        String request = builder.update(TableParameters.PROJECT_TABLE_NAME, getFieldNames())
                                .where(TableParameters.PROJECT_ID)
                                .build();
        try (Connection connection = ConnectionPool.getConnection();
                PreparedStatement statement = connection.prepareStatement(request)){
            statement.setString(1, project.getName());
            statement.setDate(2, Date.valueOf(project.getDeadline()));
            statement.setString(3, project.getStatus().name().toLowerCase());
            statement.setInt(4, project.getId());
            savedStatement = statement.toString();
            statement.executeUpdate();
        } catch (SQLException e) {
            log.error(LogMessageHolder.recordUpdatingInTableProblem(TableParameters.PROJECT_TABLE_NAME,
                                                                                            savedStatement), e);
            throw new RuntimeException(ExceptionMessages.SQL_GENERAL_PROBLEM);
        }
    }

    @Override
    public void delete(Integer id) {
        builder.clear();
        String request = builder.delete(TableParameters.PROJECT_TABLE_NAME)
                                .where(TableParameters.PROJECT_ID)
                                .build();
        try (Connection connection = ConnectionPool.getConnection();
                PreparedStatement statement = connection.prepareStatement(request)){
            statement.setInt(1, id);
            savedStatement = statement.toString();
            statement.executeUpdate();
        } catch (SQLException e) {
            log.error(LogMessageHolder.recordDeletingInTableProblem(TableParameters.PROJECT_TABLE_NAME,
                                                                                            savedStatement), e);
            throw new RuntimeException(ExceptionMessages.SQL_GENERAL_PROBLEM);
        }
    }

    private List<String> getFieldNames() {
        return Arrays.asList(TableParameters.PROJECT_NAME, TableParameters.PROJECT_DEADLINE,
                TableParameters.PROJECT_STATUS);
    }
}