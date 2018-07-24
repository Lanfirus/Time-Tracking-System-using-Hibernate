package ua.training.tts.controller.command.admin.employee;

import ua.training.tts.constant.ReqSesParameters;
import ua.training.tts.constant.controller.Servlet;
import ua.training.tts.constant.controller.command.CommandParameters;
import ua.training.tts.controller.command.Command;
import ua.training.tts.controller.util.AccessRights;
import ua.training.tts.controller.util.EmployeeDTO;
import ua.training.tts.model.entity.Employee;
import ua.training.tts.model.service.EmployeeService;

import javax.servlet.http.HttpServletRequest;
import java.util.Objects;

/**
 * Changes role of selected employee.
 * If active administrator tries to change his own account type to "Employee" system will not allow this to avoid
 * situation where no administrator left in the system while ordinary employees can't change their account types.
 */
@AccessRights(acceptedRoles = {Employee.AccountRole.ADMIN}, isAvailableForGuests = false)
public class EmployeeChangeRole implements Command {

    private EmployeeService service;

    public EmployeeChangeRole(EmployeeService service) {
        this.service = service;
    }

    /**
     * DisplayTag is used in jsp. To get value of particular row in the whole table one should specify unique parameter
     * name to be sent in request scope. Otherwise parameter will have the value from the very last entry of the table.
     * For this purpose unique parameter name is used that is some static value and employee id combined together.
     * @param request       User's request from his browser
     * @return              Page where user should be sent or redirected
     */
    @Override
    public String execute(HttpServletRequest request) {
        if (Objects.nonNull(request.getParameter(ReqSesParameters.EMPLOYEE_ID))
                && isChangeAllowed(request)) {
            Integer id = Integer.parseInt(request.getParameter(ReqSesParameters.EMPLOYEE_ID));
            String role = request.getParameter(ReqSesParameters.ACCOUNT_ROLE + id);
            service.setRoleById(id, role);
        }
        return CommandParameters.REDIRECT + CommandParameters.EMPLOYEES;
    }

    /**
     * Checks whether active user tries to change his own account type. As it could be only Admin who has access to
     * this functionality, it's forbidden for him to change his account type to Employee as it could lead to situation
     * where there is no Admins left in the whole system and there is no way to set new one except for direct
     * manipulation with database that is highly unacceptable.
     * @param request       User's request from his browser
     * @return              False if user tries to change his own account type, otherwise - true
     */
    private boolean isChangeAllowed(HttpServletRequest request){
        Integer id = Integer.parseInt(request.getParameter(ReqSesParameters.EMPLOYEE_ID));
        EmployeeDTO dto = (EmployeeDTO) request.getSession().getAttribute(ReqSesParameters.DTO);
        return !id.equals(dto.getId());
    }
}
