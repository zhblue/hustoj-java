<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Backup</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/hoj.css">
</head>
<body>
<jsp:include page="/WEB-INF/views/header.jsp"/>
<div class="container">
    <h2>Database Backup</h2>
    <c:if test="${message != null}">
        <div class="alert alert-success">${message}</div>
    </c:if>
    <form method="post">
        <button type="submit" class="btn btn-primary">Generate Backup</button>
    </form>
</div>
<jsp:include page="/WEB-INF/views/footer.jsp"/>
</body>
</html>
