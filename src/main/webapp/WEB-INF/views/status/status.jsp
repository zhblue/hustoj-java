<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Status - ${OJ_NAME}</title>
        <link rel="stylesheet" href="${pageContext.request.contextPath}/css/bootstrap.min.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/bootstrap-icons.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/katex.min.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
</head>
<body>
<jsp:include page="/WEB-INF/views/header.jsp"/>

<div class="container mt-4"><div class="container mt-4">
    <h2>Judge Status</h2>
    <table class="table table-striped table-bordered">
        <thead>
            <tr>
                <th>RunID</th>
                <th>Problem</th>
                <th>User</th>
                <th>Result</th>
                <th>Time</th>
                <th>Memory</th>
                <th>Language</th>
                <th>Code Length</th>
                <th>Submit Time</th>
            </tr>
        </thead>
        <tbody id="status-table">
            <c:forEach var="s" items="${solutions}">
                <tr>
                    <td>${s.solutionId}</td>
                    <td><a href="${pageContext.request.contextPath}/problem?id=${s.problemId}">${s.problemId}</a></td>
                    <td><a href="${pageContext.request.contextPath}/userinfo?uid=${s.userId}">${s.userId}</a></td>
                    <td><span class="label ${s.result == 4 ? 'label-success' : s.result == 11 ? 'label-warning' : 'label-danger'}">${s.result}</span></td>
                    <td>${s.time} MS</td>
                    <td>${s.memory} KB</td>
                    <td>${s.language}</td>
                    <td>${s.codeLength}</td>
                    <td>${s.inDate}</td>
                </tr>
            </c:forEach>
        </tbody>
    </table>
    <nav>
        <ul class="pagination">
            <c:if test="${page > 1}">
                <li><a href="?page=${page - 1}">Prev</a></li>
            </c:if>
            <li><span>Page ${page} / ${pageCount}</span></li>
            <c:if test="${page < pageCount}">
                <li><a href="?page=${page + 1}">Next</a></li>
            </c:if>
        </ul>
    </nav>
<jsp:include page="/WEB-INF/views/footer.jsp"/>
</body>
</html>
