package ua.training.tts.controller.command.employee;

import ua.training.tts.constant.Pages;
import ua.training.tts.controller.command.Command;
import ua.training.tts.controller.util.AccessRights;
import ua.training.tts.model.entity.Employee;

import javax.servlet.http.HttpServletRequest;

/**
 * Shows Contacts page.
 */
@AccessRights(acceptedRoles = {Employee.AccountRole.EMPLOYEE}, isAvailableForGuests = false)
public class Contacts implements Command {

    @Override
    public String execute(HttpServletRequest request) {
        return Pages.EMPLOYEE_CONTACTS;
    }
}