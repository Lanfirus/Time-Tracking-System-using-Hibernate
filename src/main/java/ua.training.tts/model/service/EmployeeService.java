package ua.training.tts.model.service;

import ua.training.tts.constant.ExceptionMessages;
import ua.training.tts.constant.General;
import ua.training.tts.constant.ReqSesParameters;
import ua.training.tts.constant.model.dao.TableParameters;
import ua.training.tts.model.dao.EmployeeDao;
import ua.training.tts.model.dao.factory.DaoFactory;
import ua.training.tts.model.entity.Employee;
import ua.training.tts.model.exception.BadRegistrationDataException;
import ua.training.tts.model.exception.NotUniqueLoginException;
import ua.training.tts.util.PasswordHashing;
import ua.training.tts.model.util.builder.EmployeeBuilder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.*;
import java.util.regex.Pattern;

public class EmployeeService {

    private ResourceBundle regexpBundle;
    private EmployeeDao dao = DaoFactory.getInstance().createEmployeeDao();

    /**
     * Builds Employee entity from the data passed within user's http servlet request
     * @param request       User's request from his browser
     * @return              Employee entity built from user's data
     */
    public Employee buildEmployee(HttpServletRequest request){
        String hashedPassword = PasswordHashing.hashPassword(request.getParameter(TableParameters.EMPLOYEE_PASSWORD));
        Employee employee = new EmployeeBuilder().setLogin(request.getParameter(TableParameters.EMPLOYEE_LOGIN))
                                                 .setPassword(hashedPassword)
                                                 .setName(request.getParameter(TableParameters.EMPLOYEE_NAME))
                                                 .setSurname(request.getParameter(TableParameters.EMPLOYEE_SURNAME))
                                                 .setPatronymic(request.getParameter(TableParameters.EMPLOYEE_PATRONYMIC))
                                                 .setEmail(request.getParameter(TableParameters.EMPLOYEE_EMAIL))
                                                 .setMobilePhone(request.getParameter(TableParameters.EMPLOYEE_MOBILE_PHONE))
                                                 .setComment(request.getParameter(TableParameters.EMPLOYEE_COMMENT))
                                                 .buildEmployee();
            return employee;
    }

    /**
     * Checks validity of user's registration data then tries to put data into database
     * @param employee      Employee entity built from user's registration data
     * @param request       User's request from his browser
     * @throws NotUniqueLoginException      Thrown if database already has such login
     * @throws BadRegistrationDataException Thrown if data validity check is failed
     */
    public void tryToPutRegistrationDataIntoDB(Employee employee, HttpServletRequest request)
            throws NotUniqueLoginException, BadRegistrationDataException {
        if (isDataCorrect(employee, request)) {
            try{
                sendReadyRegistrationDataToDB(employee);
            }
            catch(RuntimeException e){
                if (e.getMessage().contains(ExceptionMessages.UNIQUE)) {
                    throw new NotUniqueLoginException();
                }
                else {
                    throw new RuntimeException(ExceptionMessages.SQL_GENERAL_PROBLEM);
                }
            }
        }
        else {
            throw new BadRegistrationDataException();
        }
    }

    /**
     * Checks validity of user's update data then tries to put data into database
     * @param employee      Employee entity built from user's update data
     * @param request       User's request from his browser
     * @throws NotUniqueLoginException      Thrown if database already has such login
     * @throws BadRegistrationDataException Thrown if data validity check is failed
     */
    public void tryToPutUpdateDataIntoDB(Employee employee, HttpServletRequest request)
            throws NotUniqueLoginException, BadRegistrationDataException {
        if (isDataCorrect(employee, request)) {
            try{
                sendReadyUpdateDataToDB(employee);
            }
            catch(RuntimeException e){
                if (e.getMessage().contains(ExceptionMessages.UNIQUE)) {
                    throw new NotUniqueLoginException();
                }
                else {
                    throw new RuntimeException(ExceptionMessages.SQL_GENERAL_PROBLEM);
                }
            }
        }
        else {
            throw new BadRegistrationDataException();
        }
    }

