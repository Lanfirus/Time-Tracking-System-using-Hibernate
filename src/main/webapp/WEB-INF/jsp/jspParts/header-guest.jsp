<!DOCTYPE html>

<head>
    <meta charset="utf-8">
    <title><fmt:message key="header.title" /></title>

    <link rel="stylesheet" type="text/css" href="http://localhost:8888/company/resources/css/bootstrap.css" >
    <link rel="stylesheet" type="text/css" href="http://localhost:8888/company/resources/css/base.css" >
    <link rel="stylesheet" type="text/css" href="http://localhost:8888/company/resources/css/header.css" >
    <link rel="stylesheet" type="text/css" href="http://localhost:8888/company/resources/css/footer.css" >

</head>

<body>

    <header class="header">
        <div class="header-container">
            <a href="/company/tts" class="header-logo"></a>


            <br>
            <a href="${pageContext.request.contextPath}/tts/registration_form" class="button button-header"><fmt:message key="header.registration" /></a>
        </div>

        <div style="left:10px; top:20px; position:relative;">
                    <form action="${pageContext.request.contextPath}/app/language" method="post">
                        <select name="language" >
                            <option value="en_US" ${language == 'en_US' ? 'selected' : ''}>
                                    <fmt:message key="header.language.english" />
                            </option>
                            <option value="uk_UA" ${language == 'uk_UA' ? 'selected' : ''}>
                                <fmt:message key="header.language.ukrainian" />
                            </option>
                        </select>
                        <button class="button-select" style="padding:0 10px" type="submit"><fmt:message key="header.language.select" /></button>
                    </form>
        </div>
    </header>
