<%@ page isErrorPage="true" %>
<%@ include file="jspParts/general.jsp" %>
<%@ include file="jspParts/language-setup.jsp"%>
<%@ include file="jspParts/header-empty.jsp"%>

<div class="main">
    <h2><fmt:message key="error.message.initialMessage" /></h2>
    <br>
    <i>Error <%= exception %></i>
    <br>
    <fmt:message key="error.message.indexPage" />
    <br>
        <a href="${pageContext.request.contextPath}/tts/main"><fmt:message key="error.link.mainPage" /></a>
</div>

<jsp:include page="jspParts/footer.jsp"/>
