package ua.training.tts.controller.command;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import ua.training.tts.constant.Pages;
import ua.training.tts.constant.ReqSesParameters;
import ua.training.tts.constant.controller.command.CommandParameters;
import ua.training.tts.model.entity.Employee;
import ua.training.tts.model.exception.BadRegistrationDataException;
import ua.training.tts.model.exception.NotUniqueLoginException;
import ua.training.tts.model.service.EmployeeService;

import javax.servlet.http.HttpServletRequest;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;


public class RegistrationTest extends Assert{

    private HttpServletRequest request = mock(HttpServletRequest.class);
    private EmployeeService service = mock(EmployeeService.class);
    private Employee employee = mock(Employee.class);
    private Registration registration = new Registration(service);

    @Before
    public void init(){
        given(service.buildEmployee(request)).willReturn(employee);
    }

    @Test
    public void executeCorrectData() throws BadRegistrationDataException, NotUniqueLoginException{
        doNothing().when(service).tryToPutRegistrationDataIntoDB(employee, request);

        String page = registration.execute(request);
        assertEquals(CommandParameters.REDIRECT + CommandParameters.REGISTRATION_SUCCESSFUL, page);
        verify(service, times(1)).buildEmployee(request);
    }

    @Test
    public void executeNotUniqueLogin() throws BadRegistrationDataException, NotUniqueLoginException{
        doThrow(new NotUniqueLoginException()).when(service).tryToPutRegistrationDataIntoDB(employee, request);

        String page = registration.execute(request);
        assertEquals(Pages.REGISTRATION_PAGE, page);
        verify(service, times(1)).buildEmployee(request);
        verify(service, times(1)).setEmployeeEnteredDataBackToForm(request, employee);
        verify(request, times(1)).setAttribute(ReqSesParameters.LOGIN_PROBLEM, true);
    }

    @Test
    public void executeBadRegistrationData() throws BadRegistrationDataException, NotUniqueLoginException{
        doThrow(new BadRegistrationDataException()).when(service).tryToPutRegistrationDataIntoDB(employee, request);

        String page = registration.execute(request);
        assertEquals(Pages.REGISTRATION_PAGE, page);
        verify(service, times(1)).buildEmployee(request);
        verify(service, times(1)).setEmployeeEnteredDataBackToForm(request, employee);
    }
}