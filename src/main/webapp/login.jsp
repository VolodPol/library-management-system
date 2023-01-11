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


<%--<!DOCTYPE html>--%>
<%--<html lang="en">--%>
<%--<head>--%>
<%--    <meta charset="UTF-8">--%>
<%--    <meta http-equiv="X-UA-Compatible" content="IE=edge">--%>
<%--    <meta name="viewport" content="width=device-width, initial-scale=1.0">--%>
<%--    <title>Login</title>--%>
<%--    <link rel="stylesheet" href="styles.css">--%>
<%--    <script src="https://kit.fontawesome.com/2b070e02a1.js" crossorigin="anonymous"></script>--%>
<%--</head>--%>
<%--<body>--%>
<%--<div class="container">--%>
<%--    <div class="login-box">--%>
<%--        <div class="user-icon">--%>
<%--            <i class="far fa-user"></i>--%>
<%--        </div>--%>
<%--        <form class="login">--%>
<%--            <div class="form-group">--%>
<%--                <input type="text" placeholder="Username" id="username" class="form-control">--%>
<%--            </div>--%>
<%--            <div class="form-group">--%>
<%--                <input type="password" placeholder="Password" id="password" class="form-control">--%>
<%--            </div>--%>
<%--            <div class="form-group">--%>
<%--                <label for="rememberme">--%>
<%--                    <input type="checkbox" name="rememberme" id="rememberme">--%>
<%--                    Remember Password--%>
<%--                </label>--%>
<%--            </div>--%>
<%--            <div class="form-group">--%>
<%--                <button class="full-btn">Login</button>--%>
<%--            </div>--%>
<%--            <div class="form-group">--%>
<%--                <p>Not Registered? <a href="register">Sign Up</a></p>--%>
<%--            </div>--%>
<%--        </form>--%>
<%--        <div class="serparater">--%>
<%--            Or login via social links--%>
<%--        </div>--%>
<%--        <div class="social-login">--%>
<%--            <a href="facebook">--%>
<%--                <i class="fab fa-facebook-square"></i>--%>
<%--                Facebook--%>
<%--            </a>--%>
<%--            <a href="google">--%>
<%--                <i class="fab fa-google"></i>--%>
<%--                Google--%>
<%--            </a>--%>
<%--        </div>--%>
<%--    </div>--%>
<%--</div>--%>
<%--</body>--%>
<%--</html>--%>

