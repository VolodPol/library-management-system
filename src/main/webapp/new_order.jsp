<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>New order</title>
    <link rel="stylesheet" href="css/nav.css">
    <link rel="stylesheet" href="css/buttons/submit-button.css">
</head>
<body>

<nav id="navUl">
    <ul>
        <li><a href="login.jsp">Sign In</a></li>
        <li><a href="front?command=books">Catalog</a></li>
        <li><a href="front?command=my_books">Ordered books</a> </li>
        <li style="float:right"><a href="front?command=logout">Log Out</a></li>
        <li style="float:right" ><a href="front?command=profile">Profile</a></li>
    </ul>
</nav>

<h1 style="text-align: center">Create order</h1>
<div id="form-container">
    <form name="newOrderForm" action="front?isbn=${param.isbn}" method="post"><%-- was: action="front?isbn=${requestScope.isbn}" --%>

        <input type="hidden" name="command" value="order">

        <h2>Time of the registration</h2>
        <div class="input-div">
            <label>
                <input class="datetime-input" name="startTime" type="datetime-local"/>
            </label>
        </div>

        <h2>Time to bring back</h2>
        <div class="input-div">
            <label>
                <input class="datetime-input" name="endTime" type="datetime-local"/>
            </label>
        </div>

        <c:if test="${not empty requestScope.errorMessage}"><%--@elvariable id="errorMessage" type="java.lang.String"--%>
            <h3 style="color: red">${errorMessage}</h3>
            <a href="#" >Upgrade subscription</a>
        </c:if>

        <h2>Type of order:</h2>
        <div class="input-div">
            <label>
                <input type="radio" id="read_room" value="reading_room" name="order_type">
                <label class="radio-label" for="read_room">To the reading room</label><br>
                <input type="radio" id="on_sub" value="subscription" name="order_type">
                <label class="radio-label" for="on_sub">On a subscription</label>
            </label>
        </div>

        <div class="input-div"  style="padding: 20px">
            <input class="submit-button" type="submit" value="Place an order">
        </div>
    </form>
</div>

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

    .datetime-input,
    .radio-label {
        font-size: x-large;
    }

    * {
        font-family: 'Raleway', sans-serif;
    }
</style>
