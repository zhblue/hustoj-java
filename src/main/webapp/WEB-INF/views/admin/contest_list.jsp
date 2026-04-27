<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Contest List - ${OJ_NAME}</title>
        <link rel="stylesheet" href="${pageContext.request.contextPath}/css/bootstrap.min.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/bootstrap-icons.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/katex.min.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
</head>
<body>
<jsp:include page="/WEB-INF/views/header.jsp"/>

<div class="container mt-4"><div class="container mt-4">
    <h2>Admin - Contest List</h2>
    <p><a href="${pageContext.request.contextPath}/admin/contest-add" class="btn btn-primary">Add Contest</a></p>
    <table class="table table-striped">
        <thead>
            <tr><th>ID</th><th>Title</th><th>Start</th><th>End</th><th>Type</th><th>Action</th></tr>
        </thead>
        <tbody>
            <c:forEach var="c" items="${contests}">
                <tr>
                    <td>${c.contestId}</td>
                    <td>${c.title}</td>
                    <td>${c.startTime}</td>
                    <td>${c.endTime}</td>
                    <td>${c.contestType}</td>
                    <td>
                        <a href="${pageContext.request.contextPath}/admin/contest-edit?cid=${c.contestId}">Edit</a>
                    </td>
                </tr>
            </c:forEach>
        </tbody>
    </table>
<jsp:include page="/WEB-INF/views/footer.jsp"/>
</body>
</html>
