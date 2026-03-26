<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Problem List - ${OJ_NAME}</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/hoj.css">
</head>
<body>
<jsp:include page="/WEB-INF/views/header.jsp"/>
<div class="container">
    <h2>Admin - Problem List</h2>
    <p><a href="${pageContext.request.contextPath}/admin/problem-add" class="btn btn-primary">Add Problem</a></p>
    <table class="table table-striped">
        <thead>
            <tr><th>ID</th><th>Title</th><th>AC/Submit</th><th>Defunct</th><th>Action</th></tr>
        </thead>
        <tbody>
            <c:forEach var="p" items="${problems}">
                <tr>
                    <td>${p.problemId}</td>
                    <td>${p.title}</td>
                    <td>${p.accepted}/${p.submit}</td>
                    <td>${p.defunct}</td>
                    <td>
                        <a href="${pageContext.request.contextPath}/admin/problem-edit?id=${p.problemId}">Edit</a>
                        <a href="${pageContext.request.contextPath}/admin/problem-del?id=${p.problemId}">Delete</a>
                    </td>
                </tr>
            </c:forEach>
        </tbody>
    </table>
</div>
<jsp:include page="/WEB-INF/views/footer.jsp"/>
</body>
</html>
