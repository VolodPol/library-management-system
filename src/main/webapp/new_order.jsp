<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="ctg" uri="/WEB-INF/tag_tld/custom_tag.tld"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<fmt:setLocale value="${sessionScope.locale}" scope="session"/>
<fmt:setBundle basename="Localization" var="bundle"/>
<html>
<head>
    <title><fmt:message bundle="${bundle}" key="page.new_order.title"/></title>
</head>
<body>

<fmt:bundle basename="Localization" prefix="page.navigation.">
    <nav class="navUl">
        <ul>
            <li><a href="front?command=books"><fmt:message key="catalog"/></a></li>
            <li><a href="front?command=my_books"><fmt:message key="ordered_books"/></a> </li>
            <li style="float:right"><a href="front?command=logout"><fmt:message key="logout"/></a></li>
            <li style="float:right" ><a href="front?command=profile"><fmt:message key="profile"/></a></li>
            <ctg:lang locale="${sessionScope.locale}"/>
        </ul>
    </nav>
</fmt:bundle>


<h1 style="text-align: center"><fmt:message bundle="${bundle}" key="page.new_order.h1_create_order"/></h1>
<div id="form-container">
    <form name="newOrderForm" action="front?isbn=${param.isbn}" method="post"><%-- was: action="front?isbn=${requestScope.isbn}" --%>

        <input type="hidden" name="command" value="order">

        <h2><fmt:message bundle="${bundle}" key="page.new_order.h2_reg_time"/></h2>
        <div class="input-div">
            <label>
                <input class="datetime-input" name="startTime" type="datetime-local"/>
            </label>
        </div>

        <h2><fmt:message bundle="${bundle}" key="page.new_order.h2_bring_time"/></h2>
        <div class="input-div">
            <label>
                <input class="datetime-input" name="endTime" type="datetime-local"/>
            </label>
        </div>

        <c:if test="${not empty requestScope.error}">
            <h3 style="color: red"><fmt:message bundle="${bundle}" key="${requestScope.error}"/></h3>
            <c:if test="${requestScope.error == 'page.error_message.no_sub'}">
                <a href="#" ><fmt:message bundle="${bundle}" key="page.new_order.message.link"/></a>
            </c:if>
        </c:if>

        <h2><fmt:message bundle="${bundle}" key="page.new_order.h2_type_of_order"/></h2>
        <div class="input-div">
            <label>
                <input type="radio" id="read_room" value="reading_room" name="order_type">
                <label class="radio-label" for="read_room"><fmt:message bundle="${bundle}" key="page.new_order.input_label.reading_room"/></label><br>
                <input type="radio" id="on_sub" value="subscription" name="order_type">
                <label class="radio-label" for="on_sub"><fmt:message bundle="${bundle}" key="page.new_order.input_label.subscription"/></label>
            </label>
        </div>

        <div class="input-div"  style="padding: 20px">
            <input class="submit-button" type="submit" value="<fmt:message bundle="${bundle}" key="page.button.place_order"/>">
        </div>
    </form>
</div>
<jsp:include page="elements/footer.jspf"/>
</body>
</html>
<style>
  /*Form styles  */
    #form-container {
        display: block;
        margin: auto 50px;
    }

    .input-div {
        padding-left: 20px;
    }

    .datetime-input,
    .radio-label {
        font-size: x-large;
    }

    * {
        font-family: 'Raleway', sans-serif;
    }

  <%--    nav bar--%>
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

  /*.active {*/
  /*    background-color: #1e6839;*/
  /*}*/
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
