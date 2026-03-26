<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>${contest.title} - ${OJ_NAME}</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/hoj.css">
</head>
<body>
<jsp:include page="/WEB-INF/views/header.jsp"/>
<div class="container">
    <h2>${contest.title}</h2>
    <p>Start: ${contest.startTime} | End: ${contest.endTime}</p>
    <p>${contest.description}</p>

    <h3>Problems</h3>
    <table class="table table-striped">
        <thead>
            <tr><th>#</th><th>Problem ID</th><th>Title</th><th>AC / Submit</th></tr>
        </thead>
        <tbody>
            <c:forEach var="p" items="${problems}">
                <tr>
                    <td>${p.num}</td>
                    <td><a href="${pageContext.request.contextPath}/contest/problem?cid=${contest.contestId}&pid=${p.num}">${p.problemId}</a></td>
                    <td><a href="${pageContext.request.contextPath}/contest/problem?cid=${contest.contestId}&pid=${p.num}">${p.title}</a></td>
                    <td>${p.cAccepted} / ${p.cSubmit}</td>
                </tr>
            </c:forEach>
        </tbody>
    </table>
    <a href="${pageContext.request.contextPath}/contestrank?cid=${contest.contestId}" class="btn btn-info">Rank</a>
</div>
<jsp:include page="/WEB-INF/views/footer.jsp"/>
</body>
</html>
