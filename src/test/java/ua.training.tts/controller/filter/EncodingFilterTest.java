package ua.training.tts.controller.filter;

import org.junit.Assert;
import org.junit.Test;
import ua.training.tts.constant.controller.FilterParameters;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import java.io.IOException;

import static org.mockito.Mockito.*;

public class EncodingFilterTest extends Assert {

    private ServletRequest request = mock(ServletRequest.class);
    private ServletResponse response = mock(ServletResponse.class);
    private EncodingFilter filter = new EncodingFilter();
    private FilterChain chain = mock(FilterChain.class);

    @Test
    public void doFilter() throws IOException, ServletException{
        doNothing().when(response).setContentType(FilterParameters.TEXT_HTML);
        doNothing().when(response).setCharacterEncoding(FilterParameters.UTF_8);
        doNothing().when(request).setCharacterEncoding(FilterParameters.UTF_8);
        doNothing().when(chain).doFilter(request, response);
        filter.doFilter(request, response, chain);
        verify(response, times(1)).setContentType(FilterParameters.TEXT_HTML);
        verify(response, times(1)).setCharacterEncoding(FilterParameters.UTF_8);
        verify(request, times(1)).setCharacterEncoding(FilterParameters.UTF_8);
        verify(chain, times(1)).doFilter(request, response);
    }



}
