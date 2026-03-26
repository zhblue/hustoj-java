<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Register - ${OJ_NAME}</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/hoj.css">
</head>
<body>
<div class="container">
    <div class="row">
        <div class="col-md-6 col-md-offset-3">
            <div class="panel panel-default">
                <div class="panel-heading"><h3>Register</h3></div>
                <div class="panel-body">
                    <c:if test="${error != null}">
                        <div class="alert alert-danger">${error}</div>
                    </c:if>
                    <form method="post" action="${pageContext.request.contextPath}/register">
                        <input type="hidden" name="action" value="submit"/>
                        <input type="hidden" name="_token" value="${csrfToken}"/>
                        <div class="form-group">
                            <label>Username *</label>
                            <input type="text" name="user_id" class="form-control" required pattern="[a-zA-Z0-9_-]+"/>
                        </div>
                        <div class="form-group">
                            <label>Password *</label>
                            <input type="password" name="password" class="form-control" required/>
                        </div>
                        <div class="form-group">
                            <label>Repeat Password *</label>
                            <input type="password" name="rptpassword" class="form-control" required/>
                        </div>
                        <div class="form-group">
                            <label>Email *</label>
                            <input type="email" name="email" class="form-control" required/>
                        </div>
                        <div class="form-group">
                            <label>Nick</label>
                            <input type="text" name="nick" class="form-control"/>
                        </div>
                        <div class="form-group">
                            <label>School</label>
                            <input type="text" name="school" class="form-control"/>
                        </div>
                        <div class="form-group">
                            <button type="submit" class="btn btn-primary">Register</button>
                        </div>
                    </form>
                </div>
            </div>
        </div>
    </div>
</div>
</body>
</html>
