package ua.training.tts.controller.command.redirect;

import org.junit.Assert;
import org.junit.Test;
import ua.training.tts.constant.Pages;

import javax.servlet.http.HttpServletRequest;

import static org.mockito.Mockito.mock;


public class RegistrationFormTest extends Assert {

    private RegistrationForm form = new RegistrationForm();
    private HttpServletRequest request = mock(HttpServletRequest.class);

    @Test
    public void excecute() {
        String page = form.execute(request);
        assertEquals(Pages.REGISTRATION_PAGE, page);
    }
}
