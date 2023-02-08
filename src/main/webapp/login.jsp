<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Login</title>
    <meta name="viewport" content="width=device-width">
    <link rel="stylesheet" href="css/all.min.css">
    <link rel="stylesheet" href="css/style.css">
    <link rel="stylesheet" href="css/nav.css">
</head>
<body>
<nav id="navUl">
    <ul>
        <li><a class="active" href="login.jsp">Sign In</a></li>
        <li><a href="front?command=books">Catalog</a></li>
        <li style="float:right" ><a href="front?command=profile">Profile</a></li>
    </ul>
</nav>

<div class="container">
    <div class="box">
        <h1>Sign In</h1>
        <form name="loginForm" action="front" method="POST">
            <input name="command" type="hidden" value="login"/>

            <label>Username</label>
            <div>
                <i class="fa-solid fa-user"></i>
                <label>
                    <input name="login" type="text" placeholder="Enter Username">
                </label>
            </div>

            <label>Password</label>
            <div>
                <i class="fa-solid fa-lock"></i>
                <label>
                    <input name="password" type="password" placeholder="Enter Password">
                </label>
            </div>

            <%--@elvariable id="errorMessage" type="java.lang.String"--%>
            <c:if test="${not empty errorMessage}">
                <div style="margin-bottom: 10px">
                    <p>${errorMessage}</p>
                </div>
                <div>
                    <a class="fa-link" style="" href="#">Contact</a>
                </div>
            </c:if>

            <input type="submit" value="Sing In">
        </form>
        <a href="register.jsp" class="sign-up">Sign Up</a>
    </div>
</div>
</body>
</html>