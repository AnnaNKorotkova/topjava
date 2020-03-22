<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.time.format.DateTimeFormatter" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>Meals</title>
</head>
<h3><a>Meals</a></h3>
<hr>
<body>

<table border="0" style="height: 18px; width: 50%; border-collapse: collapse;">
    <jsp:useBean id="meals" type="java.util.List" scope="request"/>
    <c:forEach items="${meals}" var="t">
        <jsp:useBean id="t" type="ru.javawebinar.topjava.model.MealTo"/>
        <c:if test="${t.excess}">
        <c:set var="textColor" value="color: red"/>
        </c:if>
        <c:if test="${!t.excess}">
        <c:set var="textColor" value="color: green"/>
        </c:if>
        <tbody style="${textColor}">
        <tr>
            <td>
                    ${t.dateTime.format(DateTimeFormatter.ofPattern('DD.MM.YYYY HH:mm'))}
            </td>
            <td>
                    ${t.description}
            </td>
            <td>
                    ${t.calories}
            </td>

        </tr>
        </tbody>
    </c:forEach>
</table>
</body>
</html>