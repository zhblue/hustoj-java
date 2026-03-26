<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Rejudge</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/hoj.css">
</head>
<body>
<jsp:include page="/WEB-INF/views/header.jsp"/>
<div class="container">
    <h2>Rejudge Solutions</h2>
    <form method="post">
        <div class="form-group">
            <label>Solution IDs (comma separated)</label>
            <input type="text" name="solution_id" class="form-control"/>
        </div>
        <div class="form-group">
            <label>Problem ID</label>
            <input type="text" name="problem_id" class="form-control"/>
        </div>
        <div class="form-group">
            <label>Contest ID</label>
            <input type="text" name="contest_id" class="form-control"/>
        </div>
        <button type="submit" class="btn btn-warning">Rejudge</button>
    </form>
    <c:if test="${message != null}">
        <div class="alert alert-info">${message}</div>
    </c:if>
</div>
<jsp:include page="/WEB-INF/views/footer.jsp"/>
</body>
</html>
