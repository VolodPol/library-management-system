<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="ctg" uri="/WEB-INF/tag_tld/custom_tag.tld"%>
<html>
<head>
    <title>Profile</title>
    <link rel="stylesheet" href="css/nav.css">
    <link rel="stylesheet" href="css/buttons/submit-button.css">
</head>
<body>

<nav id="navUl">
    <ul>
        <li><a href="login.jsp">Sign In</a></li>
        <li><a href="front?command=books">Catalog</a></li>
        <c:choose>
            <c:when test="${sessionScope.role == 'user'}">
                <li><a href="front?command=my_books">Ordered books</a> </li>
            </c:when>
            <c:when test="${sessionScope.role == 'librarian'}">
                <li><a href="front?command=show_orders">Readers' orders</a></li>
                <li><a href="front?command=show_users">Users</a></li>
            </c:when>
            <c:when test="${sessionScope.role == 'admin'}">
                <li><a href="new_book.jsp">Create book</a></li>
                <li><a href="new_librarian.jsp">Create Librarian</a></li>
                <li><a href="front?command=show_librarians">Librarians</a></li>
                <li><a href="front?command=show_users">Users</a></li>
            </c:when>
        </c:choose>
        <li style="float:right"><a href="front?command=logout">Log Out</a></li>
        <li style="float:right" ><a class="active" href="front?command=profile">Profile</a></li>
    </ul>
</nav>
<c:set var="user" value="${requestScope.user}" scope="page"/>
<h2 style="text-align: center">Hello, ${sessionScope.name}!</h2>
<div id="container">
        <h2>Login   :   ${user.login}</h2>

        <h2>Email  :   ${user.email}</h2>


        <h2>Phone number    :   ${user.phone}</h2>


        <h2>User role    :   ${user.role}</h2>


        <h2>Subscription     :   ${user.subscription}</h2>

</div>

</body>
</html>

<style>
    <%--  Form styles  --%>
    #container {
        display: block;
        margin: auto 50px;
    }

    .input-div {
        padding-left: 20px;
    }

    .input {
        font-size: x-large;
    }

    * {
        font-family: 'Raleway', sans-serif;
    }

</style>
