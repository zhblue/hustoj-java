package com.hustoj.servlet;

import com.hustoj.db.DB;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.lang.reflect.Method;
import java.util.Map;

public abstract class BaseServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String action = req.getParameter("action");
        if (action == null || action.isEmpty()) {
            action = "list";
        }
        invokeAction(this, req, resp, action);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        String action = req.getParameter("action");
        if (action == null || action.isEmpty()) {
            action = "submit";
        }
        invokeAction(this, req, resp, action);
    }

    private void invokeAction(Object target, HttpServletRequest req, HttpServletResponse resp, String action) throws ServletException, IOException {
        try {
            Method method = target.getClass().getMethod(action, HttpServletRequest.class, HttpServletResponse.class);
            method.invoke(target, req, resp);
        } catch (NoSuchMethodException e) {
            resp.sendError(HttpServletResponse.SC_NOT_FOUND, "Action not found: " + action);
        } catch (Exception e) {
            Throwable cause = e.getCause();
            if (cause instanceof IOException) throw (IOException) cause;
            if (cause instanceof ServletException) throw (ServletException) cause;
            e.printStackTrace();
            req.setAttribute("error", cause != null ? cause.getMessage() : e.getMessage());
            req.getRequestDispatcher("/WEB-INF/views/error.jsp").forward(req, resp);
        }
    }

    protected void setAttr(HttpServletRequest req, String key, Object value) {
        req.setAttribute(key, value);
    }

    @SuppressWarnings("unchecked")
    protected void setAttrs(HttpServletRequest req, Map<String, Object> attrs) {
        attrs.forEach(req::setAttribute);
    }

    protected String param(HttpServletRequest req, String name) {
        return req.getParameter(name);
    }

    protected String param(HttpServletRequest req, String name, String defaultValue) {
        String v = req.getParameter(name);
        return v != null ? v : defaultValue;
    }

    protected int paramInt(HttpServletRequest req, String name, int defaultValue) {
        String v = req.getParameter(name);
        if (v == null || v.isEmpty()) return defaultValue;
        try {
            return Integer.parseInt(v);
        } catch (NumberFormatException e) {
            return defaultValue;
        }
    }

    protected long paramLong(HttpServletRequest req, String name, long defaultValue) {
        String v = req.getParameter(name);
        if (v == null || v.isEmpty()) return defaultValue;
        try {
            return Long.parseLong(v);
        } catch (NumberFormatException e) {
            return defaultValue;
        }
    }

    protected boolean paramBool(HttpServletRequest req, String name) {
        String v = req.getParameter(name);
        return "true".equalsIgnoreCase(v) || "1".equals(v) || "on".equalsIgnoreCase(v);
    }

    protected String sessionAttr(HttpSession session, String key) {
        Object v = session.getAttribute(key);
        return v != null ? v.toString() : null;
    }

    protected String sessionAttr(HttpServletRequest req, String key) {
        return sessionAttr(req.getSession(false), key);
    }

    protected void sessionSet(HttpSession session, String key, Object value) {
        session.setAttribute(key, value);
    }

    protected void sessionSet(HttpServletRequest req, String key, Object value) {
        HttpSession s = req.getSession();
        if (s != null) s.setAttribute(key, value);
    }

    protected void sessionRemove(HttpSession session, String key) {
        if (session != null) session.removeAttribute(key);
    }

    protected String getCsrfToken(HttpSession session) {
        String token = (String) session.getAttribute(DB.SESSION_CSRF_TOKEN);
        if (token == null) {
            token = java.util.UUID.randomUUID().toString();
            session.setAttribute(DB.SESSION_CSRF_TOKEN, token);
        }
        return token;
    }

    protected boolean validateCsrfToken(HttpServletRequest req) {
        String sessionToken = sessionAttr(req, DB.SESSION_CSRF_TOKEN);
        String paramToken = req.getParameter("_token");
        if (sessionToken == null || paramToken == null) return false;
        return sessionToken.equals(paramToken);
    }

    protected void redirect(HttpServletRequest req, HttpServletResponse resp, String url) throws IOException {
        resp.sendRedirect(req.getContextPath() + url);
    }

    protected void forward(HttpServletRequest req, HttpServletResponse resp, String path) throws ServletException, IOException {
        req.getRequestDispatcher(path).forward(req, resp);
    }

    protected void include(HttpServletRequest req, HttpServletResponse resp, String path) throws ServletException, IOException {
        req.getRequestDispatcher(path).include(req, resp);
    }

    protected String getUserId(HttpServletRequest req) {
        return sessionAttr(req, DB.SESSION_USER_ID);
    }

    protected boolean isAdmin(HttpServletRequest req) {
        return sessionAttr(req, DB.SESSION_ADministrator) != null;
    }

    protected boolean isLogin(HttpServletRequest req) {
        return sessionAttr(req, DB.SESSION_USER_ID) != null;
    }

    protected void setJojSession(HttpServletRequest req, String userId, String nick) {
        HttpSession session = req.getSession();
        session.setAttribute(DB.SESSION_USER_ID, userId);
        session.setAttribute(DB.SESSION_NICK, nick);
    }

    protected void setAdminSession(HttpServletRequest req) {
        req.getSession().setAttribute(DB.SESSION_ADministrator, "true");
    }
}
