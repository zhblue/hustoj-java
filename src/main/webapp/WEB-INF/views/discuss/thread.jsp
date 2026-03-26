<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>${topic.title}</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/hoj.css">
</head>
<body>
<jsp:include page="/WEB-INF/views/header.jsp"/>
<div class="container">
    <h2>${topic.title}</h2>
    <c:forEach var="r" items="${replies}">
        <div class="panel panel-default">
            <div class="panel-heading">${r.authorId} - ${r.time}</div>
            <div class="panel-body">${r.content}</div>
        </div>
    </c:forEach>
    <c:if test="${sessionScope.HUSTOJ_user_id != null}">
        <form method="post" action="${pageContext.request.contextPath}/thread?action=reply">
            <input type="hidden" name="tid" value="${topic.tid}"/>
            <textarea name="content" class="form-control" rows="5" required></textarea>
            <button type="submit" class="btn btn-primary">Reply</button>
        </form>
    </c:if>
</div>
<jsp:include page="/WEB-INF/views/footer.jsp"/>
</body>
</html>
