<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="ctg" uri="/WEB-INF/tag_tld/custom_tag.tld"%>
<%@ taglib prefix="stock" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<fmt:setLocale value="${sessionScope.locale}" scope="session"/>
<fmt:setBundle basename="Localization" var="bundle"/>

<fmt:message bundle="${bundle}" key="page.main.title" var="title"/>
<fmt:message bundle="${bundle}" key="page.main.search_label" var="search"/>
<fmt:message bundle="${bundle}" key="page.main.search_option.title" var="title_search"/>
<fmt:message bundle="${bundle}" key="page.main.search_option.author" var="author_search"/>
<fmt:message bundle="${bundle}" key="page.main.h1_my_books" var="h1_my_books"/>
<fmt:message bundle="${bundle}" key="page.sorting.sort_by.default" var="defaultType"/>
<fmt:message bundle="${bundle}" key="page.sorting.sort_order.default" var="defaultSorting"/>
<fmt:message bundle="${bundle}" key="page.main.order_by_option.title" var="title_order"/>
<fmt:message bundle="${bundle}" key="page.main.order_by_option.author" var="author_order"/>
<fmt:message bundle="${bundle}" key="page.main.order_by_option.publication" var="publication_order"/>
<fmt:message bundle="${bundle}" key="page.main.order_by_option.date" var="date_order"/>
<fmt:message bundle="${bundle}" key="page.main.order_type_option.asc" var="asc"/>
<fmt:message bundle="${bundle}" key="page.main.order_type_option.desc" var="desc"/>
<fmt:message bundle="${bundle}" key="page.main.book_table.title" var="title_tb"/>
<fmt:message bundle="${bundle}" key="page.main.book_table.author" var="author_tb"/>
<fmt:message bundle="${bundle}" key="page.main.book_table.isbn" var="isbn_tb"/>
<fmt:message bundle="${bundle}" key="page.main.book_table.num_copies" var="copies_tb"/>
<fmt:message bundle="${bundle}" key="page.main.book_table.date" var="date_tb"/>
<fmt:message bundle="${bundle}" key="page.main.book_table.action" var="action_tb"/>
<fmt:message bundle="${bundle}" key="page.main.message.out_of_stock" var="message"/>

<html>
<head>
    <title>${title}</title>
    <link rel="icon" href="https://img.icons8.com/external-flaticons-flat-flat-icons/64/null/external-library-university-flaticons-flat-flat-icons-3.png"/>
</head>
<body>
<c:if test="${empty sessionScope.role}" var="isEmptyRole" scope="page"/>
<c:set var="userRole" value="${sessionScope.role}" scope="page"/>

<fmt:bundle basename="Localization" prefix="page.navigation.">
    <nav class="navUl">
        <ul>
            <c:if test="${isEmptyRole}">
                <li><a href="login.jsp"><fmt:message key="sign_in"/></a></li>
            </c:if>
            <li><a class="active" href="front?command=books"><fmt:message key="catalog"/></a></li>
            <c:choose>
                <c:when test="${sessionScope.role == 'user'}">
                    <li><a href="front?command=my_books"><fmt:message key="ordered_books"/></a> </li>
                </c:when>
                <c:when test="${sessionScope.role == 'librarian'}">
                    <li><a href="front?command=show_orders"><fmt:message key="reader_orders"/></a></li>
                    <li><a href="front?command=show_users"><fmt:message key="users"/></a></li>
                </c:when>
                <c:when test="${sessionScope.role == 'admin'}">
                    <li><a href="new_book.jsp"><fmt:message key="create_book"/></a></li>
                    <li><a href="new_librarian.jsp"><fmt:message key="create_librarian"/></a></li>
                    <li><a href="front?command=show_librarians"><fmt:message key="librarians"/></a></li>
                    <li><a href="front?command=show_users"><fmt:message key="users"/></a></li>
                </c:when>
            </c:choose>
            <c:if test="${not isEmptyRole}">
                <li style="float:right"><a href="front?command=logout"><fmt:message key="logout"/></a></li>
                <li style="float:right" ><a href="front?command=profile"><fmt:message key="profile"/></a></li>
            </c:if>
            <ctg:lang locale="${sessionScope.locale}"/>
        </ul>
    </nav>
</fmt:bundle>
<%--Атрибути для пагінації--%>
<c:set var="page" value="${requestScope.currentPage}" scope="page"/>
<c:set var="sortBy" value="${requestScope.sortBy}" scope="page"/>
<c:set var="sortOrder" value="${requestScope.sortOrder}" scope="page"/>
<c:set var="recordsPerPage" value="${requestScope.recordsPerPage}" scope="page"/>

<div class="container">

    <div class="center">
        <div class="search-box">
            <form name="searchForm" action="front" method="get">
                <div class="inside-search">
                    <label for="criteria">${search}</label>
                    <select name="filter" id="criteria">
                        <option value="title">${title_search}</option>
                        <option value="author">${author_search}</option>
                    </select>
                </div>

                <div class="search-area">
                    <input type="hidden" name="command" value="find_book">
                    <label>
                        <input type="text" name="text-input" placeholder="Search">
                    </label>
                </div>

                <div class="search-area">
                    <input class="submit-button" type="submit" value="<fmt:message bundle="${bundle}" key="page.button.find"/>"/>
                </div>
            </form>
        </div>
    </div>

    <div class="center">
        <h1>${h1_my_books}</h1>
        <div class="search-box">
            <form action="front" method="get" style="font-size: 1.35em">
                <input type="hidden" name="command" value="books"/>
                <div class="inside-filter">
                    <label for="sort-by"></label>
                    <select name="sortBy" id="sort-by">
                        <option value="defaultType">${defaultType}</option>
                        <option value="title">${title_order}</option>
                        <option value="author">${author_order}</option>
                        <option value="publication">${publication_order}</option>
                        <option value="date">${date_order}</option>
                    </select>
                </div>

                <div class="inside-filter">
                    <label for="sort-order"></label>
                    <select name="sortOrder" id="sort-order">
                        <option value="defaultSorting">${defaultSorting}</option>
                        <option value="asc">${asc}</option>
                        <option value="desc">${desc}</option>
                    </select>
                </div>
