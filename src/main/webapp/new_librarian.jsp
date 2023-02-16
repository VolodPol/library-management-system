<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="ctg" uri="/WEB-INF/tag_tld/custom_tag.tld"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<fmt:setLocale value="${sessionScope.locale}" scope="session"/>
<fmt:setBundle basename="Localization" var="bundle"/>
<html>
<head>
    <title><fmt:message bundle="${bundle}" key="page.new_librarian.title"/></title>
    <meta name="viewport" content="width=device-width">
<%--    <link rel="stylesheet" href="css/all.min.css">--%>
<%--    <link rel="stylesheet" href="css/style.css">--%>
<%--    <link rel="stylesheet" href="css/content/nav_bar.css">--%>
<%--    <link rel="stylesheet" href="css/buttons/submit-button.css">--%>
</head>
<body>
<fmt:bundle basename="Localization" prefix="page.navigation.">
    <nav class="navUl">
        <ul>
            <li><a href="front?command=books"><fmt:message key="catalog"/></a></li>
            <li><a href="new_book.jsp"><fmt:message key="create_book"/></a></li>
            <li><a class="active" href="new_librarian.jsp"><fmt:message key="create_librarian"/></a></li>
            <li><a href="front?command=show_librarians"><fmt:message key="librarians"/></a></li>
            <li><a href="front?command=show_users"><fmt:message key="users"/></a></li>
            <li style="float:right"><a href="front?command=logout"><fmt:message key="logout"/></a></li>
            <li style="float:right" ><a href="front?command=profile"><fmt:message key="profile"/></a></li>
            <ctg:lang locale="${sessionScope.locale}"/>
        </ul>
    </nav>
</fmt:bundle>


<div class="container">
    <div class="box">
        <h1><fmt:message bundle="${bundle}" key="page.new_librarian.h1_create_librarian"/></h1>
        <form name="registerForm" action="front" method="POST">
            <input name="command" type="hidden" value="create_librarian"/>

            <label><fmt:message bundle="${bundle}" key="page.new_librarian.input_label.login"/></label>
            <div>
                <i class="fa-solid fa-user"></i>
                <label>
                    <input name="login" type="text" placeholder="Enter Username">
                </label>
            </div>

            <label><fmt:message bundle="${bundle}" key="page.new_librarian.input_label.email"/></label>
            <div>
                <i class="fa-solid fa-mail-bulk"></i>
                <label>
                    <input name="email" type="email" placeholder="Enter Email">
                </label>
            </div>

            <label><fmt:message bundle="${bundle}" key="page.new_librarian.input_label.password"/></label>
            <div>
                <i class="fa-solid fa-lock"></i>
                <label>
                    <input name="password" type="password" placeholder="Enter Password">
                </label>
            </div>

            <label><fmt:message bundle="${bundle}" key="page.new_librarian.input_label.first_name"/></label>
            <div>
                <i class="fa-solid fa-user-tag"></i>
                <label>
                    <input name="firstname" type="text" placeholder="Enter Your First Name">
                </label>
            </div>

            <label><fmt:message bundle="${bundle}" key="page.new_librarian.input_label.surname"/></label>
            <div>
                <i class="fa-solid fa-user-tag"></i>
                <label>
                    <input name="surname" type="text" placeholder="Enter Your Surname">
                </label>
            </div>

            <label><fmt:message bundle="${bundle}" key="page.new_librarian.input_label.phone"/></label>
            <div>
                <i class="fa-solid fa-phone"></i>
                <label>
                    <input name="phone" type="tel" placeholder="Input Your Phone">
                </label>
            </div>

            <label><fmt:message bundle="${bundle}" key="page.new_librarian.input_label.age"/></label>
            <div>
                <i class="fa-solid fa-clock"></i>
                <label>
                    <input name="age" type="text" placeholder="Enter Your Age">
                </label>
            </div>

            <input class="submit-button" type="submit" value="<fmt:message bundle="${bundle}" key="page.button.add_librarian"/>">
        </form>
    </div>
</div>
<jsp:include page="elements/footer.jspf"/>
</body>
</html>

<style>
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
/*    page */
</style>