<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="ctg" uri="/WEB-INF/tag_tld/custom_tag.tld"%>

<fmt:setLocale value="${sessionScope.locale}" scope="session"/>
<fmt:setBundle basename="Localization" var="bundle"/>
<html>
<head>
    <title><fmt:message bundle="${bundle}" key="page.profile.title"/></title>
    <link rel="icon" href="https://img.icons8.com/external-flaticons-flat-flat-icons/64/null/external-library-university-flaticons-flat-flat-icons-3.png"/>
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
    <p><fmt:message bundle="${bundle}" key="page.profile.h2_login"/> :</p><h2>   ${requestScope.user.login}</h2>

    <p><fmt:message bundle="${bundle}" key="page.profile.h2_email"/> :</p><h2>   ${requestScope.user.email}</h2>


    <p><fmt:message bundle="${bundle}" key="page.profile.h2_phone"/> :</p><h2>   ${requestScope.user.phone}</h2>


    <p><fmt:message bundle="${bundle}" key="page.profile.h2_role"/> :</p><h2>   ${requestScope.user.role}</h2>

    <fmt:setLocale value="${sessionScope.locale}"/>
    <p><fmt:message bundle="${bundle}" key="page.profile.h2_fine_amount"/> :</p><h2>   <fmt:formatNumber type="currency" value="${requestScope.user.fineAmount}"/></h2>


    <p><fmt:message bundle="${bundle}" key="page.profile.h2_sub"/> :</p><h2>   ${requestScope.user.subscription}</h2>

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
    p {
        margin-left: 25px;
        font-size: 1.35em;
    }

</style>
