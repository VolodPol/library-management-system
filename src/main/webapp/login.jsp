<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="ctg" uri="/WEB-INF/tag_tld/custom_tag.tld"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<fmt:setLocale value="${sessionScope.locale}" scope="session"/>
<fmt:setBundle basename="Localization" var="bundle"/>

<fmt:message bundle="${bundle}" key="page.login.title" var="title"/>
<fmt:message bundle="${bundle}" key="page.login.h1_sign_in" var="login"/>
<fmt:message bundle="${bundle}" key="page.login.input_label.username" var="username"/>
<fmt:message bundle="${bundle}" key="page.login.input_label.password" var="password"/>
<html>
<head>
    <title>${title}</title>
    <meta name="viewport" content="width=device-width">
    <link rel="stylesheet" type="text/css" href="./css/all.min.css"/>
    <link rel="stylesheet" type="text/css" href="./css/style.css"/>
</head>
<body>
<fmt:bundle basename="Localization" prefix="page.navigation.">
    <nav class="navUl">
        <ul>
            <li><a href="front?command=books">
                <fmt:message key="catalog"/>
            </a></li>
                <li style="float:right" ><a href="front?command=profile">
                    <fmt:message key="profile"/>
                </a></li>
            <ctg:lang locale="${sessionScope.locale}"/>
        </ul>
    </nav>
</fmt:bundle>

<div class="container">
    <div class="box">
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

    .active {
        background-color: #1e6839;
    }

/*    page */
</style>