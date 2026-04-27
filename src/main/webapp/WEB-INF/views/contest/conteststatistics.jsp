<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Contest Statistics - ${OJ_NAME}</title>
        <link rel="stylesheet" href="${pageContext.request.contextPath}/css/bootstrap.min.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/bootstrap-icons.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/katex.min.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
</head>
<body>
<jsp:include page="/WEB-INF/views/header.jsp"/>

<div class="container mt-4"><div class="container mt-4">
    <h2>${contest.title} - Statistics</h2>
    <p>Total AC: ${totalAc} | Total Submit: ${totalSubmit}</p>
    <table class="table table-bordered">
        <thead><tr><th>#</th><th>Problem</th><th>Title</th><th>AC</th><th>Submit</th><th>Ratio</th></tr></thead>
        <tbody>
            <c:forEach var="p" items="${problems}" varStatus="status">
                <tr>
                    <td>${status.index}</td>
                    <td>${p.problemId}</td>
                    <td>${p.title}</td>
                    <td>${acCount[status.index]}</td>
                    <td>${submitCount[status.index]}</td>
                    <td>
                        <c:choose>
                            <c:when test="${submitCount[status.index] > 0}">
                                ${String.format("%.1f", acCount[status.index] * 100.0 / submitCount[status.index])}%
                            </c:when>
                            <c:otherwise>--</c:otherwise>
                        </c:choose>
                    </td>
                </tr>
            </c:forEach>
        </tbody>
    </table>
<jsp:include page="/WEB-INF/views/footer.jsp"/>
</body>
</html>
