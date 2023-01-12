<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Login</title>
    <meta charset="UTF-8">
</head>
<body>
<div class="container">
    <div class="box">
        <h1>Books</h1>
        <table>
            <c:forEach var="book" items="${bookList}" varStatus="status">
                <tr>
                    <td>
                        <c:out value="${book.tiitle}"/>
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
