<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Edit Problem</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/hoj.css">
</head>
<body>
<jsp:include page="/WEB-INF/views/header.jsp"/>
<div class="container">
    <h2>Edit Problem ${problem.problemId}</h2>
    <form method="post">
        <input type="hidden" name="action" value="submit"/>
        <input type="hidden" name="problem_id" value="${problem.problemId}"/>
        <div class="form-group"><label>Title</label><input type="text" name="title" value="${problem.title}" class="form-control" required/></div>
        <div class="form-group"><label>Description</label><textarea name="description" class="form-control" rows="5">${problem.description}</textarea></div>
        <div class="form-group"><label>Input</label><textarea name="input" class="form-control" rows="3">${problem.input}</textarea></div>
        <div class="form-group"><label>Output</label><textarea name="output" class="form-control" rows="3">${problem.output}</textarea></div>
        <div class="form-group"><label>Sample Input</label><textarea name="sample_input" class="form-control" rows="3">${problem.sampleInput}</textarea></div>
        <div class="form-group"><label>Sample Output</label><textarea name="sample_output" class="form-control" rows="3">${problem.sampleOutput}</textarea></div>
        <div class="form-group"><label>Hint</label><textarea name="hint" class="form-control" rows="2">${problem.hint}</textarea></div>
        <div class="form-group"><label>Source</label><input type="text" name="source" value="${problem.source}" class="form-control"/></div>
        <div class="form-group"><label>Time Limit (s)</label><input type="text" name="time_limit" value="${problem.timeLimit}" class="form-control"/></div>
        <div class="form-group"><label>Memory Limit (KB)</label><input type="text" name="memory_limit" value="${problem.memoryLimit}" class="form-control"/></div>
        <div class="form-group"><label>Defunct</label><select name="defunct" class="form-control"><option value="N" ${problem.defunct == 'N' ? 'selected' : ''}>N</option><option value="Y" ${problem.defunct == 'Y' ? 'selected' : ''}>Y</option></select></div>
        <button type="submit" class="btn btn-primary">Save</button>
    </form>
</div>
<jsp:include page="/WEB-INF/views/footer.jsp"/>
</body>
</html>
