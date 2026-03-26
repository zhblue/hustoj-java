<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %>
<nav class="navbar navbar-default">
    <div class="container">
        <div class="navbar-header">
            <a class="navbar-brand" href="${pageContext.request.contextPath}/">${OJ_NAME}</a>
        </div>
        <ul class="nav navbar-nav">
            <li><a href="${pageContext.request.contextPath}/problemset">Problems</a></li>
            <li><a href="${pageContext.request.contextPath}/contest">Contests</a></li>
            <li><a href="${pageContext.request.contextPath}/status">Status</a></li>
            <li><a href="${pageContext.request.contextPath}/ranklist">Ranklist</a></li>
            <li><a href="${pageContext.request.contextPath}/discuss">Discuss</a></li>
            <li><a href="${pageContext.request.contextPath}/news">News</a></li>
            <c:if test="${sessionScope.HUSTOJ_user_id != null}">
                <li><a href="${pageContext.request.contextPath}/mail">Mail</a></li>
                <li><a href="${pageContext.request.contextPath}/userinfo?uid=${sessionScope.HUSTOJ_user_id}">${sessionScope.HUSTOJ_user_id}</a></li>
                <li><a href="${pageContext.request.contextPath}/logout">Logout</a></li>
            </c:if>
            <c:if test="${sessionScope.HUSTOJ_user_id == null}">
                <li><a href="${pageContext.request.contextPath}/loginpage">Login</a></li>
                <li><a href="${pageContext.request.contextPath}/registerpage">Register</a></li>
            </c:if>
            <c:if test="${sessionScope.HUSTOJ_administrator != null}">
                <li><a href="${pageContext.request.contextPath}/admin/">Admin</a></li>
            </c:if>
        </ul>
    </div>
</nav>
