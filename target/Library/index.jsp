<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<body>
<nav>
    <ul id="navUl">
        <li><a class="active" href="register.jsp">Sign In</a></li>
        <li><a href="front?command=books">Catalog</a></li>
        <li><a href="">Contact</a></li>
        <li style="float:right" ><a href="">About</a></li>
    </ul>
</nav>
    <h2>Hello, ${sessionScope.role} ${sessionScope.name}</h2>

    <form name="logoutForm" action="front" method="get">
        <input name="command" type="hidden" value="logout"/>
        <input type="submit" name="logoutButton" value="Log Out"/>
    </form>
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