package ua.training.tts.controller.filter;


import ua.training.tts.constant.controller.FilterParameters;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import java.io.IOException;

@WebFilter(FilterParameters.URL_PATTERN_ALL)
public class EncodingFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig){
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        servletResponse.setContentType(FilterParameters.TEXT_HTML);
        servletResponse.setCharacterEncoding(FilterParameters.UTF_8);
        servletRequest.setCharacterEncoding(FilterParameters.UTF_8);
        filterChain.doFilter(servletRequest,servletResponse);
    }

    @Override
    public void destroy() {
    }
}
