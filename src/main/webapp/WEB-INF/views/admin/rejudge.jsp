<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Rejudge</title>
        <link rel="stylesheet" href="${pageContext.request.contextPath}/css/bootstrap.min.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/bootstrap-icons.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/katex.min.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
</head>
<body>
<jsp:include page="/WEB-INF/views/header.jsp"/>

<div class="container mt-4"><div class="container mt-4">
    <h2>Rejudge Solutions</h2>
    <form method="post">
        <div class="mb-3">
            <label>Solution IDs (comma separated)</label>
            <input type="text" name="solution_id" class="form-control"/>
        </div>
        <div class="mb-3">
            <label>Problem ID</label>
            <input type="text" name="problem_id" class="form-control"/>
        </div>
        <div class="mb-3">
            <label>Contest ID</label>
            <input type="text" name="contest_id" class="form-control"/>
        </div>
        <button type="submit" class="btn btn-warning">Rejudge</button>
    </form>
    <c:if test="${message != null}">
        <div class="alert alert-info">${message}</div>
    </c:if>
<jsp:include page="/WEB-INF/views/footer.jsp"/>
</body>
</html>
