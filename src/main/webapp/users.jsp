<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
  <title>Users</title>
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
    <li><a href="front?command=books">Catalog</a></li>
    <c:choose>
      <c:when test="${userRole == 'user'}">
        <li><a href="front?command=my_books">Ordered books</a> </li>
      </c:when>
      <c:when test="${userRole == 'librarian'}">
        <li><a href="front?command=show_orders">Readers' orders</a></li>
        <li><a class="active" href="front?command=show_users">Users</a></li>
      </c:when>
      <c:when test="${userRole == 'admin'}">
        <li><a href="new_book.jsp">Create book</a></li>
        <li><a href="new_librarian.jsp">Create Librarian</a></li>
        <li><a href="front?command=show_librarians">Librarians</a></li>
        <li><a class="active" href="front?command=show_users">Users</a></li>
      </c:when>
    </c:choose>
    <li style="float:right"><a href="front?command=logout">Log Out</a></li>
    <li style="float:right" ><a href="front?command=profile">Profile</a></li>
  </ul>
</nav>
<div class="container">
  <h1>Users</h1>
  <div class="box">
    <table class="content-table">
      <thead>
      <tr>
        <th>Login</th>
        <th>Full name</th>
        <th>Email</th>
        <th>Phone</th>
        <th>Fined status</th>
        <th>Role</th>
        <th>Subscription</th>
        <c:if test="${userRole == 'admin'}" var="isAdmin">
          <th>Action</th>
        </c:if>
      </tr>
      </thead>
      <c:forEach var="user" items="${requestScope.usersList}" varStatus="status">
        <tr>
          <td><c:out value="${user.login}"/></td>
          <td><c:out value="${user.fullName}"/></td>
          <td><c:out value="${user.email}"/></td>
          <td><c:out value="${user.phone}"/></td>
          <td><c:out value="${user.finedStatus}"/></td>
          <td><c:out value="${user.role}"/></td>
          <td><c:out value="${user.subscription}"/></td>

          <c:if test="${isAdmin}">
            <td>
              <c:if test="${user.status  != 'blocked'}">
                <form name="blockForm" action="front?command=block_user" method="post">
                  <button type="submit" class="delete-button" name="userId" value="${user.id}"
                          onclick="<c:set var="page" value="${param.page}" scope="session"/>">
                    Block
                  </button>
                </form>
              </c:if>

              <c:if test="${user.status != 'active'}">
                <form name="Unblock" action="front?command=unblock_user" method="post">
                  <button type="submit" class="edit-button" name="userId" value="${user.id}"
                          onclick="<c:set var="page" value="${param.page}" scope="session"/>">
                    Unblock
                  </button>
                </form>
              </c:if>
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
        <a href="front?command=show_users&page=${requestScope.currentPage - 1}">Previous</a>
      </c:if>

      <%--        Pages--%>
      <c:forEach var="i" begin="1" end="${requestScope.numOfPages}">
        <c:choose>
          <c:when test="${i == requestScope.currentPage}">
            <a class="active">${i}</a>
          </c:when>
          <c:otherwise>
            <a href="front?command=show_users&page=${i}">${i}</a>
          </c:otherwise>
        </c:choose>
      </c:forEach>
      <%--        Display next page--%>
      <c:if test="${requestScope.currentPage < requestScope.numOfPages}">
        <a href="front?command=show_users&page=${requestScope.currentPage + 1}">Next</a>
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

  * {
    font-family: 'Raleway', sans-serif;
  }
</style>
