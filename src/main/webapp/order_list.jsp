<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="ctg" uri="/WEB-INF/tag_tld/custom_tag.tld"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<fmt:setLocale value="${sessionScope.locale}" scope="session"/>
<fmt:setBundle basename="Localization" var="bundle"/>

<fmt:message bundle="${bundle}" key="page.order_list.title" var="title"/>
<fmt:message bundle="${bundle}" key="page.order_list.h1_my_books" var="h1_my_books"/>
<fmt:message bundle="${bundle}" key="page.order_list.book_table.checked_in" var="checked_in"/>
<fmt:message bundle="${bundle}" key="page.order_list.book_table.time_bring" var="time_bring"/>
<fmt:message bundle="${bundle}" key="page.order_list.book_table.reader_username" var="username"/>
<fmt:message bundle="${bundle}" key="page.order_list.book_table.book_title" var="book_title"/>
<fmt:message bundle="${bundle}" key="page.order_list.book_table.type_of_order" var="order_type"/>
<fmt:message bundle="${bundle}" key="page.order_list.book_table.Fine" var="fine"/>
<fmt:message bundle="${bundle}" key="page.order_list.book_table.Action" var="action"/>

<html>
<head>
    <title>${title}</title>
</head>
<body>
<c:set var="userRole" value="${sessionScope.role}" scope="page"/>
<fmt:bundle basename="Localization" prefix="page.navigation.">
    <nav class="navUl">
        <ul>
            <li><a href="front?command=books"><fmt:message key="catalog"/></a></li>
            <li><a class="active" href="front?command=show_orders"><fmt:message key="reader_orders"/></a></li>
            <li><a href="front?command=show_users"><fmt:message key="users"/></a></li>
            <li style="float:right"><a href="front?command=logout"><fmt:message key="logout"/></a></li>
            <li style="float:right" ><a href="front?command=profile"><fmt:message key="profile"/></a></li>
            <ctg:lang locale="${sessionScope.locale}"/>
        </ul>
    </nav>
</fmt:bundle>

    <div class="container">
        <h1>${h1_my_books}</h1>
        <div class="box">

            <table class="content-table">
                <thead>
                <tr>
                    <th>${checked_in}</th>
                    <th>${time_bring}</th>
                    <th>${username}</th>
                    <th>${book_title}</th>
                    <th>${order_type}</th>
                    <th>${fine}</th>
                    <th>${action}</th>
                </tr>
                </thead>
                <c:set var="orders" value="${requestScope.orderList}" scope="page"/>
                <c:forEach var="order" items="${orders}" varStatus="status">
                    <tr>
                        <td><c:out value="${order.startTime}"/></td>
                        <td><c:out value="${order.endTime}"/></td>
                        <td><c:out value="${order.username}"/></td>
                        <td><c:out value="${order.bookTitle}"/></td>
                        <td><c:out value="${order.type}"/></td>
                        <td><c:out value="${order.finedStatus}"/></td>
                        <c:if test="${order.orderStatus == 'UNCONFIRMED'}">
                            <td>
                                <c:set var="currentId" value="${status.current.id}" scope="page"/>
                                <form name="confirmOrderForm" action="front" method="post">
                                    <input type="hidden" name="command" value="confirm"/>
                                    <input type="hidden" name="bookId" value="${order.bookId}"/>
                                    <button type="submit" class="submit-button" name="confirmOrder" value="${currentId}">
                                        <fmt:message bundle="${bundle}" key="page.button.confirm"/>
                                    </button>
                                </form>
                            </td>
                        </c:if>
                    </tr>
                </c:forEach>
            </table>
        </div>
    </div>
<jsp:include page="elements/footer.jspf"/>
</body>
</html>
<style>
    /*Book table styles*/
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

    /*.content-table tbody tr.active-row {*/
    /*    font-weight: bold;*/
    /*    color: #1e673a;*/
    /*}*/

    * {
        font-family: 'Raleway', sans-serif;
    }

/*    nav */
    .navUl ul{
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
/*    submit */
    .submit-button {
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

    .submit-button:hover {background-color: #3e8e41}

    .submit-button:active {
        background-color: #3e8e41;
        box-shadow: 0 5px #666;
        transform: translateY(4px);
    }
</style>
