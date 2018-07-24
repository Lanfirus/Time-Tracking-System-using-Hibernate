package ua.training.tts.controller.command.redirect;

import ua.training.tts.constant.Pages;
import ua.training.tts.controller.command.Command;
import ua.training.tts.controller.util.AccessRights;
import ua.training.tts.model.entity.Employee;

import javax.servlet.http.HttpServletRequest;

/**
 * Shows page with confirmation of successful registration
 */
@AccessRights(acceptedRoles = {Employee.AccountRole.UNKNOWN}, isAvailableForGuests = true)
public class RegistrationSuccessfulPage implements Command {
    @Override
    public String execute(HttpServletRequest request) {
        return Pages.REGISTRATION_SUCCESSFUL_PAGE;
    }
}