package ua.training.tts.controller.filter;

import org.junit.Assert;
import org.junit.Test;
import ua.training.tts.constant.ReqSesParameters;
import ua.training.tts.constant.TestConstants;
import ua.training.tts.constant.controller.FilterParameters;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

public class LanguageFilterTest extends Assert {

    private HttpServletRequest request = mock(HttpServletRequest.class);
    private HttpServletResponse response = mock(HttpServletResponse.class);
    private LanguageFilter filter = new LanguageFilter();
    private FilterChain chain = mock(FilterChain.class);
    private HttpSession session = mock(HttpSession.class);

    @Test
    public void doFilterLanguageChanged() throws IOException, ServletException{
        given(request.getSession()).willReturn(session);
        given(request.getParameter(ReqSesParameters.LANGUAGE)).willReturn(TestConstants.LANGUAGE);
        given(session.getAttribute(ReqSesParameters.LANGUAGE)).willReturn(TestConstants.ANOTHER_LANGUAGE);
        given(request.getHeader(FilterParameters.REFERER)).willReturn(TestConstants.SOME_REQUESTED_URL);
        doNothing().when(session).setAttribute(ReqSesParameters.LANGUAGE, TestConstants.LANGUAGE);
        doNothing().when(response).sendRedirect(TestConstants.SOME_REQUESTED_URL);
        filter.doFilter(request, response, chain);
        verify(session, times(1)).setAttribute(ReqSesParameters.LANGUAGE, TestConstants.LANGUAGE);
        verify(response, times(1)).sendRedirect(request.getHeader(FilterParameters.REFERER));
    }

    @Test
    public void doFilterLanguageNotChangedRequestParameterIsNull() throws IOException, ServletException{
        given(request.getSession()).willReturn(session);
        given(request.getParameter(ReqSesParameters.LANGUAGE)).willReturn(TestConstants.NULL);
        given(session.getAttribute(ReqSesParameters.LANGUAGE)).willReturn(TestConstants.ANOTHER_LANGUAGE);
        doNothing().when(chain).doFilter(request, response);
        filter.doFilter(request, response, chain);
        verify(chain, times(1)).doFilter(request, response);
    }

    @Test
    public void doFilterLanguageNotChangedRequestAndSessionParametersAreSame() throws IOException, ServletException{
        given(request.getSession()).willReturn(session);
        given(request.getParameter(ReqSesParameters.LANGUAGE)).willReturn(TestConstants.LANGUAGE);
        given(session.getAttribute(ReqSesParameters.LANGUAGE)).willReturn(TestConstants.LANGUAGE);
        doNothing().when(chain).doFilter(request, response);
        filter.doFilter(request, response, chain);
        verify(chain, times(1)).doFilter(request, response);
    }

}
