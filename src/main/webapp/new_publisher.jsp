<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>New publisher</title>
    <link rel="stylesheet" href="css/nav.css">
    <link rel="stylesheet" href="css/buttons/submit-button.css">
</head>
<body>

<nav id="navUl">
    <ul>
        <li><a href="login.jsp">Sign In</a></li>
        <li><a href="front?command=books">Catalog</a></li>
        <li><a href="new_book.jsp">Create book</a></li>
        <li><a href="new_librarian.jsp">Create Librarian</a></li>
        <li><a href="front?command=show_librarians">Librarians</a></li>
        <li><a href="">Contact</a></li>
        <li style="float:right"><a href="front?command=logout">Log Out</a></li>
        <li style="float:right" ><a href="">About</a></li>
    </ul>
</nav>

<h1 style="text-align: center">Create publisher</h1>
<div id="form-container">
    <form name="newPublisherForm" action="front" method="post">
        <input type="hidden" name="command" value="create_publisher">

        <h2>Name of publication</h2>
        <div class="input-div">
            <label>
                <input class="input" name="publication" type="text"/>
            </label>
        </div>
<%--        Publication is present --%>
        <c:set var="message" value="${requestScope.publicationPresenceError}"/>
        <c:if test="${not empty message}">
            <h3>${message}</h3>
        </c:if>

        <div class="input-div"  style="padding: 20px">
            <input class="submit-button" type="submit" value="Add a publisher">
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

    .input {
        font-size: x-large;
    }
</style>
