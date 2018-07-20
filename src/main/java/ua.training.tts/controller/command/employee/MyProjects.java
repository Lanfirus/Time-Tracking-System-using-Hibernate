package ua.training.tts.controller.command.employee;

import ua.training.tts.constant.Pages;
import ua.training.tts.constant.ReqSesParameters;
import ua.training.tts.constant.controller.command.CommandParameters;
import ua.training.tts.controller.command.Command;
import ua.training.tts.controller.util.AccessRights;
import ua.training.tts.controller.util.EmployeeDTO;
import ua.training.tts.model.entity.Employee;
import ua.training.tts.model.entity.full.FullTask;
import ua.training.tts.model.service.FullTaskService;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * Shows all projects assigned to specific employee.
 */
@AccessRights(acceptedRoles = {Employee.AccountRole.EMPLOYEE}, isAvailableForGuests = false)
public class MyProjects implements Command {

    private FullTaskService service;

    public MyProjects(FullTaskService service) {
        this.service = service;
    }

    @Override
    public String execute(HttpServletRequest request) {
        EmployeeDTO dto = (EmployeeDTO) request.getSession().getAttribute(ReqSesParameters.DTO);
        List<FullTask> list = service.findAllProjectsByEmployeeId(dto.getId());
        list = prepareListForPagination(request, list);
        request.setAttribute(ReqSesParameters.PROJECT_LIST, list);
        return Pages.EMPLOYEE_MY_PROJECTS_PAGE;
    }

    /**
     * Pagination implementation. Creates sublist from original list according to current page user is on.
     * Returns it to be showed for user as well as some parameters to be used on page (like "Number of pages").
     * @param request       User's request from his browser.
     * @param list          Initial list of all Tasks assigned to this Employee.
     * @return              Sublist related to current page user is on.
     */
    private static List<FullTask> prepareListForPagination(HttpServletRequest request, List<FullTask> list){
        int numberOfPages = (int)Math.ceil((double)list.size() / CommandParameters.RECORDS_PER_PAGE);
        String currentPage = request.getParameter(ReqSesParameters.CURRENT_PAGE);

        Integer currentPageNumber;
        if(currentPage == null){
            currentPageNumber = CommandParameters.ONE;
        }
        else {
            currentPageNumber = Integer.parseInt(currentPage);
        }
        Integer lastIndex = CommandParameters.RECORDS_PER_PAGE * currentPageNumber;
        Integer firstIndex = lastIndex - CommandParameters.RECORDS_PER_PAGE;

        if(lastIndex > list.size()){
            lastIndex = list.size();
        }

        request.setAttribute(ReqSesParameters.NUMBER_OF_PAGES, numberOfPages);
        request.setAttribute(ReqSesParameters.FIRST_INDEX, firstIndex);

        return list.subList(firstIndex, lastIndex);
    }
}