package ua.training.tts.controller.util;

import ua.training.tts.constant.ReqSesParameters;

import javax.servlet.jsp.tagext.TagSupport;
import java.time.LocalDate;

/**
 * Custom JSTL tag that put today date into Request Scope
 */
public class TodayTag extends TagSupport{

    public int doStartTag(){

        LocalDate localDate = LocalDate.now();
        pageContext.setAttribute(ReqSesParameters.TODAY, localDate);

        return SKIP_BODY;
    }
}
