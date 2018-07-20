package ua.training.tts.controller.command.redirect;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import ua.training.tts.constant.Pages;
import ua.training.tts.constant.ReqSesParameters;
import ua.training.tts.constant.TestConstants;
import ua.training.tts.constant.controller.command.CommandParameters;
import ua.training.tts.controller.util.EmployeeDTO;
import ua.training.tts.model.entity.Employee;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;


public class MainPageTest extends Assert{

    private MainPage page = new MainPage();
    private HttpServletRequest request = mock(HttpServletRequest.class);
    private HttpSession session = mock(HttpSession.class);
    private EmployeeDTO dtoAdmin;
    private EmployeeDTO dtoEmployee;
    private EmployeeDTO dtoUnknown;

    @Before
    public void init() {
        dtoAdmin = new EmployeeDTO(TestConstants.ID, CommandParameters.EMPTY, CommandParameters.EMPTY,
                Employee.AccountRole.ADMIN);
        dtoEmployee = new EmployeeDTO(TestConstants.ID, CommandParameters.EMPTY, CommandParameters.EMPTY,
                Employee.AccountRole.EMPLOYEE);
        dtoUnknown = new EmployeeDTO(TestConstants.ID, CommandParameters.EMPTY, CommandParameters.EMPTY,
                Employee.AccountRole.UNKNOWN);
        when(request.getSession()).thenReturn(session);
        when(request.getSession()).thenReturn(session);
    }

    @Test
    public void executeDtoAdmin() {
        given(session.getAttribute(ReqSesParameters.DTO)).willReturn(dtoAdmin);
        String page = this.page.execute(request);
        assertEquals(Pages.ADMIN_INDEX_PAGE, page);
    }

    @Test
    public void executeDtoEmployee() {
        given(session.getAttribute(ReqSesParameters.DTO)).willReturn(dtoEmployee);
        String page = this.page.execute(request);
        assertEquals(Pages.EMPLOYEE_INDEX_PAGE, page);
    }

    @Test
    public void executeDtoUnknown() {
        given(session.getAttribute(ReqSesParameters.DTO)).willReturn(dtoUnknown);
        String page = this.page.execute(request);
        verify(request, times(1)).setAttribute(ReqSesParameters.BAD_LOGIN_PASSWORD, true);
        verify(request, times(2)).getSession();
        verify(session, times(1)).removeAttribute(ReqSesParameters.DTO);
        assertEquals(Pages.INDEX_PAGE, page);
    }

    @Test
    public void executeDtoNull() {
        given(session.getAttribute(ReqSesParameters.DTO)).willReturn(null);
        String page = this.page.execute(request);
        assertEquals(Pages.INDEX_PAGE, page);
    }
}




