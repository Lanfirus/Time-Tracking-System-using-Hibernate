package ua.training.tts.controller.command.redirect;

import org.junit.Assert;
import org.junit.Test;
import ua.training.tts.constant.Pages;

import javax.servlet.http.HttpServletRequest;

import static org.mockito.Mockito.mock;


public class LoginFormTest extends Assert {

    private LoginForm form = new LoginForm();
    private HttpServletRequest request = mock(HttpServletRequest.class);

    @Test
    public void excecute() {
        String page = form.execute(request);
        assertEquals(Pages.LOGIN_PAGE, page);
    }
}
