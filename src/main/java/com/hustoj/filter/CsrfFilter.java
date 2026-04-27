package com.hustoj.filter;

import com.hustoj.db.DB;
import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * CSRF protection filter matching PHP HustOJ's csrf_check.php behavior.
 * 
 * PHP uses $_SESSION[$OJ_NAME.'_'.'csrf_keys'] as an array of valid tokens.
 * Each POST must include a csrf parameter that exists in the array.
 * Once used, the token is removed from the array (one-time use).
 */
public class CsrfFilter implements Filter {

    public static final String CSRF_KEYS_SESSION_ATTR = "HUSTOJ_csrf_keys";

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse resp = (HttpServletResponse) response;

        // Only validate POST requests
        if ("POST".equalsIgnoreCase(req.getMethod())) {
            HttpSession session = req.getSession(false);
            if (session == null) {
                resp.sendError(HttpServletResponse.SC_FORBIDDEN, "No session");
                return;
            }

            @SuppressWarnings("unchecked")
            CopyOnWriteArrayList<String> csrfKeys = 
                (CopyOnWriteArrayList<String>) session.getAttribute(CSRF_KEYS_SESSION_ATTR);

            if (csrfKeys == null || !csrfKeys.isEmpty()) {
                String paramToken = req.getParameter("csrf");
                
                if (paramToken == null || paramToken.isEmpty()) {
                    resp.sendError(HttpServletResponse.SC_FORBIDDEN, "Missing CSRF token");
                    return;
                }

                // Check if token exists in the list
                if (!csrfKeys.contains(paramToken)) {
                    resp.sendError(HttpServletResponse.SC_FORBIDDEN, "Invalid CSRF token");
                    return;
                }

                // Remove the used token (one-time use like PHP)
                csrfKeys.remove(paramToken);
            }
        }
        chain.doFilter(request, response);
    }

    @Override
    public void destroy() {
    }
}
