<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Login</title>
    <meta charset="UTF-8">
</head>
<body>
<%--<nav class="nav-bar-cell1-rev">--%>
<%--    <a href="" class="profile">Про сайт</a>--%>
<%--    <a href="" class="profile">Ще щось</a>--%>
<%--</nav>--%>
<nav>
    <ul id="navUl">
        <li><a href="login.jsp">Sign In</a></li>
        <li><a class="active" href="front?command=books">Catalog</a></li>
        <li><a href="">Contact</a></li>
        <li style="float:right" ><a href="">About</a></li>
    </ul>
</nav>
<div class="container">
    <h1>Book Catalog</h1>
    <div class="box">

        <table class="content-table">
            <c:set var="userRole" value="${sessionScope.role}" scope="page"/>
            <thead>
                <tr>
                    <th>Title</th>
                    <th>Author</th>
                    <th>ISBN</th>
                    <th>Number of Copies</th>
                    <th>Date of Publication</th>
                    <c:if test="${userRole == 'user'}" var="testResult">
                        <th>Action</th>
                    </c:if>
                </tr>
            </thead>
            <c:forEach var="book" items="${requestScope.bookList}" varStatus="status">
                <tr>
                    <td>
                        <c:out value="${book.title}"/>
                    </td>
                    <td>
                        <c:out value="${book.author}"/>
                    </td>
                    <td>
                        <c:out value="${book.isbn}"/>
                    </td>
                    <td>
                        <c:out value="${book.copiesNumber}"/>
                    </td>
                    <td>
                        <c:out value="${book.dateOfPublication}"/>
                    </td>

                    <c:if test="${testResult}">
                        <td>
<%--                            <form name="orderForm" action="new_order.jsp" method="get">--%>
<%--                                <input type="hidden" name="isbn" value="${book.isbn}"/>--%>
<%--                                <input type="submit" value="Order" id="submit-button"/>--%>
<%--                            </form>--%>
                            <c:set var="isbn" value="${book.isbn}" scope="session"/> <%-- was request scope --%>
                            <form name="orderForm" action="new_order.jsp" method="get">
                                <input type="submit" value="Order" id="submit-button"/>
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
        font-size: 1.5em;/* was 0.9 em */
        min-width: 400px;
        border-radius: 5px 5px 0 0;
        overflow: hidden;
        box-shadow: 0 0 20px rgba(0, 0, 0, 0.15);
    }

    .content-table thead tr {
        background-color: #0ea0c2;
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
        border-bottom: 2px solid #0ea0c2;
    }

    .content-table tbody tr.active-row {
        font-weight: bold;
        color: #0ea0c2;
    }
/*    -------------------------------------------------*/
/*    to nav bar*/

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

/*    submit button*/
    #submit-button {
        padding: 5px;
        background: #0066A2;
        color: white;
        border-style: outset;
        border-color: #0066A2;
        font: bold 18px arial,sans-serif;
        text-shadow: none;

        cursor: pointer;
        box-shadow: 0 2px #999;
    }

    #submit-button:hover {background-color: #3e8e41}

    #submit-button:active {
        background-color: #3e8e41;
        box-shadow: 0 5px #666;
        transform: translateY(4px);
    }
</style>