package ua.training.tts.controller.util;

import org.junit.Assert;
import org.junit.Test;
import ua.training.tts.constant.General;
import ua.training.tts.constant.TestConstants;
import ua.training.tts.model.entity.Employee;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionBindingEvent;
import java.util.Map;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

@SuppressWarnings("unchecked")
public class EmployeeDTOTest extends Assert{

    private String login = TestConstants.LOGIN;
    private String password = TestConstants.PASSWORD;
    private Employee.AccountRole role = Employee.AccountRole.UNKNOWN;
    private Integer id = TestConstants.ID;
    private EmployeeDTO dto = new EmployeeDTO(id, login, password, role);
    private Map<EmployeeDTO, HttpSession> logins = mock(Map.class);
    private HttpSessionBindingEvent event = mock(HttpSessionBindingEvent.class);
    private HttpSession session = mock(HttpSession.class);
    private HttpSession oldSession = mock(HttpSession.class);
    private ServletContext context = mock(ServletContext.class);

    @Test
    public void valueBoundAlreadyLogin(){
        given(event.getSession()).willReturn(session);
        given(session.getServletContext()).willReturn(context);
        given(context.getAttribute(General.LOGINS)).willReturn(logins);
        given(logins.get(any(EmployeeDTO.class))).willReturn(oldSession);
        dto.valueBound(event);
        assertTrue(dto.isAlreadyLoggedIn());
        verify(oldSession, times(1)).invalidate();
    }

    @Test
    public void valueBoundNotYetLogin(){
        given(event.getSession()).willReturn(session);
        given(session.getServletContext()).willReturn(context);
        given(context.getAttribute(General.LOGINS)).willReturn(logins);
        given(logins.get(any(EmployeeDTO.class))).willReturn(null);
        given(logins.put(any(EmployeeDTO.class), eq(session))).willReturn(null);
        dto.valueBound(event);
        assertFalse(dto.isAlreadyLoggedIn());
        verify(oldSession, times(0)).invalidate();
        verify(logins, times(1)).put(any(EmployeeDTO.class), eq(session));
    }

    @Test
    public void valueUnbound(){
        given(event.getSession()).willReturn(session);
        given(session.getServletContext()).willReturn(context);
        given(context.getAttribute(General.LOGINS)).willReturn(logins);
        given(logins.remove(any(EmployeeDTO.class))).willReturn(null);
        dto.valueUnbound(event);
        verify(logins, times(1)).remove(any(EmployeeDTO.class));
    }

    @Test
    public void equalsEqual(){
        Boolean check = dto.equals(new EmployeeDTO(id, login, password, role));
        assertTrue(check);
    }

    @Test
    public void equalsNotEqual(){
        Boolean check = dto.equals(new EmployeeDTO(id, login, login, role));
        assertFalse(check);
    }

    @Test
    public void equalsWithNull(){
        Boolean check = dto.equals(null);
        assertFalse(check);
    }

    @Test
    public void equalsWithNotEmployeeDTO(){
        Boolean check = dto.equals(new String());
        assertFalse(check);
    }

    @Test
    public void hashcodeEqual(){
        Boolean check = (dto.hashCode() == new EmployeeDTO(id, login, password, role).hashCode());
        assertTrue(check);
    }

    @Test
    public void hashcodeNotEqualOnLogin(){
        Boolean check = (dto.hashCode() == new EmployeeDTO(id, password, password, role).hashCode());
        assertFalse(check);
    }

    @Test
    public void hashcodeNotEqualOnPassword(){
        Boolean check = (dto.hashCode() == new EmployeeDTO(id, login, login, role).hashCode());
        assertFalse(check);
    }

    @Test
    public void hashcodeNotEqualOnRole(){
        Boolean check = (dto.hashCode() == new EmployeeDTO(id, login, password, Employee.AccountRole.ADMIN).hashCode());
        assertFalse(check);
    }

    @Test
    public void hashcodeNotEqualOnAlreadyLoggedIn(){
        dto.setAlreadyLoggedIn(true);
        Boolean check = (dto.hashCode() == new EmployeeDTO(id, login, password, role).hashCode());
        assertFalse(check);
    }
}
