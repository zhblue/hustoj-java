<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Ranklist - ${OJ_NAME}</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/hoj.css">
</head>
<body>
<jsp:include page="/WEB-INF/views/header.jsp"/>
<div class="container">
    <h2>Ranklist</h2>
    <p>Total: ${total} | AC Users: ${acUsers}</p>
    <table class="table table-striped table-bordered">
        <thead>
            <tr><th>Rank</th><th>User</th><th>Nick</th><th>School</th><th>Solved</th><th>Submit</th><th>Ratio</th></tr>
        </thead>
        <tbody>
            <c:forEach var="u" items="${users}" varStatus="status">
                <tr>
                    <td>${status.index + 1}</td>
                    <td><a href="${pageContext.request.contextPath}/userinfo?uid=${u.userId}">${u.userId}</a></td>
                    <td>${u.nick}</td>
                    <td>${u.school}</td>
                    <td>${u.solved}</td>
                    <td>${u.submit}</td>
                    <td>${u.submit > 0 ? String.format("%.1f", u.solved * 100.0 / u.submit) : 0}%</td>
                </tr>
            </c:forEach>
        </tbody>
    </table>
</div>
<jsp:include page="/WEB-INF/views/footer.jsp"/>
</body>
</html>
