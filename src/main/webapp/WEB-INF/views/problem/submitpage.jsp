<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Submit - ${OJ_NAME}</title>
        <link rel="stylesheet" href="${pageContext.request.contextPath}/css/bootstrap.min.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/bootstrap-icons.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/katex.min.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
</head>
<body>
<jsp:include page="/WEB-INF/views/header.jsp"/>

<div class="container mt-4"><div class="container mt-4">
    <h2>Submit Problem ${problem.problemId}</h2>
    <form method="post" action="${pageContext.request.contextPath}/submit">
        <input type="hidden" name="action" value="submit"/>
        <input type="hidden" name="id" value="${problem.problemId}"/>
        <div class="mb-3">
            <label>Language</label>
            <select name="language" class="form-control">
                <option value="0">C</option>
                <option value="1">C++</option>
                <option value="2">Pascal</option>
                <option value="3">Java</option>
                <option value="4">Ruby</option>
                <option value="5">Bash</option>
                <option value="6">Python</option>
                <option value="7">PHP</option>
                <option value="8">Perl</option>
                <option value="9">C#</option>
            </select>
        </div>
        <div class="mb-3">
            <label>Source Code</label>
            <textarea name="source" class="form-control" rows="20" required></textarea>
        </div>
        <button type="submit" class="btn btn-primary">Submit</button>
    </form>
<jsp:include page="/WEB-INF/views/footer.jsp"/>
</body>
</html>
