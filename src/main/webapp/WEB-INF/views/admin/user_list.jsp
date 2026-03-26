<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>User List - ${OJ_NAME}</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/hoj.css">
</head>
<body>
<jsp:include page="/WEB-INF/views/header.jsp"/>
<div class="container">
    <h2>Admin - User List</h2>
    <table class="table table-striped">
        <thead>
            <tr><th>UserID</th><th>Nick</th><th>School</th><th>Solved</th><th>Submit</th><th>Defunct</th><th>Action</th></tr>
        </thead>
        <tbody>
            <c:forEach var="u" items="${users}">
                <tr>
                    <td><a href="${pageContext.request.contextPath}/userinfo?uid=${u.userId}">${u.userId}</a></td>
                    <td>${u.nick}</td>
                    <td>${u.school}</td>
                    <td>${u.solved}</td>
                    <td>${u.submit}</td>
                    <td>${u.defunct}</td>
                    <td>
                        <a href="${pageContext.request.contextPath}/admin/user?action=edit&uid=${u.userId}">Edit</a>
                    </td>
                </tr>
            </c:forEach>
        </tbody>
    </table>
</div>
<jsp:include page="/WEB-INF/views/footer.jsp"/>
</body>
</html>
