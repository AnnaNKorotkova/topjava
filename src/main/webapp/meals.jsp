<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://topjava.javawebinar.ru/functions" %>
<%--<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>--%>
<html>
<head>
    <title>Meal list</title>
    <style>
        .normal {
            color: green;
        }

        .excess {
            color: red;
        }
    </style>
</head>
<body>
<section>
    <h3><a href="index.html">Home</a></h3>
    <hr/>
    <h2>Meals</h2>
    <form id="form" method="post" action="meals?action=filter" enctype="application/x-www-form-urlencoded">
        <div class="row">
            <table>
                <tr>
                    <td>
                        <div class="offset-1 col-2">
                            <label for="startDate">От даты</label>
                            <input type="date" class="form-control" name="startDate" id="startDate" autocomplete="off">
                        </div>
                    </td>
                    <td>
                        <div class="col-2">
                            <label for="endDate">До даты</label>
                            <input type="date" class="form-control" name="endDate" id="endDate" autocomplete="off">
                        </div>
                    </td>
                </tr>

                <tr>
                    <td>
                        <div class="offset-2 col-2">
                            <label for="startTime">От времени</label>
                            <input type="time" class="form-control" name="startTime" id="startTime" autocomplete="off">
                        </div>
                    </td>
                    <td>
                        <div class="col-2">
                            <label for="endTime">До времени</label>
                            <input type="time" class="form-control" name="endTime" id="endTime" autocomplete="off">
                        </div>
                    </td>
                </tr>
            </table>
            <button type="submit" class="confirmButton">Фильтр</button>
        </div>
    </form>


    <a href="meals?action=create">Add Meal</a>
    <br><br>
    <table border="1" cellpadding="8" cellspacing="0">
        <thead>
        <tr>
            <th>Date</th>
            <th>Description</th>
            <th>Calories</th>
            <th></th>
            <th></th>
        </tr>
        </thead>
        <c:forEach items="${meals}" var="meal">
            <jsp:useBean id="meal" type="ru.javawebinar.topjava.to.MealTo"/>
            <tr class="${meal.excess ? 'excess' : 'normal'}">
                <td>
                        <%--${meal.dateTime.toLocalDate()} ${meal.dateTime.toLocalTime()}--%>
                        <%--<%=TimeUtil.toString(meal.getDateTime())%>--%>
                        <%--${fn:replace(meal.dateTime, 'T', ' ')}--%>
                        ${fn:formatDateTime(meal.dateTime)}
                </td>
                <td>${meal.description}</td>
                <td>${meal.calories}</td>
                <td><a href="meals?action=update&id=${meal.id}">Update</a></td>
                <td><a href="meals?action=delete&id=${meal.id}">Delete</a></td>
            </tr>
        </c:forEach>
    </table>
</section>
</body>
</html>