<%--                Скільки записів відображати              --%>
                <div class="inside-filter">
                    <label>
                        <input type="number" name="recNum" min="1" max="10" step="1" value="${recordsPerPage}">
                    </label>
                </div>

                <div class="inside-filter">
                    <input class="submit-button" type="submit" value="<fmt:message bundle="${bundle}" key="page.button.confirm"/>"/>
                </div>
            </form>
        </div>
    </div>

    <c:if test="${not empty requestScope.errorMessage}">
        <div class="center">
            <h3 style="color: red"><fmt:message bundle="${bundle}" key="${requestScope.errorMessage}"/></h3>
        </div>

    </c:if>

    <div class="box">
        <table class="content-table">
            <thead>
                <tr>
                    <th>${title_tb}</th>
                    <th>${author_tb}</th>
                    <th>${isbn_tb}</th>
                    <th>${copies_tb}</th>
                    <th>${date_tb}</th>
                    <c:if test="${userRole == 'user' || userRole == 'admin'}">
                        <th>${action_tb}</th>
                    </c:if>
                </tr>
            </thead>
            <c:forEach var="book" items="${requestScope.bookList}" varStatus="status">
                <tr>
                    <td><c:out value="${book.title}"/></td>
                    <td><c:out value="${book.author}"/></td>
                    <td><c:out value="${book.isbn}"/></td>
                    <td>
                        <stock:stock_message amount="${book.copiesNumber}" message="${message}"/>
                    </td>
                    <td><c:out value="${book.dateOfPublication}"/></td>
                    <c:if test="${userRole == 'user'}">
                        <td>
                            <form name="orderForm" action="new_order.jsp" method="get">
                                <button type="submit" class="submit-button" name="isbn" value="${book.isbn}">
                                    <fmt:message bundle="${bundle}" key="page.button.order"/>
                                </button>
                            </form>
                        </td>
                    </c:if>
                    <c:if test="${userRole == 'admin'}">
                        <td>
                            <form name="deleteForm" action="front?command=delete_book" method="post">
                                <button type="submit" class="delete-button" name="isbn" value="${book.isbn}">
                                    <fmt:message bundle="${bundle}" key="page.button.delete"/>
                                </button>
                            </form>
                            <form name="editForm" action="edit_book.jsp" method="post">
                                <button type="submit" class="edit-button" name="formerIsbn" value="${book.isbn}">
                                    <fmt:message bundle="${bundle}" key="page.button.edit"/>
                                </button>
                            </form>
                        </td>
                    </c:if>
                </tr>
            </c:forEach>
        </table>
    </div>

    <div class="center">
        <div class="pagination">
            <%-- Попередня сторінка --%>
            <c:if test="${page > 1}">
                <a href="front?command=books&page=${page - 1}&sortBy=${sortBy}&sortOrder=${sortOrder}&recNum=${recordsPerPage}">
                    <fmt:message bundle="${bundle}" key="page.pagination.prev"/>
                </a>
            </c:if>

            <%--        Усі сторінки --%>
            <c:forEach var="i" begin="1" end="${requestScope.numOfPages}">
                <c:choose>
                    <c:when test="${i == page}">
                        <a class="active">${i}</a>
                    </c:when>
                    <c:otherwise>
                        <a href="front?command=books&page=${i}&sortBy=${sortBy}&sortOrder=${sortOrder}&recNum=${recordsPerPage}">
                                ${i}
                        </a>
                    </c:otherwise>
                </c:choose>
            </c:forEach>
            <%--        Наступна сторінка--%>
            <c:if test="${page < requestScope.numOfPages}">
                <a href="front?command=books&page=${page + 1}&sortBy=${sortBy}&sortOrder=${sortOrder}&recNum=${recordsPerPage}">
                    <fmt:message bundle="${bundle}" key="page.pagination.next"/>
                </a>
            </c:if>
        </div>
    </div>
    <jsp:include page="elements/footer.jspf"/>
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

    .content-table {
        border-collapse: collapse;
        margin: 5px 0;/*                                                         */
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

/*    -----------------------------------------*/
/*search styles*/
    .search-box {
        font-size: 1.35em;
        text-align: center;/* */
    }

    .search-box h3{
        margin: 5px 0;
    }

    .search-box form {
        display: block;
        align-items: center;
    }
    .inside-search {
        font-size: 1.25em;
        padding: 5px;
        margin-block: 5px;
    }
    /* */
    .search-area {
        display: inline-block;
    }

    .inside-filter {
        padding: 10px 20px;
        display: inline-block;
    }
    /* */

    .search-box select {
        font-size: 0.8em;
    }

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
/*    edit*/
    .edit-button {
        padding: 10px;
        background: #1fc59b;
        color: white;
        border-style: outset;
        border-color: #1fc59b;
        font: bold 18px arial,sans-serif;
        text-shadow: none;
        cursor: pointer;
        box-shadow: 0 2px #999;
    }

    .edit-button:hover {background-color: #3e8e41}

    .edit-button:active {
        background-color: #3e8e41;
        box-shadow: 0 5px #666;
        transform: translateY(4px);
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
/*    input number*/
    input[type='number']{
        width: 40px;
        height: 30px;
    }
</style>