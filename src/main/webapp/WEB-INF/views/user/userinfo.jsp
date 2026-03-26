<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>User Info - ${OJ_NAME}</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/hoj.css">
</head>
<body>
<jsp:include page="/WEB-INF/views/header.jsp"/>
<div class="container">
    <h2>User: ${user.userId}</h2>
    <table class="table">
        <tr><th>Nick</th><td>${user.nick}</td></tr>
        <tr><th>School</th><td>${user.school}</td></tr>
        <tr><th>Email</th><td>${user.email}</td></tr>
        <tr><th>Solved</th><td>${user.solved}</td></tr>
        <tr><th>Submit</th><td>${user.submit}</td></tr>
        <tr><th>Ratio</th><td>${user.submit > 0 ? (user.solved * 100 / user.submit) : 0}%</td></tr>
        <tr><th>Reg Time</th><td>${user.regTime}</td></tr>
    </table>
    <c:if test="${sessionScope.HUSTOJ_user_id == user.userId || sessionScope.HUSTOJ_administrator != null}">
        <a href="${pageContext.request.contextPath}/modifypassword" class="btn btn-primary">Change Password</a>
    </c:if>
</div>
<jsp:include page="/WEB-INF/views/footer.jsp"/>
</body>
</html>
