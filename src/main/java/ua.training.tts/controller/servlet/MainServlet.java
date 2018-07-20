package ua.training.tts.controller.servlet;

import ua.training.tts.constant.ReqSesParameters;
import ua.training.tts.constant.controller.Servlet;
import ua.training.tts.constant.controller.command.CommandParameters;
import ua.training.tts.controller.command.*;
import ua.training.tts.controller.command.admin.*;
import ua.training.tts.controller.command.admin.employee.EmployeeChangeRole;
import ua.training.tts.controller.command.admin.employee.EmployeeDelete;
import ua.training.tts.controller.command.admin.employee.EmployeeInformation;
import ua.training.tts.controller.command.admin.project.*;
import ua.training.tts.controller.command.admin.task.*;
import ua.training.tts.controller.command.employee.*;
import ua.training.tts.controller.command.redirect.NewTaskFormEmployee;
import ua.training.tts.controller.command.profile.Profile;
import ua.training.tts.controller.command.profile.ProfileUpdate;
import ua.training.tts.controller.command.redirect.*;
import ua.training.tts.controller.util.AccessRights;
import ua.training.tts.controller.util.EmployeeDTO;
import ua.training.tts.model.service.EmployeeService;
import ua.training.tts.model.service.FullTaskService;
import ua.training.tts.model.service.ProjectService;
import ua.training.tts.model.service.TaskService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;


/**
 * Executes role of controller class switching user requests to respective command implementation.
 * In case of incorrect request forwards user to the main (index) page.
 */
@WebServlet(Servlet.URL_PATTERN)
public class MainServlet extends HttpServlet {
    private Map<String, Command> commands = new HashMap<>();


    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {
        processRequest(request, response);
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {
        processRequest(request, response);
    }

    /**
     * Processing method for both Get and Post requests.
     * Defines what Command class to use for each particular request based on request's URL.
     * Redirects user to main page in case of requesting non-existing resources.
     *
     * @param request  User's request from his browser.
     * @param response Response to be send to user.
     * @throws ServletException
     * @throws IOException
     */
    private void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        Command command = getCommand(request);
        String page = command.execute(request);
        sendUserToPage(page, request, response);
    }

    /**
     * Defines Command class to use for user request based on request URI.
     * If requested resource is incorrect, redirects user to main page.
     * Also checks user access rights to get to requested resources.
     * @param request       User's request from his browser
     * @return              Command class to use to process user's request
     */
    private Command getCommand(HttpServletRequest request) {
        String path = request.getRequestURI().replaceAll(Servlet.URI_REPLACE_PATTERN, Servlet.REPLACEMENT);
        Command command = commands.get(path);
        return Objects.nonNull(command) && isAccessGranted(command, request) ?
                command : x -> CommandParameters.REDIRECT + Servlet.SERVLET_MAIN;
    }

    /**
     * Does authorization check for requested resource.
     * It's possible for user to not have any account role (any user before login or after logout). Such users
     * considered as guests and method checks whether requested resource is allowed for guests or not.
     * @param command       Command class to be used to process user request
     * @param request       User's request from his browser
     * @return              True if user is allowed to launch requested Command class, othersise - false
     */
    private boolean isAccessGranted(Command command, HttpServletRequest request) {
        Class<?> commandClass = command.getClass();
        AccessRights rights = commandClass.getAnnotation(AccessRights.class);
        EmployeeDTO dto = (EmployeeDTO) request.getSession().getAttribute(ReqSesParameters.DTO);
        if (Objects.nonNull(dto)){
            return Objects.nonNull(rights) && Arrays.asList(rights.acceptedRoles()).contains(dto.getRole());
        }
        else {
            return Objects.nonNull(rights) && rights.isAvailableForGuests();
        }
    }

