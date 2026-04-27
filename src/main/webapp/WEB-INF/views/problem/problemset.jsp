<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Problem Set - ${OJ_NAME}</title>
        <link rel="stylesheet" href="${pageContext.request.contextPath}/css/bootstrap.min.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/bootstrap-icons.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/katex.min.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
</head>
<body>
<jsp:include page="/WEB-INF/views/header.jsp"/>

<div class="container mt-4"><div class="container mt-4">
    <h2>Problem Set</h2>
    <form method="get" class="form-inline mb-3">
        <input type="text" name="search" value="${keyword}" class="form-control" placeholder="Search..."/>
        <button type="submit" class="btn btn-default">Search</button>
    </form>
    <table class="table table-striped table-bordered">
        <thead>
            <tr>
                <th>ID</th>
                <th>Title</th>
                <th>Ratio</th>
                <th>AC / Total</th>
                <th>Source</th>
            </tr>
        </thead>
        <tbody>
            <c:forEach var="p" items="${problems}">
                <tr>
                    <td><a href="${pageContext.request.contextPath}/problem?id=${p.problemId}">${p.problemId}</a></td>
                    <td><a href="${pageContext.request.contextPath}/problem?id=${p.problemId}">${p.title}</a></td>
                    <td>
                        <c:choose>
                            <c:when test="${p.submit > 0}">
                                ${String.format("%.1f", p.accepted * 100.0 / p.submit)}%
                            </c:when>
                            <c:otherwise>--</c:otherwise>
                        </c:choose>
                    </td>
                    <td>${p.accepted} / ${p.submit}</td>
                    <td>${p.source}</td>
                </tr>
            </c:forEach>
        </tbody>
    </table>
    <nav>
        <ul class="pagination">
            <c:if test="${page > 1}">
                <li><a href="?page=${page - 1}&search=${keyword}">Prev</a></li>
            </c:if>
            <li><span>Page ${page} / ${pageCount}</span></li>
            <c:if test="${page < pageCount}">
                <li><a href="?page=${page + 1}&search=${keyword}">Next</a></li>
            </c:if>
        </ul>
    </nav>
<jsp:include page="/WEB-INF/views/footer.jsp"/>
</body>
</html>
