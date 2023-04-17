<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="ctg" uri="/WEB-INF/tag_tld/custom_tag.tld"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<fmt:setLocale value="${sessionScope.locale}" scope="session"/>
<fmt:setBundle basename="Localization" var="bundle"/>
<fmt:setBundle basename="captcha_credentials" var="captchaBundle"/>

<fmt:message bundle="${bundle}" key="page.login.title" var="title"/>
<fmt:message bundle="${bundle}" key="page.login.h1_sign_in" var="login"/>
<fmt:message bundle="${bundle}" key="page.login.input_label.username" var="username"/>
<fmt:message bundle="${bundle}" key="page.login.input_label.password" var="password"/>
<html lang="en">
<head>
    <title>${title}</title>
    <meta name="viewport" content="width=device-width">
    <link rel="stylesheet" type="text/css" href="css/all.min.css"/>
    <link rel="stylesheet" type="text/css" href="css/style.css"/>
    <link rel="icon" href="https://img.icons8.com/external-flaticons-flat-flat-icons/64/null/external-library-university-flaticons-flat-flat-icons-3.png"/>
    <script src='https://www.google.com/recaptcha/api.js?hl=en'></script>
</head>
<body>
<fmt:bundle basename="Localization" prefix="page.navigation.">
    <nav class="navUl">
        <ul>
            <li><a href="front?command=books">
                <fmt:message key="catalog"/>
            </a></li>
            <ctg:lang locale="${sessionScope.locale}"/>
        </ul>
    </nav>
</fmt:bundle>

<div id="container">
    <div id="box">
        <h1>${login}</h1>
        <form name="loginForm" action="front" method="POST">
            <input name="command" type="hidden" value="login"/>

            <label>${username}</label>
            <div>
                <i class="fa-solid fa-user"></i>
                <label>
                    <input name="login" type="text" placeholder="Enter Username">
                </label>
            </div>

            <label>${password}</label>
            <div>
                <i class="fa-solid fa-lock"></i>
                <label>
                    <input name="password" type="password" placeholder="Enter Password">
                </label>
            </div>
            <div class="g-recaptcha" data-sitekey="<fmt:message bundle="${captchaBundle}" key="Site_key"/>"></div>

            <c:set var="error" value="${requestScope.error}" scope="page"/>
            <c:if test="${not empty error}">
                <div style="margin-top: 10px">
                    <p><fmt:message bundle="${bundle}" key="${error}"/></p>
                </div>
            </c:if>

            <input type="submit" value="${login}">
        </form>
        <a href="register.jsp" class="sign-up"><fmt:message bundle="${bundle}" key="page.register.h1_sign_up"/></a>
    </div>
</div>
<jsp:include page="elements/footer.jspf"/>
</body>
</html>
<style>
<%--    nav --%>
    .navUl ul{
        list-style-type: none;
        margin: 0;
        padding: 0;
        overflow: hidden;
        background-color: #333;
    }

    li {
        float: left;
    }

    li a {
        display: block;
        color: white;
        text-align: center;
        padding: 14px 16px;
        text-decoration: none;
    }


    li a:hover {
        background-color: #111;
    }


    /* Задній фон */
    body {
        background-color: #fff; /* білий фон */
        font-family: 'Raleway', sans-serif;
    }

    /* Фоновий блок */
    #container {
        width: 100%; /* 90% ширини сторінки */
        height: 100vh; /* 100% висоти видимої частини сторінки */
        background-color: #96d38c; /* салатово-зелений фон */
        display: flex;
        justify-content: center; /* центруємо по горизонталі */
        align-items: center; /* центруємо по вертикалі */
    }

    /* Форма входу */
    #box {
        width: 300px; /* ширина форми */
        padding: 20px; /* внутрішні відступи */
        background-color: #fff; /* білий фон форми */
        border-radius: 5px; /* закруглені кути форми */
        text-align: center; /* центруємо текст в формі */
        box-shadow: 0 0 10px rgba(0, 0, 0, 0.1); /* тінь форми */
    }

</style>