<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Ordered books</title>
    <link rel="stylesheet" href="css/nav.css">
    <link rel="stylesheet" href="css/buttons/submit-button.css">
</head>
<body>
<nav id="navUl">
    <ul>
        <li><a href="login.jsp">Sign In</a></li>
        <li><a href="front?command=books">Catalog</a></li>
        <li><a class="active" href="front?command=my_books">Ordered books</a> </li>
        <li style="float:right"><a href="front?command=logout">Log Out</a></li>
        <li style="float:right" ><a href="front?command=profile">Profile</a></li>
    </ul>
</nav>
<c:set var="messageList" value="${requestScope.messageList}" scope="page"/>
<c:choose>
    <c:when test="${empty requestScope.userBookList}">
        <div class="container">
            <h1>Unfortunately, you haven't ordered any book yet.</h1>
        </div>
        <a href="front?command=books">Go to catalog</a>
    </c:when>
    <c:otherwise>
        <div class="container">
            <h1>Ordered books</h1>
            <div class="box">
                <table class="content-table">
                    <thead>
                    <tr>
                        <th>Title</th>
                        <th>Author</th>
                        <th>Order date</th>
                        <th>Return before</th>
                        <th>Subscription</th>
                        <c:if test="${not empty messageList}">
                            <th>Status</th>
                        </c:if>
                        <th>Return book</th>
                    </tr>
                    </thead>

                    <c:forEach var="userBook" items="${requestScope.userBookList}" varStatus="status">
                        <tr>
                            <td><c:out value="${userBook.title}"/></td>
                            <td><c:out value="${userBook.author}"/></td>
                            <td><c:out value="${userBook.orderDate}"/></td>
                            <td><c:out value="${userBook.returnDate}"/></td>
                            <td><c:out value="${userBook.subscription}"/></td>
                            <c:set var="message" value="${messageList[status.index]}" scope="page"/>
                            <td>
                                <c:out value="${message}"/>
                            </td>

                            <td>
                                <form action="front?start=${userBook.orderDate}&end=${userBook.returnDate}" name="returnForm" method="post">
                                    <input type="hidden" name="command" value="return">
                                    <button class="submit-button" type="submit" name="bookId" value="${userBook.bookId}">
                                        Return
                                    </button>
                                </form>
                            </td>
                        </tr>
                    </c:forEach>
                </table>
            </div>
        </div>
    </c:otherwise>
</c:choose>
</body>
</html>

<style>
    <%--Book table styles--%>
    .box {
        display: flex;
        align-items: center;
        justify-content: center;
    }

    .container h1 {
        text-align: center;
    }

    * {
        /* Change your font family */
        font-family: sans-serif;
    }

    .content-table {
        border-collapse: collapse;
        margin: 25px 0;
        font-size: 1.35em;/* was 0.9 em */
        min-width: 400px;
        border-radius: 5px 5px 0 0;
        overflow: hidden;
        box-shadow: 0 0 20px rgba(0, 0, 0, 0.15);
    }

    .content-table thead tr {
        background-color: #1e673a;
        color: #ffffff;
        text-align: left;
        font-weight: bold;
    }

    .content-table th,
    .content-table td {
        padding: 12px 15px;
    }

    .content-table tbody tr {
        border-bottom: 1px solid #dddddd;
    }

    .content-table tbody tr:nth-of-type(even) {
        background-color: #f3f3f3;
    }

    .content-table tbody tr:last-of-type {
        border-bottom: 2px solid #1e673a;
    }

    .content-table tbody tr.active-row {
        font-weight: bold;
        color: #1e673a;
    }
    /*    -------------------------------------------------*/
    * {
        font-family: 'Raleway', sans-serif;
    }
</style>