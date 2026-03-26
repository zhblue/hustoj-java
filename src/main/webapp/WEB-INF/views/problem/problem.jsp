<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>${problem.title} - ${OJ_NAME}</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/hoj.css">
</head>
<body>
<jsp:include page="/WEB-INF/views/header.jsp"/>
<div class="container">
    <h2>Problem ${problem.problemId}: ${problem.title}</h2>
    <table class="table">
        <tr><th>Time Limit</th><td>${problem.timeLimit} Second(s)</td></tr>
        <tr><th>Memory Limit</th><td>${problem.memoryLimit} KB</td></tr>
        <tr><th>Source</th><td>${problem.source}</td></tr>
    </table>

    <div class="panel panel-info">
        <div class="panel-heading">Description</div>
        <div class="panel-body">${problem.description}</div>
    </div>

    <div class="panel panel-info">
        <div class="panel-heading">Input</div>
        <div class="panel-body">${problem.input}</div>
    </div>

    <div class="panel panel-info">
        <div class="panel-heading">Output</div>
        <div class="panel-body">${problem.output}</div>
    </div>

    <div class="panel panel-default">
        <div class="panel-heading">Sample Input</div>
        <pre class="sample-output">${problem.sampleInput}</pre>
    </div>

    <div class="panel panel-default">
        <div class="panel-heading">Sample Output</div>
        <pre class="sample-output">${problem.sampleOutput}</pre>
    </div>

    <c:if test="${problem.hint != null && problem.hint != ''}">
        <div class="panel panel-warning">
            <div class="panel-heading">Hint</div>
            <div class="panel-body">${problem.hint}</div>
        </div>
    </c:if>

    <c:if test="${sessionScope.HUSTOJ_user_id != null}">
        <a href="${pageContext.request.contextPath}/submit?id=${problem.problemId}" class="btn btn-success">Submit</a>
    </c:if>
</div>
<jsp:include page="/WEB-INF/views/footer.jsp"/>
</body>
</html>
