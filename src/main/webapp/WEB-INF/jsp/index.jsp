<%@ include file="jspParts/general.jsp"%>
<%@ include file="jspParts/language-setup.jsp"%>
<%@ include file="jspParts/header-guest.jsp"%>

<section class="land-panorama">

    <div class="main-container">
        <h1 class="land-panorama-h1"><fmt:message key="main.text.timeTracking" />
            <br><fmt:message key="main.text.scopeManagement" /></h1>
        <p class="land-panorama-description"><fmt:message key="main.text.continue" /></p>
        <a class="button button-land" href="${pageContext.request.contextPath}/tts/login_form">
            <fmt:message key="main.text.startUsing" /></a>
    </div>
</section>

   <section class="land-body">
          <div class="main-container">
              <h2 class="land-body-h2 land-body-h2-long"><fmt:message key="main.text.additional" /></h2>
          </div>
   </section>

   <c:if test="${not empty requestScope.badLoginPassword}">
           <script>
           alert("<fmt:message key="registration.bad.loginPassword" />");
           </script>
   </c:if>

<jsp:include page="jspParts/footer.jsp"/>
