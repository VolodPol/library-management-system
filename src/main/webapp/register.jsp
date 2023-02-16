<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="ctg" uri="/WEB-INF/tag_tld/custom_tag.tld"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<fmt:setLocale value="${sessionScope.locale}" scope="session"/>
<fmt:setBundle basename="Localization" var="bundle"/>

<fmt:message bundle="${bundle}" key="page.register.title" var="title"/>
<fmt:message bundle="${bundle}" key="page.register.h1_sign_up" var="h1_sign_up"/>
<fmt:message bundle="${bundle}" key="page.register.input_label.username" var="username"/>
<fmt:message bundle="${bundle}" key="page.register.input_label.email" var="email"/>
<fmt:message bundle="${bundle}" key="page.register.input_label.password" var="password"/>
<fmt:message bundle="${bundle}" key="page.register.input_label.first_name" var="first_name"/>
<fmt:message bundle="${bundle}" key="page.register.input_label.surname" var="surname"/>
<fmt:message bundle="${bundle}" key="page.register.input_label.phone" var="phone"/>
<fmt:message bundle="${bundle}" key="page.register.input_label.age" var="age"/>
<fmt:message bundle="${bundle}" key="page.register.link_sign_in" var="sign_in"/>
<html>
<head>
    <title>${title}</title>
<%--    <link rel="stylesheet" href="css/all.min.css">--%>
<%--    <link rel="stylesheet" href="css/style.css">--%>
    <meta name="viewport" content="width=device-width">
<%--    <link rel="stylesheet" href="css/content/nav_bar.css">--%>
</head>
<body>
<fmt:bundle basename="Localization" prefix="page.navigation.">
    <nav class="navUl">
        <ul>
            <li><a class="active" href="login.jsp">Sign In</a></li>
            <li><a href="front?command=books">Catalog</a></li>
            <ctg:lang locale="${sessionScope.locale}"/>
        </ul>
    </nav>
</fmt:bundle>


<div class="container">
    <div class="box">
        <h1>${h1_sign_up}</h1>
        <form name="registerForm" action="front" method="POST">
            <input name="command" type="hidden" value="sign_up"/>

            <label>${username}</label>
            <div>
                <i class="fa-solid fa-user"></i>
                <label>
                    <input name="login" type="text" placeholder="Enter Username">
                </label>
            </div>

            <label>${email}</label>
            <div>
                <i class="fa-solid fa-mail-bulk"></i>
                <label>
                    <input name="email" type="email" placeholder="Enter Email">
                </label>
            </div>

            <label>${password}</label>
            <div>
                <i class="fa-solid fa-lock"></i>
                <label>
                    <input name="password" type="password" placeholder="Enter Password">
                </label>
            </div>

            <label>${first_name}</label>
            <div>
                <i class="fa-solid fa-user-tag"></i>
                <label>
                    <input name="firstname" type="text" placeholder="Enter Your First Name">
                </label>
            </div>

            <label>${surname}</label>
            <div>
                <i class="fa-solid fa-user-tag"></i>
                <label>
                    <input name="surname" type="text" placeholder="Enter Your Surname">
                </label>
            </div>

            <label>${phone}</label>
            <div>
                <i class="fa-solid fa-phone"></i>
                <label>
                    <input name="phone" type="tel" placeholder="Input Your Phone">
                </label>
            </div>

            <label>${age}</label>
            <div>
                <i class="fa-solid fa-clock"></i>
                <label>
                    <input name="age" type="text" placeholder="Enter Your Age">
                </label>
            </div>

            <c:set var="error" value="${requestScope.error}" scope="page"/>
            <c:if test="${not empty error}">
                <div style="margin-top: 10px">
                    <p><fmt:message bundle="${bundle}" key="${error}"/></p>
                </div>
            </c:if>


            <input type="submit" value="${title}">
        </form>
        <a href="login.jsp" class="sign-up">${sign_in}</a>
    </div>
</div>
<jsp:include page="elements/footer.jspf"/>
</body>
</html>

<style>
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