<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <title>New order</title>
</head>
<body>

<nav>
    <ul id="navUl">
        <li><a class="active" href="register.jsp">Sign In</a></li>
        <li><a href="front?command=books">Catalog</a></li>
        <li><a href="">Contact</a></li>
        <li style="float:right" ><a href="">About</a></li>
    </ul>
</nav>

<h1 style="text-align: center">Create order</h1>
<div id="form-container">
    <form name="newOrderForm" action="front" method="post"><%-- was: action="front?isbn=${requestScope.isbn}" --%>

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

        <h2>Type of order:</h2>
        <div class="input-div">
            <label>
                <input type="radio" id="read_room" value="read_room" name="order_type">
                <label class="radio-label" for="read_room">To the reading room</label><br>
                <input type="radio" id="on_sub" value="on_sub" name="order_type">
                <label class="radio-label" for="on_sub">On a subscription</label>
            </label>
        </div>

        <div class="input-div"  style="padding: 20px">
            <input class="form-submit-button" type="submit" value="Place an order">
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

    .form-submit-button {
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

.form-submit-button:hover {background-color: #3e8e41}

.form-submit-button:active {
    background-color: #3e8e41;
    box-shadow: 0 5px #666;
    transform: translateY(4px);
}


<%-- Navigation bar --%>
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
