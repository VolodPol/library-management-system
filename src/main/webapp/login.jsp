<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Login</title>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width">
    <link rel="stylesheet" href="css/all.min.css">
    <link rel="stylesheet" href="css/style.css">
</head>
<body>
<%--<nav>--%>
<%--    <a href="front?command=books">Каталог книг</a>--%>
<%--    <a href="" class="profile">Про сайт</a>--%>
<%--</nav>--%>
<nav>
    <ul id="navUl">
        <li><a class="active" href="register.jsp">Sign In</a></li>
        <li><a href="front?command=books">Catalog</a></li>
        <li><a href="">Contact</a></li>
        <li style="float:right" ><a href="">About</a></li>
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


            <input type="submit" value="Sing In">
        </form>
        <a href="register.jsp" class="sign-up">Sign Up</a>
    </div>
</div>
</body>
</html>

<style>
    /*ul  */
    #navUl {
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