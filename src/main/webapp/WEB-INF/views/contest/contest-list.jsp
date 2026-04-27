<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Contests - ${OJ_NAME}</title>
        <link rel="stylesheet" href="${pageContext.request.contextPath}/css/bootstrap.min.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/bootstrap-icons.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/katex.min.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
</head>
<body>
<jsp:include page="/WEB-INF/views/header.jsp"/>

<div class="container mt-4"><div class="container mt-4">
    <h2>Contests</h2>
    <p>
        <a href="?filter=running" class="btn btn-success">Running</a>
        <a href="?filter=upcoming" class="btn btn-info">Upcoming</a>
        <a href="?filter=recent" class="btn btn-default">Recent</a>
        <a href="?" class="btn btn-primary">All</a>
    </p>
    <table class="table table-striped table-bordered">
        <thead>
            <tr><th>ID</th><th>Title</th><th>Start</th><th>End</th><th>Type</th><th>Status</th></tr>
        </thead>
        <tbody>
            <c:forEach var="c" items="${contests}">
                <tr>
                    <td>${c.contestId}</td>
                    <td><a href="${pageContext.request.contextPath}/contest-detail?cid=${c.contestId}">${c.title}</a></td>
                    <td>${c.startTime}</td>
                    <td>${c.endTime}</td>
                    <td>${c.contestType == 1 ? 'OI' : 'ACM'}</td>
                    <td>
                        <c:choose>
                            <c:when test="${c.startTime > now}">Upcoming</c:when>
                            <c:when test="${c.endTime < now}">Ended</c:when>
                            <c:otherwise>Running</c:otherwise>
                        </c:choose>
                    </td>
                </tr>
            </c:forEach>
        </tbody>
    </table>
<jsp:include page="/WEB-INF/views/footer.jsp"/>
</body>
</html>
