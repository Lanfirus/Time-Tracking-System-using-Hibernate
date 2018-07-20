package ua.training.tts.controller.command.profile;

import ua.training.tts.constant.Pages;
import ua.training.tts.constant.ReqSesParameters;
import ua.training.tts.constant.controller.command.CommandParameters;
import ua.training.tts.constant.model.dao.TableParameters;
import ua.training.tts.controller.command.Command;
import ua.training.tts.controller.util.AccessRights;
import ua.training.tts.controller.util.EmployeeDTO;
import ua.training.tts.model.entity.Employee;
import ua.training.tts.model.exception.BadRegistrationDataException;
import ua.training.tts.model.exception.NotUniqueLoginException;
import ua.training.tts.model.service.EmployeeService;
import ua.training.tts.util.LogMessageHolder;

import javax.servlet.http.HttpServletRequest;
import java.util.Objects;

/**
 * Updates user profile
 */
@AccessRights(acceptedRoles = {Employee.AccountRole.ADMIN, Employee.AccountRole.EMPLOYEE}, isAvailableForGuests = false)
public class ProfileUpdate implements Command {

    private EmployeeService service;
    private EmployeeDTO dto;

    public ProfileUpdate(EmployeeService service) {
        this.service = service;
    }

    /**
     * Process request data to update user profile and then to notify user about result.
     * Employee password is the first field processed during Employee creation, so if request was empty (like on Page
     * update) this field will be null.
     * @param request       User's request from his browser
     * @return              Page where user should be sent or redirected
     */
    @Override
    public String execute(HttpServletRequest request) {
        if (Objects.nonNull(request.getParameter(TableParameters.EMPLOYEE_PASSWORD))) {
            Employee employee = service.buildEmployee(request);
            dto = (EmployeeDTO) request.getSession().getAttribute(ReqSesParameters.DTO);
            employee.setId(dto.getId());
            return tryToAddEmployeeRegistrationDataToDB(employee, request);
        }
        else {
            return CommandParameters.REDIRECT + CommandParameters.PROFILE;
        }
    }

    /**
     * Tries to update user profile. If successful - updates DTO, set respective parameter and returns user to his
     * profile page. If not - set other parameters and also returns user to his profile page.
     * @param employee      Employee entity built earlier
     * @param request       User's request from his browser
     * @return              Page where user should be sent or redirected
     */
    private String tryToAddEmployeeRegistrationDataToDB(Employee employee, HttpServletRequest request){
        try{
            service.tryToPutUpdateDataIntoDB(employee, request);
            checkDTOAndChangeDataIfNeeded(employee);
            request.setAttribute(ReqSesParameters.PROFILE_UPDATE_OK, true);
            log.info(LogMessageHolder.userRegistrationSuccessful(employee.getLogin()));
        }
        catch (NotUniqueLoginException e) {
            request.setAttribute(ReqSesParameters.NOT_UNIQUE_LOGIN, true);
            log.warn(LogMessageHolder.userUsedExistingLogin(employee.getLogin()));

        }
        catch (BadRegistrationDataException e) {
            request.setAttribute(ReqSesParameters.BAD_REGISTRATION_DATA, true);
            log.warn(LogMessageHolder.userUsedNotExistingCredentials(employee.getLogin(), employee.getPassword()));
        }
        service.setEmployeeEnteredDataBackToForm(request, employee);
        return selectPageToReturn(dto.getRole());
    }

    /**
     * Updates DTO to reflect potential changes in user login/password
     * @param employee      Employee entity built earlier
     */
    private void checkDTOAndChangeDataIfNeeded(Employee employee){
        if (!employee.getLogin().equals(dto.getLogin())) {
            dto.setLogin(employee.getLogin());
        }
        if (!employee.getPassword().equals(dto.getPassword())) {
            dto.setPassword(employee.getPassword());
        }
    }

    /**
     * Selects to what profile page user should be forwarded to based on his account role
     * @param role      User's account role
     * @return          Page where user should be sent or redirected
     */
    private String selectPageToReturn(Employee.AccountRole role){
        if (Employee.AccountRole.ADMIN == role) {
            return Pages.PROFILE_ADMIN_PAGE;
        }
        return Pages.PROFILE_EMPLOYEE_PAGE;
    }
}