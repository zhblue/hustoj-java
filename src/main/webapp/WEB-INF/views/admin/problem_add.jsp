<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Add Problem</title>
        <link rel="stylesheet" href="${pageContext.request.contextPath}/css/bootstrap.min.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/bootstrap-icons.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/katex.min.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
</head>
<body>
<jsp:include page="/WEB-INF/views/header.jsp"/>

<div class="container mt-4"><div class="container mt-4">
    <h2>Add Problem</h2>
    <form method="post">
        <div class="mb-3"><label>Title</label><input type="text" name="title" class="form-control" required/></div>
        <div class="mb-3"><label>Description</label><textarea name="description" class="form-control" rows="5"></textarea></div>
        <div class="mb-3"><label>Input</label><textarea name="input" class="form-control" rows="3"></textarea></div>
        <div class="mb-3"><label>Output</label><textarea name="output" class="form-control" rows="3"></textarea></div>
        <div class="mb-3"><label>Sample Input</label><textarea name="sample_input" class="form-control" rows="3"></textarea></div>
        <div class="mb-3"><label>Sample Output</label><textarea name="sample_output" class="form-control" rows="3"></textarea></div>
        <div class="mb-3"><label>Hint</label><textarea name="hint" class="form-control" rows="2"></textarea></div>
        <div class="mb-3"><label>Source</label><input type="text" name="source" class="form-control"/></div>
        <div class="mb-3"><label>Time Limit (s)</label><input type="text" name="time_limit" value="1" class="form-control"/></div>
        <div class="mb-3"><label>Memory Limit (KB)</label><input type="text" name="memory_limit" value="256" class="form-control"/></div>
        <button type="submit" class="btn btn-primary">Add</button>
    </form>
<jsp:include page="/WEB-INF/views/footer.jsp"/>
</body>
</html>