    /**
     * Matches user's data with regexps.
     * @param employee      Employee entity built from user's data
     * @param request       User's request from his browser
     * @return      True if data is valid, false otherwise
     */
    private boolean isDataCorrect(Employee employee, HttpServletRequest request) {
        bundleInitialization(request);
        boolean check = matchInputWithRegexp(employee.getLogin(), regexpBundle.getString(TableParameters.EMPLOYEE_LOGIN));
        check &= matchInputWithRegexp(employee.getPassword(),
                regexpBundle.getString(TableParameters.EMPLOYEE_PASSWORD));
        check &= matchInputWithRegexp(employee.getName(), regexpBundle.getString(TableParameters.EMPLOYEE_NAME));
        check &= matchInputWithRegexp(employee.getSurname(), regexpBundle.getString(TableParameters.EMPLOYEE_SURNAME));
        check &= matchInputWithRegexp(employee.getPatronymic(),
                regexpBundle.getString(TableParameters.EMPLOYEE_PATRONYMIC));
        check &= matchInputWithRegexp(employee.getEmail(), regexpBundle.getString(TableParameters.EMPLOYEE_EMAIL));
        check &= matchInputWithRegexp(employee.getMobilePhone(),
                regexpBundle.getString(TableParameters.EMPLOYEE_MOBILE_PHONE));
        check &= matchInputWithRegexp(employee.getComment(), regexpBundle.getString(TableParameters.EMPLOYEE_COMMENT));

        return check;
    }

    /**
     * Initializes Resource Bundles with respective locale
     *
     * @param request       User's request from his browser
     */
    private void bundleInitialization(HttpServletRequest request) {
        HttpSession session = request.getSession();
        String locale = session.getAttribute(ReqSesParameters.LANGUAGE) == null ?
                General.ENGLISH : (String)session.getAttribute(ReqSesParameters.LANGUAGE);
        regexpBundle = ResourceBundle.getBundle(General.REGEXP_BUNDLE_NAME, new Locale(locale));
    }

    /**
     * Applies regexp to user input data to check its validity
     *
     * @param input     User data
     * @param regexp        Respective regexp to be applied to input data
     * @return      True if data pass regexp, false otherwise
     */
    private boolean matchInputWithRegexp(String input, String regexp) {
        return Pattern.matches(regexp, input);
    }

    /**
     * Sends checked user data to be put into database
     * @param employee      Employee entity built from user's registration data
     */
    private void sendReadyRegistrationDataToDB(Employee employee) {
        dao.create(employee);
    }

    /**
     * Sends checked  user data to be put into DB.
     * @param employee      Employee entity built from user's update data
     */
    public void sendReadyUpdateDataToDB(Employee employee) {
        dao.update(employee);
    }

    /**
     * Sets data entered by user back to request scope to be used later on on html page
     * @param request       User's request from his browser
     * @param employee      Employee entity built from user data
     */
    public void setEmployeeEnteredDataBackToForm(HttpServletRequest request, Employee employee){
        request.setAttribute(TableParameters.EMPLOYEE_LOGIN, employee.getLogin());
        request.setAttribute(TableParameters.EMPLOYEE_NAME, employee.getName());
        request.setAttribute(TableParameters.EMPLOYEE_SURNAME, employee.getSurname());
        request.setAttribute(TableParameters.EMPLOYEE_PATRONYMIC, employee.getPatronymic());
        request.setAttribute(TableParameters.EMPLOYEE_EMAIL, employee.getEmail());
        request.setAttribute(TableParameters.EMPLOYEE_MOBILE_PHONE, employee.getMobilePhone());
        request.setAttribute(TableParameters.EMPLOYEE_COMMENT, employee.getComment());
    }

    public boolean isEmployeeExist(String login, String password){
        return dao.isEntryExist(login, password);
    }

    public Employee findByLogin(String login){
        return dao.findByLogin(login);
    }

    public Employee findById(Integer id){
        return dao.findById(id);
    }

    public List<Employee> findAll(){
        return dao.findAll();
    }

    public void setRoleById(Integer id, String role){
        dao.setRoleById(id, role);
    }

    public void deleteById(Integer id){
        dao.delete(id);
    }
}