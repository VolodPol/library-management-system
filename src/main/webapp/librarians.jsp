<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="ctg" uri="/WEB-INF/tag_tld/custom_tag.tld"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<fmt:setLocale value="${sessionScope.locale}" scope="session"/>
<fmt:setBundle basename="Localization" var="bundle"/>

<fmt:message bundle="${bundle}" key="page.librarians.title" var="title"/>
<fmt:message bundle="${bundle}" key="page.librarians.h1_librarians" var="h1_librarians"/>
<fmt:message bundle="${bundle}" key="page.librarians.book_table.login" var="login"/>
<fmt:message bundle="${bundle}" key="page.librarians.book_table.full_name" var="fullname"/>
<fmt:message bundle="${bundle}" key="page.librarians.book_table.email" var="email"/>
<fmt:message bundle="${bundle}" key="page.librarians.book_table.phone" var="phone"/>
<fmt:message bundle="${bundle}" key="page.librarians.book_table.age" var="age"/>
<fmt:message bundle="${bundle}" key="page.librarians.book_table.action" var="action"/>
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
            <li><a href="new_book.jsp"><fmt:message key="create_book"/></a></li>
            <li><a href="new_librarian.jsp"><fmt:message key="create_librarian"/></a></li>
            <li><a class="active" href="front?command=show_librarians"><fmt:message key="librarians"/></a></li>
            <li><a href="front?command=show_users"><fmt:message key="users"/></a></li>
            <li style="float:right"><a href="front?command=logout"><fmt:message key="logout"/></a></li>
            <li style="float:right" ><a href="front?command=profile"><fmt:message key="profile"/></a></li>
            <ctg:lang locale="${sessionScope.locale}"/>
        </ul>
    </nav>
</fmt:bundle>
<div class="container">
    <h1>${h1_librarians}</h1>
    <div class="box">
        <table class="content-table">
            <thead>
            <tr>
                <th>${login}</th>
                <th>${fullname}</th>
                <th>${email}</th>
                <th>${phone}</th>
                <th>${age}</th>
                <th>${action}</th>
            </tr>
            </thead>
            <c:forEach var="librarian" items="${requestScope.librariansList}" varStatus="status">
                <tr>
                    <td><c:out value="${librarian.login}"/></td>
                    <td><c:out value="${librarian.fullName}"/></td>
                    <td><c:out value="${librarian.email}"/></td>
                    <td><c:out value="${librarian.phone}"/></td>
                    <td><c:out value="${librarian.age}"/></td>

                        <td>
                            <form name="deleteForm" action="front?command=delete_librarian" method="post">
                                <button type="submit" class="delete-button" name="id" value="${librarian.id}"
                                onclick="<c:set var="page" value="${param.page}" scope="session"/>">
                                    <fmt:message bundle="${bundle}" key="page.button.delete"/>
                                </button>
                            </form>
                        </td>
                </tr>
            </c:forEach>
        </table>
    </div>

    <div class="center">
        <div class="pagination">
            <%--Display previous page--%>
            <c:if test="${requestScope.currentPage > 1}">
                <a href="front?command=show_librarians&page=${requestScope.currentPage - 1}"><fmt:message bundle="${bundle}" key="page.pagination.prev"/></a>
            </c:if>

            <%--        Pages--%>
            <c:forEach var="i" begin="1" end="${requestScope.numOfPages}">
                <c:choose>
                    <c:when test="${i == requestScope.currentPage}">
                        <a class="active">${i}</a>
                    </c:when>
                    <c:otherwise>
                        <a href="front?command=show_librarians&page=${i}">${i}</a>
                    </c:otherwise>
                </c:choose>
            </c:forEach>
            <%--        Display next page--%>
            <c:if test="${requestScope.currentPage < requestScope.numOfPages}">
                <a href="front?command=show_librarians&page=${requestScope.currentPage + 1}"><fmt:message bundle="${bundle}" key="page.pagination.next"/></a>
            </c:if>
        </div>
    </div>

</div>
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

    /*.content-table tbody tr.active-row {*/
    /*    font-weight: bold;*/
    /*    color: #1e673a;*/
    /*}*/

    * {
        font-family: 'Raleway', sans-serif;
    }

    /*nav */
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
    /*    delete */
    .delete-button {
        padding: 10px;
        background: #a20000;
        color: white;
        border-style: outset;
        border-color: #a20000;
        font: bold 18px arial,sans-serif;
        text-shadow: none;
        cursor: pointer;
        box-shadow: 0 2px #999;
    }

    .delete-button:hover {background-color: #3e8e41}

    .delete-button:active {
        background-color: #3e8e41;
        box-shadow: 0 5px #666;
        transform: translateY(4px);
    }

    /*    pagination */
    .center {
        text-align: center;
    }

    .pagination {
        display: inline-block;
        margin-top: 20px;
    }

    .pagination a {
        color: black;
        float: left;
        padding: 8px 16px;
        text-decoration: none;
        transition: background-color .3s;
        border: 1px solid #ddd;
        margin: 0 4px;
        border-radius:3px;
    }

    .pagination a.active {
        background-color: #1e673a;
        color: white;
        border: 1px solid #1e673a;
        border-radius:3px;
    }

    .pagination a:hover:not(.active) {background-color: #ddd;}
</style>
