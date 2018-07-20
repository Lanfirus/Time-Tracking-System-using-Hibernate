package ua.training.tts.controller.filter;

import ua.training.tts.constant.ReqSesParameters;
import ua.training.tts.constant.controller.FilterParameters;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.jsp.jstl.core.Config;
import java.io.IOException;

@WebFilter(FilterParameters.URL_PATTERN_ALL)
public class LanguageFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig){
    }

    /**
     * Monitor parameters of request and session responsible for holding preferred language setting.
     * If request parameter is not null it means that user selected some language on language selector.
     * If language parameters in session and request are different it means that session parameter needs to be
     * changed to one from request to reflect user choice.
     *
     * @param servletRequest        Http servlet request
     * @param servletResponse       Http servlet response
     * @param chain                 Filter chain
     * @throws IOException
     * @throws ServletException
     */
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        HttpSession session = request.getSession();

        if(request.getParameter(ReqSesParameters.LANGUAGE) != null
                && session.getAttribute(ReqSesParameters.LANGUAGE) != request.getParameter(ReqSesParameters.LANGUAGE)) {
            session.setAttribute(ReqSesParameters.LANGUAGE, request.getParameter(ReqSesParameters.LANGUAGE));
            response.sendRedirect(request.getHeader(FilterParameters.REFERER));
            return;
        }
            chain.doFilter(servletRequest, servletResponse);
    }

    @Override
    public void destroy() {
    }
}
