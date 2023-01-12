<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Login</title>
    <meta charset="UTF-8">
</head>
<body>
<nav class="nav-bar-cell1-rev">
    <a href="" class="profile">Про сайт</a>
    <a href="" class="profile">Ще щось</a>
</nav>
<div class="container">
    <div class="box">
        <h1>Books</h1>
        <table class="content-table">
            <thead>
                <tr>
                    <th>Title</th>
                    <th>Author</th>
                    <th>ISBN</th>
                    <th>Number of Copies</th>
                    <th>Date of Publication</th>
                </tr>
            </thead>
            <c:forEach var="book" items="${bookList}" varStatus="status">
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
                </tr>
            </c:forEach>
        </table>
    </div>
</div>
</body>
</html>

<style>
    * {
        /* Change your font family */
        font-family: sans-serif;
    }

    .content-table {
        border-collapse: collapse;
        margin: 25px 0;
        font-size: 0.9em;
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
</style>