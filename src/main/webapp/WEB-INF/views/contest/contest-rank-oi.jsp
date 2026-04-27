<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Contest Rank (OI) - ${OJ_NAME}</title>
        <link rel="stylesheet" href="${pageContext.request.contextPath}/css/bootstrap.min.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/bootstrap-icons.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/katex.min.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
</head>
<body>
<jsp:include page="/WEB-INF/views/header.jsp"/>

<div class="container mt-4"><div class="container mt-4">
    <h2>${contest.title} - OI Mode Rank</h2>
    <table class="table table-striped table-bordered">
        <thead>
            <tr>
                <th>Rank</th><th>User</th><th>Nick</th><th>Total Score</th>
            </tr>
        </thead>
        <tbody>
            <c:forEach var="row" items="${rankList}">
                <tr>
                    <td>${row.rank}</td>
                    <td>${row.userId}</td>
                    <td>${row.nick}</td>
                    <td>${row.totalScore}</td>
                </tr>
            </c:forEach>
        </tbody>
    </table>
<jsp:include page="/WEB-INF/views/footer.jsp"/>
</body>
</html>
