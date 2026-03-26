<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Add Problem</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/hoj.css">
</head>
<body>
<jsp:include page="/WEB-INF/views/header.jsp"/>
<div class="container">
    <h2>Add Problem</h2>
    <form method="post">
        <div class="form-group"><label>Title</label><input type="text" name="title" class="form-control" required/></div>
        <div class="form-group"><label>Description</label><textarea name="description" class="form-control" rows="5"></textarea></div>
        <div class="form-group"><label>Input</label><textarea name="input" class="form-control" rows="3"></textarea></div>
        <div class="form-group"><label>Output</label><textarea name="output" class="form-control" rows="3"></textarea></div>
        <div class="form-group"><label>Sample Input</label><textarea name="sample_input" class="form-control" rows="3"></textarea></div>
        <div class="form-group"><label>Sample Output</label><textarea name="sample_output" class="form-control" rows="3"></textarea></div>
        <div class="form-group"><label>Hint</label><textarea name="hint" class="form-control" rows="2"></textarea></div>
        <div class="form-group"><label>Source</label><input type="text" name="source" class="form-control"/></div>
        <div class="form-group"><label>Time Limit (s)</label><input type="text" name="time_limit" value="1" class="form-control"/></div>
        <div class="form-group"><label>Memory Limit (KB)</label><input type="text" name="memory_limit" value="256" class="form-control"/></div>
        <button type="submit" class="btn btn-primary">Add</button>
    </form>
</div>
<jsp:include page="/WEB-INF/views/footer.jsp"/>
</body>
</html>
