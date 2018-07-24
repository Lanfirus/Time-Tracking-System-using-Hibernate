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
 * Deletes account of selected employee.
 * If active administrator tries to delete his own account system will not allow this to avoid
 * situation where no administrator left in the system while ordinary employees can't change their account types.
 */
@AccessRights(acceptedRoles = {Employee.AccountRole.ADMIN}, isAvailableForGuests = false)
public class EmployeeDelete implements Command {

    private EmployeeService service;

    public EmployeeDelete(EmployeeService service) {
        this.service = service;
    }

    @Override
    public String execute(HttpServletRequest request) {
        if (Objects.nonNull(request.getParameter(ReqSesParameters.EMPLOYEE_ID))
                && isDeletionAllowed(request)) {
            Integer id = Integer.parseInt(request.getParameter(ReqSesParameters.EMPLOYEE_ID));
            service.deleteById(id);
        }
        return CommandParameters.REDIRECT + CommandParameters.EMPLOYEES;
    }

    /**
     * Checks whether active user tries to delete his own account. As it could be only Admin who has access to
     * this functionality, it's forbidden for him to delete his account as it could lead to situation
     * where there is no Admins left in the whole system and there is no way to set new one except for direct
     * manipulation with database that is highly unacceptable.
     * @param request       User's request from his browser
     * @return              False if user tries to delete his own account, otherwise - true
     */
    private boolean isDeletionAllowed(HttpServletRequest request){
        Integer id = Integer.parseInt(request.getParameter(ReqSesParameters.EMPLOYEE_ID));
        EmployeeDTO dto = (EmployeeDTO) request.getSession().getAttribute(ReqSesParameters.DTO);
        return !id.equals(dto.getId());
    }
}