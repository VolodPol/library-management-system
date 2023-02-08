<%@ page contentType="text/html;charset=UTF-8" %>
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
        <h1>Sign Up</h1>
        <form name="registerForm" action="front" method="POST">
            <input name="command" type="hidden" value="sign_up"/>

            <label>Username</label>
            <div>
                <i class="fa-solid fa-user"></i>
                <label>
                    <input name="login" type="text" placeholder="Enter Username">
                </label>
            </div>

            <label>Email</label>
            <div>
                <i class="fa-solid fa-mail-bulk"></i>
                <label>
                    <input name="email" type="email" placeholder="Enter Email">
                </label>
            </div>

            <label>Password</label>
            <div>
                <i class="fa-solid fa-lock"></i>
                <label>
                    <input name="password" type="password" placeholder="Enter Password">
                </label>
            </div>

            <label>First Name</label>
            <div>
                <i class="fa-solid fa-user-tag"></i>
                <label>
                    <input name="firstname" type="text" placeholder="Enter Your First Name">
                </label>
            </div>

            <label>Surname</label>
            <div>
                <i class="fa-solid fa-user-tag"></i>
                <label>
                    <input name="surname" type="text" placeholder="Enter Your Surname">
                </label>
            </div>

            <label>Phone number</label>
            <div>
                <i class="fa-solid fa-phone"></i>
                <label>
                    <input name="phone" type="tel" placeholder="Input Your Phone">
                </label>
            </div>

            <label>Age</label>
            <div>
                <i class="fa-solid fa-clock"></i>
                <label>
                    <input name="age" type="text" placeholder="Enter Your Age">
                </label>
            </div>


            <input type="submit" value="Register">
        </form>
        <a href="login.jsp" class="sign-up">Sign In</a>
    </div>
</div>
</body>
</html>

