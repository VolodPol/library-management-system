<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Login</title>
    <link rel="stylesheet" href="css/nav.css">
    <link rel="stylesheet" href="css/pagination.css">
    <link rel="stylesheet" href="css/buttons/edit.css">
    <link rel="stylesheet" href="css/buttons/delete.css">
    <link rel="stylesheet" href="css/buttons/submit-button.css">
</head>
<body>
<c:set var="userRole" value="${sessionScope.role}" scope="page"/>

<nav id="navUl">
    <ul>
        <li><a href="login.jsp">Sign In</a></li>
        <li><a class="active" href="front?command=books">Catalog</a></li>
        <c:choose>
            <c:when test="${sessionScope.role == 'user'}">
                <li><a href="front?command=my_books">Ordered books</a> </li>
            </c:when>
            <c:when test="${sessionScope.role == 'librarian'}">
                <li><a href="front?command=show_orders">Readers' orders</a></li>
            </c:when>
            <c:when test="${sessionScope.role == 'admin'}">
                <li><a href="new_book.jsp">Create book</a></li>
                <li><a href="new_librarian.jsp">Create Librarian</a></li>
                <li><a href="front?command=show_librarians">Librarians</a></li>
            </c:when>
        </c:choose>
        <li><a href="">Contact</a></li>
        <li style="float:right"><a href="front?command=logout">Log Out</a></li>
        <li style="float:right" ><a href="">About</a></li>
    </ul>
</nav>
<div class="container">

    <div class="center">
        <div id="search-box">
            <h3>Looking for a book?</h3>
            <form name="searchForm" action="front" method="get">

                <div class="inside-search">
                    <label for="criteria">Search by: </label>
                    <select name="filter" id="criteria">
                        <option value="title">Title</option>
                        <option value="author">Author</option>
                    </select>
                </div>
                <div class="inside-search">
                    <input type="hidden" name="command" value="find_book">

                    <label>
                        <input type="text" name="text-input" placeholder="Search">
                    </label>
                </div>

                <div class="inside-search">
                    <input class="submit-button" type="submit" value="Find">
                </div>
            </form>
        </div>
    </div>



    <h1>My books</h1>
    <div class="box">
        <table class="content-table">
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
                    <c:if test="${userRole == 'admin'}">
                        <th>Action</th>
                    </c:if>
                </tr>
            </thead>
            <c:forEach var="book" items="${requestScope.bookList}" varStatus="status">
                <tr>
                    <td><c:out value="${book.title}"/></td>
                    <td><c:out value="${book.author}"/></td>
                    <td><c:out value="${book.isbn}"/></td>
                    <td>
                        <c:choose>
                            <c:when test="${book.copiesNumber == 0}">
                                <p>Out of stock</p>
                            </c:when>
                            <c:otherwise>
                                <c:out value="${book.copiesNumber}"/>
                            </c:otherwise>
                        </c:choose>
                    </td>
                    <td><c:out value="${book.dateOfPublication}"/></td>
                    <c:if test="${testResult}">
                        <td>
                            <form name="orderForm" action="new_order.jsp" method="get">
                                <button type="submit" class="submit-button" name="isbn" value="${book.isbn}">
                                    Order
                                </button>
                            </form>
                        </td>
                    </c:if>
                    <c:if test="${sessionScope.role == 'admin'}">
                        <td>
                            <form name="deleteForm" action="front?command=delete_book" method="post">
                                <button type="submit" class="delete-button" name="isbn" value="${book.isbn}">
                                    Delete
                                </button>
                            </form>
                            <form name="editForm" action="edit_book.jsp" method="post">
                                <button type="submit" class="edit-button" name="formerIsbn" value="${book.isbn}">
                                    Edit
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
            <%--Display previous page--%>
            <c:if test="${requestScope.currentPage > 1}">
                <a href="front?command=books&page=${requestScope.currentPage - 1}">Previous</a>
            </c:if>

            <%--        Pages--%>
            <c:forEach var="i" begin="1" end="${requestScope.numOfPages}">
                <c:choose>
                    <c:when test="${i == requestScope.currentPage}">
                        <a class="active">${i}</a>
                    </c:when>
                    <c:otherwise>
                        <a href="front?command=books&page=${i}">${i}</a>
                    </c:otherwise>
                </c:choose>
            </c:forEach>
            <%--        Display next page--%>
            <c:if test="${requestScope.currentPage < requestScope.numOfPages}">
                <a href="front?command=books&page=${requestScope.currentPage + 1}">Next</a>
            </c:if>
        </div>
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

/*    -----------------------------------------*/
/*search styles*/
    #search-box {
        font-size: 1.35em;
    }

    #search-box form {
        display: block;
    }
    .inside-search {
        padding: 10px;
        margin-block: 10px;
    }
</style>