<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Discuss</title>
        <link rel="stylesheet" href="${pageContext.request.contextPath}/css/bootstrap.min.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/bootstrap-icons.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/katex.min.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
</head>
<body>
<jsp:include page="/WEB-INF/views/header.jsp"/>

<div class="container mt-4"><div class="container mt-4">
    <h2>Discussion</h2>
    <table class="table table-striped">
        <thead><tr><th>Topic</th><th>Author</th><th>Problem</th></tr></thead>
        <tbody>
            <c:forEach var="t" items="${topics}">
                <tr>
                    <td><a href="${pageContext.request.contextPath}/thread?tid=${t.tid}">${t.title}</a></td>
                    <td>${t.authorId}</td>
                    <td><a href="${pageContext.request.contextPath}/problem?id=${t.pid}">${t.pid}</a></td>
                </tr>
            </c:forEach>
        </tbody>
    </table>
<jsp:include page="/WEB-INF/views/footer.jsp"/>
</body>
</html>
