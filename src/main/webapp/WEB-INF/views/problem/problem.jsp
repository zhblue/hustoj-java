<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>${problem.title} - ${OJ_NAME}</title>
        <link rel="stylesheet" href="${pageContext.request.contextPath}/css/bootstrap.min.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/bootstrap-icons.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/katex.min.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
</head>
<body>
<jsp:include page="/WEB-INF/views/header.jsp"/>

<div class="container mt-4"><div class="container mt-4">
    <h2>Problem ${problem.problemId}: ${problem.title}</h2>
    <table class="table">
        <tr><th>Time Limit</th><td>${problem.timeLimit} Second(s)</td></tr>
        <tr><th>Memory Limit</th><td>${problem.memoryLimit} KB</td></tr>
        <tr><th>Source</th><td>${problem.source}</td></tr>
    </table>

    <div class="card border-info">
        <div class="card-header">Description</div>
        <div class="card-body">${problem.description}</div>
    </div>

    <div class="card border-info">
        <div class="card-header">Input</div>
        <div class="card-body">${problem.input}</div>
    </div>

    <div class="card border-info">
        <div class="card-header">Output</div>
        <div class="card-body">${problem.output}</div>
    </div>

    <div class="card">
        <div class="card-header">Sample Input</div>
        <pre class="sample-output">${problem.sampleInput}</pre>
    </div>

    <div class="card">
        <div class="card-header">Sample Output</div>
        <pre class="sample-output">${problem.sampleOutput}</pre>
    </div>

    <c:if test="${problem.hint != null && problem.hint != ''}">
        <div class="card border-warning">
            <div class="card-header">Hint</div>
            <div class="card-body">${problem.hint}</div>
        </div>
    </c:if>

    <c:if test="${sessionScope.HUSTOJ_user_id != null}">
        <a href="${pageContext.request.contextPath}/submit?id=${problem.problemId}" class="btn btn-success">Submit</a>
    </c:if>
<jsp:include page="/WEB-INF/views/footer.jsp"/>
</body>
</html>
