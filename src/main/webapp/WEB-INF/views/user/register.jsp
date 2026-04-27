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
        <div class="col-md-6 offset-md-3">
            <div class="card">
                <div class="card-header"><h3>Register</h3></div>
                <div class="card-body">
                    <c:if test="${error != null}">
                        <div class="alert alert-danger">${error}</div>
                    </c:if>
                    <form method="post" action="${pageContext.request.contextPath}/register">
                        <input type="hidden" name="action" value="submit"/>
                        <input type="hidden" name="_token" value="${csrfToken}"/>
                        <div class="mb-3">
                            <label>Username *</label>
                            <input type="text" name="user_id" class="form-control" required pattern="[a-zA-Z0-9_-]+"/>
                        </div>
                        <div class="mb-3">
                            <label>Password *</label>
                            <input type="password" name="password" class="form-control" required/>
                        </div>
                        <div class="mb-3">
                            <label>Repeat Password *</label>
                            <input type="password" name="rptpassword" class="form-control" required/>
                        </div>
                        <div class="mb-3">
                            <label>Email *</label>
                            <input type="email" name="email" class="form-control" required/>
                        </div>
                        <div class="mb-3">
                            <label>Nick</label>
                            <input type="text" name="nick" class="form-control"/>
                        </div>
                        <div class="mb-3">
                            <label>School</label>
                            <input type="text" name="school" class="form-control"/>
                        </div>
                        <div class="mb-3">
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
