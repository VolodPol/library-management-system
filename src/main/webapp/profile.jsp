<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="ctg" uri="/WEB-INF/tag_tld/custom_tag.tld"%>

<fmt:setLocale value="${sessionScope.locale}" scope="session"/>
<fmt:setBundle basename="Localization" var="bundle"/>
<html>
<head>
    <title><fmt:message bundle="${bundle}" key="page.profile.title"/></title>
<%--    <link rel="stylesheet" href="css/content/nav_bar.css">--%>
<%--    <link rel="stylesheet" href="css/buttons/submit-button.css">--%>
</head>
<body>

<fmt:bundle basename="Localization" prefix="page.navigation.">
    <nav class="navUl">
        <ul>
            <li><a href="front?command=books"><fmt:message key="catalog"/></a></li>
            <c:choose>
                <c:when test="${sessionScope.role == 'user'}">
                    <li><a href="front?command=my_books"><fmt:message key="ordered_books"/></a> </li>
                </c:when>
                <c:when test="${sessionScope.role == 'librarian'}">
                    <li><a href="front?command=show_orders"><fmt:message key="reader_orders"/></a></li>
                    <li><a href="front?command=show_users"><fmt:message key="users"/></a></li>
                </c:when>
                <c:when test="${sessionScope.role == 'admin'}">
                    <li><a href="new_book.jsp"><fmt:message key="create_book"/></a></li>
                    <li><a href="new_librarian.jsp"><fmt:message key="create_librarian"/></a></li>
                    <li><a href="front?command=show_librarians"><fmt:message key="librarians"/></a></li>
                    <li><a href="front?command=show_users"><fmt:message key="users"/></a></li>
                </c:when>
            </c:choose>
            <li style="float:right"><a href="front?command=logout"><fmt:message key="logout"/></a></li>
            <li style="float:right" ><a class="active" href="front?command=profile"><fmt:message key="profile"/></a></li>
            <ctg:lang locale="${sessionScope.locale}"/>
        </ul>
    </nav>
</fmt:bundle>

<c:set var="user" value="${requestScope.user}" scope="page"/>
<h2 style="text-align: center"><fmt:message bundle="${bundle}" key="page.profile.h2_greeting"/> ${sessionScope.name}!</h2>
<div id="container">
        <h2><fmt:message bundle="${bundle}" key="page.profile.h2_login"/>   :   ${user.login}</h2>

        <h2><fmt:message bundle="${bundle}" key="page.profile.h2_email"/>  :   ${user.email}</h2>


        <h2><fmt:message bundle="${bundle}" key="page.profile.h2_phone"/>    :   ${user.phone}</h2>


        <h2><fmt:message bundle="${bundle}" key="page.profile.h2_role"/>    :   ${user.role}</h2>


        <h2><fmt:message bundle="${bundle}" key="page.profile.h2_fine_amount"/>    :   ${user.fineAmount}</h2>


        <h2><fmt:message bundle="${bundle}" key="page.profile.h2_sub"/>     :   ${user.subscription}</h2>

</div>

</body>
<jsp:include page="elements/footer.jspf"/>
</html>

<style>
    <%--  Form styles  --%>
    #container {
        display: block;
        margin: auto 50px;
    }

    .input-div {
        padding-left: 20px;
    }

    .input {
        font-size: x-large;
    }

    * {
        font-family: 'Raleway', sans-serif;
    }

    /*    nav */
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
    /*    submit */
    .submit-button {
        padding: 10px;
        background: #0066A2;
        color: white;
        border-style: outset;
        border-color: #0066A2;
        font: bold 18px arial,sans-serif;
        text-shadow: none;
        cursor: pointer;
        box-shadow: 0 2px #999;
    }

    .submit-button:hover {background-color: #3e8e41}

    .submit-button:active {
        background-color: #3e8e41;
        box-shadow: 0 5px #666;
        transform: translateY(4px);
    }

</style>
