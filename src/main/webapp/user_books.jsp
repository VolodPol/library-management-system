<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="ctg" uri="/WEB-INF/tag_tld/custom_tag.tld"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<fmt:setLocale value="${sessionScope.locale}" scope="session"/>
<fmt:setBundle basename="Localization" var="bundle"/>

<fmt:message bundle="${bundle}" key="page.user_books.title" var="title"/>
<fmt:message bundle="${bundle}" key="page.user_books.message" var="message"/>
<fmt:message bundle="${bundle}" key="page.user_books.message.link" var="message_link"/>
<fmt:message bundle="${bundle}" key="page.user_books.h1_ordered_books" var="h1_ordered"/>
<fmt:message bundle="${bundle}" key="page.user_books.table.title" var="tb_title"/>
<fmt:message bundle="${bundle}" key="page.user_books.table.author" var="tb_author"/>
<fmt:message bundle="${bundle}" key="page.user_books.table.order_date" var="tb_order_date"/>
<fmt:message bundle="${bundle}" key="page.user_books.table.return_before" var="tb_return_before"/>
<fmt:message bundle="${bundle}" key="page.user_books.table.sub" var="tb_sub"/>
<fmt:message bundle="${bundle}" key="page.user_books.table.fine" var="tb_fine"/>
<fmt:message bundle="${bundle}" key="page.user_books.table.return_book" var="tb_return_book"/>

<html>
<head>
    <title>${title}</title>
    <link rel="icon" href="https://img.icons8.com/external-flaticons-flat-flat-icons/64/null/external-library-university-flaticons-flat-flat-icons-3.png"/>
</head>
<body>
<fmt:bundle basename="Localization" prefix="page.navigation.">
    <nav class="navUl">
        <ul>
            <li><a href="front?command=books"><fmt:message key="catalog"/></a></li>
            <li><a class="active" href="front?command=my_books"><fmt:message key="ordered_books"/></a> </li>
            <li style="float:right"><a href="front?command=logout"><fmt:message key="logout"/></a></li>
            <li style="float:right" ><a href="front?command=profile"><fmt:message key="profile"/></a></li>
            <ctg:lang locale="${sessionScope.locale}"/>
        </ul>
    </nav>
</fmt:bundle>

<c:choose>
    <c:when test="${empty requestScope.userBookList}">
        <div class="container">
            <h1>${message}</h1>
        </div>
        <a href="front?command=books">${message_link}</a>
    </c:when>
    <c:otherwise>
        <div class="container">
            <h1>${h1_ordered}</h1>
            <div class="box">
                <table class="content-table">
                    <thead>
                    <tr>
                        <th>${tb_title}</th>
                        <th>${tb_author}</th>
                        <th>${tb_order_date}</th>
                        <th>${tb_return_before}</th>
                        <th>${tb_sub}</th>
                        <th>${tb_fine}</th>
                        <th>${tb_return_book}</th>
                    </tr>
                    </thead>

                    <c:forEach var="userBook" items="${requestScope.userBookList}" varStatus="status">
                        <tr>
                            <td><c:out value="${userBook.title}"/></td>
                            <td><c:out value="${userBook.author}"/></td>
                            <td><c:out value="${userBook.orderDate}"/></td>
                            <td><c:out value="${userBook.returnDate}"/></td>
                            <td><c:out value="${userBook.subscription}"/></td>
                            <td><c:out value="${userBook.finedStatus}"/></td>

                            <td>
                                <form action="front?start=${userBook.orderDate}&end=${userBook.returnDate}" name="returnForm" method="post">
                                    <input type="hidden" name="command" value="return">
                                    <button class="submit-button" type="submit" name="bookId" value="${userBook.bookId}">
                                        <fmt:message bundle="${bundle}" key="page.button.return"/>
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
<jsp:include page="elements/footer.jspf"/>
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

    /*    -------------------------------------------------*/
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