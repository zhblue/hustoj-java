package com.hustoj.filter;

import com.hustoj.db.DB;
import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

public class AuthFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse resp = (HttpServletResponse) response;
        HttpSession session = req.getSession(false);

        String uri = req.getRequestURI();
        String contextPath = req.getContextPath();
        String path = uri.substring(contextPath.length());

        // Static resources and public pages
        if (isPublicPath(path)) {
            chain.doFilter(request, response);
            return;
        }

        // Check login requirement
        if (DB.isOjNeedLogin() && !isLogin(session)) {
            resp.sendRedirect(contextPath + "/loginpage");
            return;
        }

        // Admin page protection
        if (path.startsWith("/admin/") && !isAdmin(session)) {
            resp.sendRedirect(contextPath + "/loginpage");
            return;
        }

        chain.doFilter(request, response);
    }

    private boolean isPublicPath(String path) {
        return path.equals("/") ||
               path.equals("/index") ||
               path.equals("/index.jsp") ||
               path.equals("/loginpage") ||
               path.equals("/loginpage.jsp") ||
               path.equals("/registerpage") ||
               path.equals("/registerpage.jsp") ||
               path.equals("/lostpassword") ||
               path.equals("/lostpassword.jsp") ||
               path.equals("/status-ajax") ||
               path.startsWith("/css/") ||
               path.startsWith("/js/") ||
               path.startsWith("/image/") ||
               path.startsWith("/assets/") ||
               path.startsWith("/kindeditor/") ||
               path.endsWith(".css") ||
               path.endsWith(".js") ||
               path.endsWith(".png") ||
               path.endsWith(".jpg") ||
               path.endsWith(".gif") ||
               path.endsWith(".svg") ||
               path.endsWith(".woff") ||
               path.endsWith(".woff2") ||
               path.endsWith(".ttf");
    }

    private boolean isLogin(HttpSession session) {
        return session != null && session.getAttribute(DB.SESSION_USER_ID) != null;
    }

    private boolean isAdmin(HttpSession session) {
        return session != null && session.getAttribute(DB.SESSION_ADministrator) != null;
    }

    @Override
    public void destroy() {
    }
}
