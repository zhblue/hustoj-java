<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>New Post</title>
        <link rel="stylesheet" href="${pageContext.request.contextPath}/css/bootstrap.min.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/bootstrap-icons.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/katex.min.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
</head>
<body>
<jsp:include page="/WEB-INF/views/header.jsp"/>

<div class="container mt-4"><div class="container mt-4">
    <h2>New Discussion</h2>
    <form method="post">
        <input type="hidden" name="action" value="submit"/>
        <input type="hidden" name="pid" value="${pid}"/>
        <input type="hidden" name="cid" value="${cid}"/>
        <div class="mb-3">
            <label>Title</label>
            <input type="text" name="title" class="form-control" required/>
        </div>
        <div class="mb-3">
            <label>Content</label>
            <textarea name="content" class="form-control" rows="10" required></textarea>
        </div>
        <button type="submit" class="btn btn-primary">Post</button>
    </form>
<jsp:include page="/WEB-INF/views/footer.jsp"/>
</body>
</html>