    /**
     * Defines how and where to send user according to link got from respective Command class.
     * User could be forwarded or redirected to respective page.
     *
     * @param page     Page where user will be send afterwards. Consist of additional word "redirect" in case
     *                 of redirection that is removed in method to get correct page link.
     * @param request  User's request from his browser
     * @param response Response to be send to user.
     * @throws IOException
     * @throws ServletException
     */
    private void sendUserToPage(String page, HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {
        if (page.contains(Servlet.REDIRECT)) {
            response.sendRedirect(page.replace(Servlet.REDIRECT, Servlet.REPLACEMENT));
        } else {
            request.getRequestDispatcher(page).forward(request, response);
        }
    }

    public void init() {
        {
            commands.put(Servlet.MAIN, new MainPage());
            commands.put(Servlet.REGISTRATION_FORM, new RegistrationForm());
            commands.put(Servlet.REGISTRATION, new Registration(new EmployeeService()));
            commands.put(Servlet.REGISTRATION_SUCCESSFUL, new RegistrationSuccessfulPage());
            commands.put(Servlet.LOGIN_FORM, new LoginForm());
            commands.put(Servlet.LOGIN, new Login(new EmployeeService()));
            commands.put(Servlet.LOGOUT, new Logout());
            commands.put(Servlet.PROFILE, new Profile(new EmployeeService()));
            commands.put(Servlet.PROFILE_UPDATE, new ProfileUpdate(new EmployeeService()));
            commands.put(Servlet.ADMIN_EMPLOYEE_INFORMATION, new EmployeeInformation(new EmployeeService()));
            commands.put(Servlet.ADMIN_EMPLOYEE_CHANGE_ROLE, new EmployeeChangeRole(new EmployeeService()));
            commands.put(Servlet.ADMIN_EMPLOYEE_DELETE, new EmployeeDelete(new EmployeeService()));
            commands.put(Servlet.EMPLOYEE_MY_TASKS, new MyTasks(new TaskService()));
            commands.put(Servlet.ADMIN_ALL_TASKS, new AllTasks(new TaskService()));
            commands.put(Servlet.EMPLOYEE_NEW_TASK_FORM, new NewTaskFormEmployee(new TaskService()));
            commands.put(Servlet.EMPLOYEE_REQUEST_NEW_TASK, new NewTaskByEmployee(new TaskService()));
            commands.put(Servlet.EMPLOYEE_MY_PROJECTS, new MyProjects(new FullTaskService()));
            commands.put(Servlet.EMPLOYEE_MY_PROJECTS_SORT, new MyProjectsSort(new FullTaskService()));
            commands.put(Servlet.EMPLOYEE_CONTACTS, new Contacts());
            commands.put(Servlet.ADMIN_ALL_PROJECTS, new AllProjects(new ProjectService()));
            commands.put(Servlet.ADMIN_TASK_EDIT_FORM, new TaskEditFormAdmin(new TaskService()));
            commands.put(Servlet.ADMIN_TASK_EDIT, new TaskEdit(new TaskService()));
            commands.put(Servlet.ADMIN_TASK_DELETE, new TaskDelete(new TaskService()));
            commands.put(Servlet.ADMIN_NEW_TASK_FORM, new NewTaskFormAdmin());
            commands.put(Servlet.ADMIN_NEW_TASK, new NewTaskByAdmin(new TaskService()));
            commands.put(Servlet.ADMIN_NEW_PROJECT, new NewProjectCreation(new ProjectService()));
            commands.put(Servlet.ADMIN_NEW_PROJECT_FORM, new NewProjectFormAdmin());
            commands.put(Servlet.ADMIN_PROJECT_DELETE, new ProjectDelete(new ProjectService()));
            commands.put(Servlet.ADMIN_PROJECT_EDIT, new ProjectEdit(new ProjectService()));
            commands.put(Servlet.ADMIN_PROJECT_EDIT_FORM, new ProjectEditFormAdmin(new ProjectService()));
            commands.put(Servlet.ADMIN_PROJECT_ARCHIVE, new ProjectTaskArchive(new FullTaskService()));
            commands.put(Servlet.ADMIN_ASSIGNED_TASKS, new AssignedTasks(new TaskService()));
            commands.put(Servlet.ADMIN_FINISHED_TASKS, new FinishedTasks(new TaskService()));
            commands.put(Servlet.ADMIN_CANCELLED_TASKS, new CancelledTasks(new TaskService()));
            commands.put(Servlet.ADMIN_NEW_REQUEST_TASKS, new NewRequestTasks(new TaskService()));
            commands.put(Servlet.ADMIN_NOT_APPROVED_TASKS, new NotApprovedTasks(new TaskService()));
            commands.put(Servlet.ADMIN_ASSIGNED_PROJECTS, new AssignedProjects(new ProjectService()));
            commands.put(Servlet.ADMIN_CANCELLED_PROJECTS, new CancelledProjects(new ProjectService()));
            commands.put(Servlet.ADMIN_FINISHED_PROJECTS, new FinishedProjects(new ProjectService()));
            commands.put(Servlet.ADMIN_NEW_PROJECTS, new NewProjects(new ProjectService()));
            commands.put(Servlet.ADMIN_ARCHIVED_PROJECTS, new ArchivedProjects(new ProjectService()));
            commands.put(Servlet.ADMIN_ARCHIVED_TASKS, new ArchivedTasks(new TaskService()));
        }
    }
}