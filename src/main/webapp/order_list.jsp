<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Title</title>
    <link rel="stylesheet" href="css/nav.css">
    <link rel="stylesheet" href="css/buttons/submit-button.css">
</head>
<body>
<c:set var="userRole" value="${sessionScope.role}" scope="page"/>
    <nav id="navUl">
        <ul>
            <li><a href="front?command=books">Catalog</a></li>
            <li><a class="active" href="front?command=show_orders">Readers' orders</a></li>
            <li style="float:right"><a href="front?command=logout">Log Out</a></li>
            <li style="float:right" ><a href="front?command=profile">Profile</a></li>
        </ul>
    </nav>
    <div class="container">
        <h1>Orders</h1>
        <div class="box">

            <table class="content-table">
                <thead>
                <tr>
                    <th>Checked in at</th>
                    <th>Time to bring back</th>
                    <th>Reader's username</th>
                    <th>Book's title</th>
                    <c:if test="${userRole == 'librarian'}" var="testResult">
                        <th>Action</th>
                        <c:set var="orders" value="${requestScope.orderList}" scope="page"/>
                    </c:if>
                </tr>
                </thead>
                <c:forEach var="order" items="${orders}" varStatus="status">
                    <tr>
                        <td><c:out value="${order.startTime}"/></td>
                        <td><c:out value="${order.endTime}"/></td>
                        <td><c:out value="${order.username}"/></td>
                        <td><c:out value="${order.bookTitle}"/></td>
                        <c:if test="${testResult and order.orderStatus == 'UNCONFIRMED'}">
                            <td>
                                <c:set var="currentId" value="${status.current.id}" scope="page"/>
                                <form name="confirmOrderForm" action="front" method="post">
                                    <input type="hidden" name="command" value="confirm"/>
                                    <input type="hidden" name="bookId" value="${order.bookId}"/>
                                    <button type="submit" class="submit-button" name="confirmOrder" value="${currentId}">
                                        Confirm
                                    </button>
                                </form>
                            </td>
                        </c:if>
                    </tr>
                </c:forEach>
            </table>
        </div>
    </div>
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

    * {
        font-family: 'Raleway', sans-serif;
    }
</style>
