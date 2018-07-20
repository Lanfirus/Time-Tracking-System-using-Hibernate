package ua.training.tts.controller.servlet;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import ua.training.tts.constant.Pages;
import ua.training.tts.constant.TestConstants;
import ua.training.tts.constant.controller.Servlet;
import ua.training.tts.constant.controller.command.CommandParameters;
import ua.training.tts.controller.command.Command;
import ua.training.tts.controller.command.Login;
import ua.training.tts.controller.command.Logout;
import ua.training.tts.controller.command.Registration;
import ua.training.tts.controller.command.redirect.LoginForm;
import ua.training.tts.controller.command.redirect.MainPage;
import ua.training.tts.controller.command.redirect.RegistrationForm;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;
import static org.powermock.api.mockito.PowerMockito.verifyPrivate;
import static org.powermock.api.mockito.PowerMockito.whenNew;

@RunWith(PowerMockRunner.class)
@PrepareForTest(MainServlet.class)
public class MainServletTest extends Assert {

    private MainServlet servlet = PowerMockito.spy(new MainServlet());
    private HttpServletRequest request = mock(HttpServletRequest.class);
    private HttpServletResponse response = mock(HttpServletResponse.class);
    private RequestDispatcher dispatcher = mock(RequestDispatcher.class);
    private Command command = mock(Command.class);

    @Test
    public void processRequest() throws Exception {
        PowerMockito.doReturn(command).when(servlet, TestConstants.MAIN_SERVLET_GET_COMMAND, request);
        given(command.execute(request)).willReturn(TestConstants.PAGE);
        PowerMockito.doNothing().when(servlet, TestConstants.MAIN_SERVLET_SEND_USER_TO_PAGE, TestConstants.PAGE,
                request, response);

        servlet.init();
        servlet.doGet(request, response);
        verifyPrivate(servlet, times(1))
                .invoke(TestConstants.MAIN_SERVLET_GET_COMMAND, request);
        verify(command, times(1)).execute(request);
        verifyPrivate(servlet, times(1))
                .invoke(TestConstants.MAIN_SERVLET_SEND_USER_TO_PAGE, TestConstants.PAGE, request, response);
    }
}