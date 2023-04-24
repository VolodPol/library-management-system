<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="ctg" uri="/WEB-INF/tag_tld/custom_tag.tld"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<fmt:setLocale value="${sessionScope.locale}" scope="session"/>
<fmt:setBundle basename="Localization" var="bundle"/>
<html>
<head>
  <title><fmt:message bundle="${bundle}" key="page.create_book.title"/></title>
  <link rel="icon" href="https://img.icons8.com/external-flaticons-flat-flat-icons/64/null/external-library-university-flaticons-flat-flat-icons-3.png"/>
</head>
<body>

<fmt:bundle basename="Localization" prefix="page.navigation.">
  <nav class="navUl">
    <ul>
      <li><a href="front?command=books"><fmt:message key="catalog"/></a></li>
      <li><a class="active" href="new_book.jsp"><fmt:message key="create_book"/></a></li>
      <li><a href="new_librarian.jsp"><fmt:message key="create_librarian"/></a></li>
      <li><a href="front?command=show_librarians"><fmt:message key="librarians"/></a></li>
      <li><a href="front?command=show_users"><fmt:message key="users"/></a></li>
      <li style="float:right"><a href="front?command=logout"><fmt:message key="logout"/></a></li>
      <li style="float:right" ><a href="front?command=profile"><fmt:message key="profile"/></a></li>
      <ctg:lang locale="${sessionScope.locale}"/>
    </ul>
  </nav>
</fmt:bundle>


<h1 style="text-align: center"><fmt:message bundle="${bundle}" key="page.create_book.h1_edit_book_info"/></h1>
<div id="form-container">
  <form name="newBookForm" action="front" method="post">

    <input type="hidden" name="command" value="create_book">

    <h2><fmt:message bundle="${bundle}" key="page.create_book.h2_title"/></h2>
    <div class="input-div">
      <label>
        <input class="input" name="title" type="text"/>
      </label>
    </div>

    <h2><fmt:message bundle="${bundle}" key="page.create_book.h2_author"/></h2>
    <div class="input-div">
      <label>
        <input class="input" name="author" type="text"/>
      </label>
    </div>

    <h2><fmt:message bundle="${bundle}" key="page.create_book.h2_isbn"/></h2>
    <div class="input-div">
      <label>
        <input class="input" name="isbn" type="text"/>
      </label>
    </div>

    <h2><fmt:message bundle="${bundle}" key="page.create_book.h2_num_of_copies"/></h2>
    <div class="input-div">
      <label>
        <input class="input" name="copies_number" type="text"/>
      </label>
    </div>

    <h2><fmt:message bundle="${bundle}" key="page.create_book.h2_date"/></h2>
    <div class="input-div">
      <label>
        <input class="input" name="date" type="date"/>
      </label>
    </div>

    <h2><fmt:message bundle="${bundle}" key="page.create_book.h2_publisher"/></h2>
    <div class="input-div">
      <label>
        <input class="input" name="publisher" type="text">
      </label>
    </div>

    <c:set var="error" value="${requestScope.error}" scope="page"/>
    <c:if test="${not empty error}">
      <div style="margin-top: 10px">
        <p><fmt:message bundle="${bundle}" key="${error}"/></p>
      </div>

    </c:if>

    <c:if test="${error == 'page.error_message.publication_error'}">
      <div class="input-div">
        <h3><fmt:message bundle="${bundle}" key="${error}"/></h3>
        <a href="new_publisher.jsp"><fmt:message bundle="${bundle}" key="page.create_book.message.link"/></a>
      </div>
    </c:if>

    <div class="input-div"  style="padding: 20px">
      <input class="submit-button" type="submit" value="<fmt:message bundle="${bundle}" key="page.button.create_book"/>">
    </div>
  </form>
</div>
<jsp:include page="elements/footer.jspf"/>
</body>
</html>
<style>
  <%--  Form styles  --%>
  #form-container {
    display: block;
    margin: auto 50px;
  }

  .input-div {
    padding-left: 20px;
  }

  .input {
    font-size: x-large;
  }

  * {
    font-family: 'Raleway', sans-serif;
  }

/*  nav bar*/
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
/*  submit */
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
