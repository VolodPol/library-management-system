<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="ctg" uri="/WEB-INF/tag_tld/custom_tag.tld"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<fmt:setLocale value="${sessionScope.locale}" scope="session"/>
<fmt:setBundle basename="Localization" var="bundle"/>

<fmt:message bundle="${bundle}" key="page.users.title" var="title"/>
<fmt:message bundle="${bundle}" key="page.users.h1_users" var="h1_users"/>
<fmt:message bundle="${bundle}" key="page.users.book_table.login" var="tb_login"/>
<fmt:message bundle="${bundle}" key="page.users.book_table.full_name" var="tb_fullname"/>
<fmt:message bundle="${bundle}" key="page.users.book_table.email" var="tb_email"/>
<fmt:message bundle="${bundle}" key="page.users.book_table.phone" var="tb_phone"/>
<fmt:message bundle="${bundle}" key="page.users.book_table.fine_amount" var="tb_fine_amount"/>
<fmt:message bundle="${bundle}" key="page.users.book_table.role" var="tb_role"/>
<fmt:message bundle="${bundle}" key="page.users.book_table.sub" var="tb_sub"/>
<fmt:message bundle="${bundle}" key="page.users.book_table.action" var="tb_action"/>
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
      <c:choose>
        <c:when test="${userRole == 'librarian'}">
          <li><a href="front?command=show_orders"><fmt:message key="reader_orders"/></a></li>
          <li><a class="active" href="front?command=show_users"><fmt:message key="users"/></a></li>
        </c:when>
        <c:when test="${userRole == 'admin'}">
          <li><a href="new_book.jsp"><fmt:message key="create_book"/></a></li>
          <li><a href="new_librarian.jsp"><fmt:message key="create_librarian"/></a></li>
          <li><a href="front?command=show_librarians"><fmt:message key="librarians"/></a></li>
          <li><a class="active" href="front?command=show_users"><fmt:message key="users"/></a></li>
        </c:when>
      </c:choose>
      <li style="float:right"><a href="front?command=logout"><fmt:message key="logout"/></a></li>
      <li style="float:right" ><a href="front?command=profile"><fmt:message key="profile"/></a></li>
      <ctg:lang locale="${sessionScope.locale}"/>
    </ul>
  </nav>
</fmt:bundle>
<div class="container">
  <h1>${h1_users}</h1>
  <div class="box">
    <table class="content-table">
      <thead>
      <tr>
        <th>${tb_login}</th>
        <th>${tb_fullname}</th>
        <th>${tb_email}</th>
        <th>${tb_phone}</th>
        <th>${tb_fine_amount}</th>
        <th>${tb_role}</th>
        <th>${tb_sub}</th>
        <c:if test="${userRole == 'admin'}" var="isAdmin">
          <th>${tb_action}</th>
        </c:if>
      </tr>
      </thead>
      <c:forEach var="user" items="${requestScope.usersList}" varStatus="status">
        <tr>
          <td><c:out value="${user.login}"/></td>
          <td><c:out value="${user.fullName}"/></td>
          <td><c:out value="${user.email}"/></td>
          <td><c:out value="${user.phone}"/></td>
          <td><c:out value="${user.fineAmount}"/></td>
          <td><c:out value="${user.role}"/></td>
          <td><c:out value="${user.subscription}"/></td>

          <c:if test="${isAdmin}">
            <td>
              <c:if test="${user.status  != 'blocked'}">
                <form name="blockForm" action="front?command=block_user&action=true" method="post">
                  <button type="submit" class="delete-button" name="userId" value="${user.id}"
                          onclick="<c:set var="page" value="${param.page}" scope="session"/>">
                    <fmt:message bundle="${bundle}" key="page.button.block"/>
                  </button>
                </form>
              </c:if>

              <c:if test="${user.status != 'active'}">
                <form name="Unblock" action="front?command=block_user&action=false" method="post">
                  <button type="submit" class="edit-button" name="userId" value="${user.id}"
                          onclick="<c:set var="page" value="${param.page}" scope="session"/>">
                    <fmt:message bundle="${bundle}" key="page.button.unblock"/>
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
        <a href="front?command=show_users&page=${requestScope.currentPage - 1}"><fmt:message bundle="${bundle}" key="page.pagination.prev"/></a>
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
        <a href="front?command=show_users&page=${requestScope.currentPage + 1}"><fmt:message bundle="${bundle}" key="page.pagination.next"/></a>
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

  * {
    font-family: 'Raleway', sans-serif;
  }
/*  nav */
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
/*  edit*/
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
/*  delete*/
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

/*  pagination */
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
