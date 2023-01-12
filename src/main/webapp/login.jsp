<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Login</title>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width">
    <link rel="stylesheet" href="css/all.min.css">
    <link rel="stylesheet" href="css/style.css">
</head>
<body>
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