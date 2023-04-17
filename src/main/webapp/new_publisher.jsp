<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="ctg" uri="/WEB-INF/tag_tld/custom_tag.tld"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<fmt:setLocale value="${sessionScope.locale}" scope="session"/>
<fmt:setBundle basename="Localization" var="bundle"/>
<html>
<head>
    <title><fmt:message bundle="${bundle}" key="page.new_publisher.title"/></title>
    <link rel="icon" href="https://img.icons8.com/external-flaticons-flat-flat-icons/64/null/external-library-university-flaticons-flat-flat-icons-3.png"/>
</head>
<body>

<fmt:bundle basename="Localization" prefix="page.navigation.">
    <nav class="navUl">
        <ul>
            <li><a href="front?command=books"><fmt:message key="catalog"/></a></li>
            <li><a href="new_book.jsp"><fmt:message key="create_book"/></a></li>
            <li><a href="new_librarian.jsp"><fmt:message key="create_librarian"/></a></li>
            <li><a href="front?command=show_librarians"><fmt:message key="librarians"/></a></li>
            <li><a href="front?command=show_users"><fmt:message key="users"/></a></li>
            <li style="float:right"><a href="front?command=logout"><fmt:message key="logout"/></a></li>
            <li style="float:right" ><a href="front?command=profile"><fmt:message key="profile"/></a></li>
            <ctg:lang locale="${sessionScope.locale}"/>
        </ul>
    </nav>
</fmt:bundle>


<h1 style="text-align: center"><fmt:message bundle="${bundle}" key="page.new_publisher.h1_create_publisher"/></h1>
<div id="form-container">
    <form name="newPublisherForm" action="front" method="post">
        <input type="hidden" name="command" value="create_publisher">

        <h2><fmt:message bundle="${bundle}" key="page.new_publisher.h2_name_of_publication"/></h2>
        <div class="input-div">
            <label>
                <input class="input" name="publication" type="text"/>
            </label>
        </div>
<%--        Publication is present --%>
        <c:set var="message" value="${requestScope.error}"/>
        <c:if test="${not empty message}">
            <h3><fmt:message bundle="${bundle}" key="${message}"/></h3>
        </c:if>

        <div class="input-div"  style="padding: 20px">
            <input class="submit-button" type="submit" value="<fmt:message bundle="${bundle}" key="page.button.add_publisher"/>">
        </div>
    </form>
</div>
<jsp:include page="elements/footer.jspf"/>
</body>
</html>
<style>
    <%--  Form styles  --%>
    #form-container {
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
    <%--    nav bar--%>
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

    /*.active {*/
    /*    background-color: #1e6839;*/
    /*}*/
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
