<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.time.format.DateTimeFormatter" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="C" uri="http://java.sun.com/jsp/jstl/core" %>
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
        <c:set var="textColor" value="${t.excess ? 'color: red' : 'color: green'}"/>
        <tbody style="${textColor}">
        <tr>
            <td>
                    ${t.dateTime.format(DateTimeFormatter.ofPattern('dd.MM.YYYY HH:mm'))}
            </td>
            <td>
                    ${t.description}
            </td>
            <td>
                    ${t.calories}
            </td>
            <td>
                <button type="button"><a href="meals?uuid=${t.uuid}&action=delete">Delete</a></button>
            </td>
            <td>
                <button type="button"><a href="meals?uuid=${t.uuid}&action=edit">Edit</a></button>
            </td>
        </tr>
        </tbody>
    </c:forEach>
</table>
<c:if test="${meal != null}">
    <jsp:useBean id="meal" type="ru.javawebinar.topjava.model.Meal" scope="request"/>
    <form id="form" method="post" action="meals" enctype="application/x-www-form-urlencoded">
        <input type="hidden" name="uuid" value="${meal.uuid}">
        <tr>
            <td>
                <input type="text" style="text-align: center" id="date" name="date" size="15" required
                       value="${meal.dateTime.format(DateTimeFormatter.ofPattern('dd.MM.YYYY HH:mm'))}"
                       placeholder="DD-MM-YYYY-HH-mm">
            </td>
            <td>
                <input type="text" style="text-align: center" id="desc" name="desc" size="33" required
                       value="${meal.description}" placeholder="Description">
            </td>
            <td>
                <input type="text" style="text-align: center" id="cal" name="cal" size="8" required
                       value="${meal.calories}" placeholder="5< cal <5000">
            </td>
        </tr>
        <button type="submit" class="confirmButton">Сохранить</button>
        <button onclick="window.history.back()">Отменить</button>
    </form>
</c:if>
<c:if test="${meal == null}">
    <a href="meals?action=save">
        <button type="submit">Создать новую запись</button>
    </a>
</c:if>
</body>
</html>