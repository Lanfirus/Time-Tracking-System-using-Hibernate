package ua.training.tts.controller.command.redirect;

import ua.training.tts.constant.Pages;
import ua.training.tts.controller.command.Command;
import ua.training.tts.controller.util.AccessRights;
import ua.training.tts.model.entity.Employee;

import javax.servlet.http.HttpServletRequest;

/**
 * Shows page where new project could be created
 */
@AccessRights(acceptedRoles = {Employee.AccountRole.ADMIN}, isAvailableForGuests = false)
public class NewProjectFormAdmin implements Command {

    @Override
    public String execute(HttpServletRequest request) {

        return Pages.ADMIN_NEW_PROJECT;
    }
}