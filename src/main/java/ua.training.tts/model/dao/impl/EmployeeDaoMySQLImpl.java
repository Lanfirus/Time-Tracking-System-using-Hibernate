package ua.training.tts.model.dao.impl;

import ua.training.tts.constant.ExceptionMessages;
import ua.training.tts.constant.model.dao.TableParameters;
import ua.training.tts.model.dao.EmployeeDao;
import ua.training.tts.model.dao.connectionpool.ConnectionPool;
import ua.training.tts.model.entity.Employee;
import ua.training.tts.model.util.RequestBuilder;
import ua.training.tts.model.util.builder.EmployeeBuilder;
import ua.training.tts.util.LogMessageHolder;

import java.sql.*;
import java.util.*;

/**
 * MySQL implementation of Employee entity DAO
 */
public class EmployeeDaoMySQLImpl implements EmployeeDao {

    private RequestBuilder builder;
    private String savedStatement;

    public EmployeeDaoMySQLImpl(RequestBuilder builder){
        this.builder = builder;
    }

    @Override
    public void create(Employee employee) {
        builder.clear();
        String request = builder.insertIntoTable(TableParameters.EMPLOYEE_TABLE_NAME)
                                .insertValueNames(getFieldNames())
                                .build();
        try (Connection connection = ConnectionPool.getConnection();
                PreparedStatement statement = connection.prepareStatement(request)) {
            statement.setString(1, employee.getLogin());
            statement.setString(2, employee.getPassword());
            statement.setString(3, employee.getName());
            statement.setString(4, employee.getSurname());
            statement.setString(5, employee.getPatronymic());
            statement.setString(6, employee.getEmail());
            statement.setString(7, employee.getMobilePhone());
            statement.setString(8, employee.getComment());
            savedStatement = statement.toString();
            statement.executeUpdate();
        } catch (SQLException e) {
            log.error(LogMessageHolder.recordInsertionToTableProblem(TableParameters.EMPLOYEE_TABLE_NAME,
                                                                                            savedStatement), e);
            throw new RuntimeException(e.getMessage());
        }
    }

