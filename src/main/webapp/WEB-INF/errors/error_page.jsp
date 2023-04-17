<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<fmt:setLocale value="${sessionScope.locale}" scope="session"/>
<fmt:setBundle basename="Localization" var="bundle"/>

<html lang="en">
<head>
  <meta charset="utf-8" />
  <title>Unexpected Error</title>

</head>

<body class="loading">
<div class="error-container">
  <h1>500 : <fmt:message bundle="${bundle}" key="page.error.name"/> <b>:(</b></h1>
  <h2><fmt:message bundle="${bundle}" key="page.error.advice"/> <a href="${pageContext.request.contextPath}/index.jsp"><fmt:message bundle="${bundle}" key="page.error.homepage"/></a>.</h2>
</div>
</body>
</html>

<style>
  html, body {
    background-image: url(https://static.vecteezy.com/system/resources/previews/007/872/980/original/flat-illustration-500-internal-server-error-concept-can-t-connect-to-server-or-internet-website-error-500-can-be-used-for-web-landing-page-animation-promotion-etc-vector.jpg);

    background-position: center;
    background-repeat: no-repeat;
    background-size: contain;
  }

  body {
    margin: 0;
    display: flex;
    flex-direction: column;
  }

  .error-container {
    flex-grow: 1;
    display: flex;
    flex-direction: column;
    justify-content: flex-start;
    align-items: center;
    padding: 50px;
    box-sizing: border-box;
  }
</style>
