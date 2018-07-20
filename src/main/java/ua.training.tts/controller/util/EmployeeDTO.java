package ua.training.tts.controller.util;

import ua.training.tts.constant.General;
import ua.training.tts.model.entity.Employee;

import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionBindingEvent;
import java.util.Map;
import java.util.Optional;

/**
 * Data transfer object class to implement multi-login management and to ease CRUD operations on entities.
 * On login this DTO is filled with respective employee's data and put into his session.
 * Could be extended later on to implement other functionalities or to improve some code during refactoring.
 */
public class EmployeeDTO implements javax.servlet.http.HttpSessionBindingListener {

    private Integer id;
    private String login;
    private String password;
    private Employee.AccountRole role;
    private boolean alreadyLoggedIn;

    /**
     * Is called every time this DTO is put into session's attributes.
     *
     * @param event     Putting this DTO into session's attribute.
     */
    @Override
    @SuppressWarnings("unchecked")
    public void valueBound(HttpSessionBindingEvent event) {
        Map<EmployeeDTO, HttpSession> logins =
                (Map<EmployeeDTO, HttpSession>) event.getSession().getServletContext().getAttribute(General.LOGINS);
        Optional<HttpSession> oldSession = Optional.ofNullable(logins.get(this));
        if (oldSession.isPresent()) {
            oldSession.get().invalidate();
            alreadyLoggedIn = true;
        }
        logins.put(this, event.getSession());
    }

    /**
     * Is called every time this DTO is removed from session's attributes. This includes session invalidate process.
     *
     * @param event     Removing this DTO from session's attribute.
     */
    @Override
    @SuppressWarnings("unchecked")
    public void valueUnbound(HttpSessionBindingEvent event) {
        Map<EmployeeDTO, HttpSession> logins =
                (Map<EmployeeDTO, HttpSession>) event.getSession().getServletContext().getAttribute(General.LOGINS);
        logins.remove(this);
    }

    /**
     * To increase efficiency of storing these DTOs into HashMaps
     *
     * @param other             Object to be checked for equality with current exemplar of DTO class.
     * @return                  True if both objects are the same, false otherwise.
     */
    @Override
    public boolean equals (Object other) {
        if (this == other) {return true;}
        if (other == null || !(other instanceof EmployeeDTO)) {return false;}
        EmployeeDTO test = (EmployeeDTO) other;
        return (this.login.equals(test.login) && this.password.equals(test.password)
                && this.role.name().equals(test.role.name()) && this.alreadyLoggedIn == test.alreadyLoggedIn);
    }

    /**
     * To increase efficiency of storing these DTOs into HashMaps
     *
     * @return                  Hashcode of DTO object.
     */
    @Override
    public int hashCode(){
        int base = 31;
        int result = base;

        result += base * (login == null ? 0 : login.hashCode());
        result += base * (password == null ? 0 : password.hashCode());
        result += base * (role.name() == null ? 0 : role.hashCode());
        result += base * (alreadyLoggedIn ? 1 : 0);

        return result;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
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

    public Employee.AccountRole getRole() {
        return role;
    }

    public void setRole(Employee.AccountRole role) {
        this.role = role;
    }

    public boolean isAlreadyLoggedIn() {
        return alreadyLoggedIn;
    }

    public void setAlreadyLoggedIn(boolean alreadyLoggedIn) {
        this.alreadyLoggedIn = alreadyLoggedIn;
    }

    public EmployeeDTO(Integer id, String login, String password, Employee.AccountRole role) {
        this.id = id;
        this.login = login;
        this.password = password;
        this.role = role;
    }
}
