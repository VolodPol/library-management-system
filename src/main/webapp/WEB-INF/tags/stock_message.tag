<%@tag language="java" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<%@attribute name="amount" required="true" type="java.lang.Integer"%>
<%@attribute name="message" required="true" type="java.lang.String"%>

<c:choose>
    <c:when test="${amount == 0}">
        <p>${message}</p>
    </c:when>
    <c:otherwise>
        <c:out value="${amount}"/>
    </c:otherwise>
</c:choose>