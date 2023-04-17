<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="ctg" uri="/WEB-INF/tag_tld/custom_tag.tld"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<fmt:setLocale value="${sessionScope.locale}" scope="session"/>
<fmt:setBundle basename="Localization" var="bundle"/>

<c:if test="${empty sessionScope.role}" var="isEmptyRole" scope="page"/>
<html>
<head>
    <title><fmt:message bundle="${bundle}" key="page.index.title"/></title>
    <link rel="icon" href="https://img.icons8.com/external-flaticons-flat-flat-icons/64/null/external-library-university-flaticons-flat-flat-icons-3.png"/>
</head>
<body>
<fmt:bundle basename="Localization" prefix="page.navigation.">
    <nav class="navUl">
        <ul>
            <c:if test="${isEmptyRole}">
                <li><a class="active" href="login.jsp"><fmt:message key="sign_in"/></a></li>
            </c:if>
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
            <c:if test="${not isEmptyRole}">
                <li style="float:right"><a href="front?command=logout"><fmt:message key="logout"/></a></li>
                <li style="float:right" ><a href="front?command=profile"><fmt:message key="profile"/></a></li>
            </c:if>
            <ctg:lang locale="${sessionScope.locale}"/>
        </ul>
    </nav>
</fmt:bundle>

<div class="main-div">
    <div class="welcome-box">
        <ctg:hi role="${sessionScope.role}" login="${sessionScope.name}" locale="${sessionScope.locale}"/>
    </div>
</div>
<jsp:include page="elements/footer.jspf"/>
</body>
</html>
<style>
    .main-div {
        background-image: url(https://img.freepik.com/premium-photo/book-stack-library-room-blurred-bookshelf-background_42691-514.jpg?w=2000);
        /* filter: blur(2px); */
        /* -webkit-filter: blur(2px); */
        height: 100%;

        background-position: center;
        background-repeat: no-repeat;
        background-size: cover;
    }

    .welcome-box {
        font-weight: bold;
        font-size: x-large;
        position: absolute;
        top: 50%;
        left: 50%;
        transform: translate(-50%, -50%);
        z-index: 2;
        width: 80%;
        padding: 20px;
        text-align: center;
    }
    * {
        font-family: 'Raleway', sans-serif;
    }

/*    nav bar*/
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
</style>