    @Override
    public Employee findById(Integer id) {
        builder.clear();
        String request = builder.selectAllFromTable(TableParameters.EMPLOYEE_TABLE_NAME)
                                .where(TableParameters.EMPLOYEE_ID)
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
            log.error(LogMessageHolder.recordSearchingInTableProblem(TableParameters.EMPLOYEE_TABLE_NAME,
                                                                                            savedStatement), e);
            throw new RuntimeException(ExceptionMessages.SQL_GENERAL_PROBLEM);
        }
    }

    /**
     * Builds Employee entity from data gotten from database
     *
     * @param set       Result set with data from database
     * @return          Employee entity filled with data from database
     * @throws SQLException
     */
    private Employee extractDataFromResultSet(ResultSet set) throws SQLException{
        EmployeeBuilder builder = new EmployeeBuilder();
        return builder.setId(set.getInt(TableParameters.EMPLOYEE_ID))
                      .setLogin(set.getString(TableParameters.EMPLOYEE_LOGIN))
                      .setPassword(set.getString(TableParameters.EMPLOYEE_PASSWORD))
                      .setName(set.getString(TableParameters.EMPLOYEE_NAME))
                      .setSurname(set.getString(TableParameters.EMPLOYEE_SURNAME))
                      .setPatronymic(set.getString(TableParameters.EMPLOYEE_PATRONYMIC))
                      .setEmail(set.getString(TableParameters.EMPLOYEE_EMAIL))
                      .setMobilePhone(set.getString(TableParameters.EMPLOYEE_MOBILE_PHONE))
                      .setComment(set.getString(TableParameters.EMPLOYEE_COMMENT))
                      .setAccountRole(set.getString(TableParameters.EMPLOYEE_ACCOUNT_ROLE))
                      .buildEmployeeFull();
    }

    @Override
    public Employee findByLogin(String login) {
        builder.clear();
        String request = builder.selectAllFromTable(TableParameters.EMPLOYEE_TABLE_NAME)
                                .where(TableParameters.EMPLOYEE_LOGIN)
                                .build();
        try (Connection connection = ConnectionPool.getConnection();
                PreparedStatement statement = connection.prepareStatement(request)) {
            statement.setString(1, login);
            savedStatement = statement.toString();
            ResultSet set = statement.executeQuery();
            set.next();
            return extractDataFromResultSet(set);
        }
        catch (SQLException e) {
            log.error(LogMessageHolder.recordSearchingInTableProblem(TableParameters.EMPLOYEE_TABLE_NAME,
                                                                                        savedStatement), e);
            throw new RuntimeException(ExceptionMessages.SQL_GENERAL_PROBLEM);
        }
    }

    @Override
    public List<Employee> findAll() {
        builder.clear();
        List<Employee> resultList = new ArrayList<>();
        String request = builder.selectAllFromTable(TableParameters.EMPLOYEE_TABLE_NAME)
                                .build();
        try (Connection connection = ConnectionPool.getConnection();
                PreparedStatement statement = connection.prepareStatement(request)){
            savedStatement = statement.toString();
            ResultSet set = statement.executeQuery();
            while (set.next()){
                Employee result = extractDataFromResultSet(set);
                resultList.add(result);
            }
        } catch (SQLException e) {
            log.error(LogMessageHolder.recordSearchingInTableProblem(TableParameters.EMPLOYEE_TABLE_NAME,
                                                                                        savedStatement), e);
            throw new RuntimeException(ExceptionMessages.SQL_GENERAL_PROBLEM);
        }
        return resultList;
    }

    @Override
    public void update(Employee employee) {
        builder.clear();
        String request = builder.update(TableParameters.EMPLOYEE_TABLE_NAME, getFieldNames())
                                .where(TableParameters.EMPLOYEE_ID)
                                .build();
        try (Connection connection = ConnectionPool.getConnection();
                PreparedStatement statement = connection.prepareStatement(request)){
            statement.setString(1, employee.getLogin());
            statement.setString(2, employee.getPassword());
            statement.setString(3, employee.getName());
            statement.setString(4, employee.getSurname());
            statement.setString(5, employee.getPatronymic());
            statement.setString(6, employee.getEmail());
            statement.setString(7, employee.getMobilePhone());
            statement.setString(8, employee.getComment());
            statement.setInt(9, employee.getId());
            savedStatement = statement.toString();
            statement.executeUpdate();
        } catch (SQLException e) {
            log.error(LogMessageHolder.recordUpdatingInTableProblem(TableParameters.EMPLOYEE_TABLE_NAME,
                                                                                            savedStatement), e);
            throw new RuntimeException(e.getMessage());
        }
    }

    @Override
    public void delete(Integer id) {
        builder.clear();
        String request = builder.delete(TableParameters.EMPLOYEE_TABLE_NAME)
                                .where(TableParameters.EMPLOYEE_ID)
                                .build();
        try (Connection connection = ConnectionPool.getConnection();
                PreparedStatement statement = connection.prepareStatement(request)){
            statement.setInt(1, id);
            savedStatement = statement.toString();
            statement.executeUpdate();
        } catch (SQLException e) {
            log.error(LogMessageHolder.recordDeletingInTableProblem(TableParameters.EMPLOYEE_TABLE_NAME,
                                                                                            savedStatement), e);
            throw new RuntimeException(ExceptionMessages.SQL_GENERAL_PROBLEM);
        }
    }

    /**
     * Checks that Employee with provided login and password is exist in database
     * @param login     Provided login
     * @param password  Provided password
     * @return          True if employee with provided login/password exists, false otherwise
     */
    @Override
    public boolean isEntryExist(String login, String password) {
        builder.clear();
        String request = builder.selectAllFromTable(TableParameters.EMPLOYEE_TABLE_NAME)
                                .where(TableParameters.EMPLOYEE_LOGIN)
                                .and(TableParameters.EMPLOYEE_PASSWORD)
                                .build();
        try (Connection connection = ConnectionPool.getConnection();
                PreparedStatement statement = connection.prepareStatement(request)) {
            statement.setString(1, login);
            statement.setString(2, password);
            savedStatement = statement.toString();
            ResultSet set = statement.executeQuery();
            set.next();
            set.getString(TableParameters.EMPLOYEE_ID);
            return true;
            }
        catch (SQLException e) {
            if (e.getMessage().equals(ExceptionMessages.ILLEGAL_OPERATION_ON_EMPTY_RESULT_SET)) {
                return false;
            }
            else {
                log.error(LogMessageHolder.recordSearchingInTableProblem(TableParameters.EMPLOYEE_TABLE_NAME,
                                                                                            savedStatement), e);
                throw new RuntimeException(ExceptionMessages.SQL_GENERAL_PROBLEM);
            }
        }
    }

    /**
     * Sets provided role to Employee with provided Id
     *
     * @param id        Provided Employee Id
     * @param role      Provided account role to be set to Employee
     */
    @Override
    public void setRoleById(Integer id, String role) {
        builder.clear();
        String request = builder.updateOne(TableParameters.EMPLOYEE_TABLE_NAME, TableParameters.EMPLOYEE_ACCOUNT_ROLE)
                                .where(TableParameters.EMPLOYEE_ID)
                                .build();
        try (Connection connection = ConnectionPool.getConnection();
                PreparedStatement statement = connection.prepareStatement(request)){
            statement.setString(1, role);
            statement.setInt(2, id);
            savedStatement = statement.toString();
            statement.executeUpdate();
        } catch (SQLException e) {
            log.error(LogMessageHolder.recordUpdatingInTableProblem(TableParameters.EMPLOYEE_TABLE_NAME,
                                                                                            savedStatement), e);
            throw new RuntimeException(ExceptionMessages.SQL_GENERAL_PROBLEM);
        }
    }

    private List<String> getFieldNames() {
        return Arrays.asList(TableParameters.EMPLOYEE_LOGIN, TableParameters.EMPLOYEE_PASSWORD, TableParameters.EMPLOYEE_NAME,
                TableParameters.EMPLOYEE_SURNAME, TableParameters.EMPLOYEE_PATRONYMIC, TableParameters.EMPLOYEE_EMAIL,
                TableParameters.EMPLOYEE_MOBILE_PHONE, TableParameters.EMPLOYEE_COMMENT);
    }
}