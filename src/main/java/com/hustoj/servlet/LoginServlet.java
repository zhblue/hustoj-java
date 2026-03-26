package com.hustoj.servlet;

import com.hustoj.db.DB;
import com.hustoj.entity.User;
import com.hustoj.service.UserService;
import com.hustoj.util.IpUtil;
import com.hustoj.util.SecurityUtil;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

public class LoginServlet extends BaseServlet {

    private final UserService userService = new UserService();

    public void list(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession(false);
        if (session != null && session.getAttribute(DB.SESSION_USER_ID) != null) {
            redirect(req, resp, "/");
            return;
        }
        session = req.getSession(true);
        String token = getCsrfToken(session);
        setAttr(req, "csrfToken", token);
        setAttr(req, "OJ_VCODE", DB.isOjVcode());
        setAttr(req, "OJ_NAME", DB.getOjName());
        forward(req, resp, "/WEB-INF/views/user/login.jsp");
    }

    public void submit(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession(false);
        if (session != null && session.getAttribute(DB.SESSION_CSRF_TOKEN) != null) {
            String sessionToken = sessionAttr(session, DB.SESSION_CSRF_TOKEN);
            String paramToken = param(req, "_token");
            if (sessionToken != null && !sessionToken.equals(paramToken)) {
                setAttr(req, "error", "CSRF token mismatch");
                forward(req, resp, "/WEB-INF/views/user/login.jsp");
                return;
            }
        }

        String userId = param(req, "user_id");
        String password = param(req, "password");
        String vcode = param(req, "vcode");

        if (userId == null || password == null) {
            setAttr(req, "error", "Username and password required");
            forward(req, resp, "/WEB-INF/views/user/login.jsp");
            return;
        }

        // Check login fail limit
        int failLimit = DB.getConfigInt("oj.login.fail.limit", 5);
        if (failLimit > 0) {
            int failCount = userService.countLoginFail(userId, 5);
            if (failCount >= failLimit) {
                setAttr(req, "error", "Too many login failures, please try again later");
                forward(req, resp, "/WEB-INF/views/user/login.jsp");
                return;
            }
        }

        // VCODE check
        if (DB.isOjVcode()) {
            String sessionVcode = sessionAttr(session, DB.SESSION_VCODE);
            if (sessionVcode == null || !sessionVcode.equalsIgnoreCase(vcode)) {
                setAttr(req, "error", "Verification code wrong");
                forward(req, resp, "/WEB-INF/views/user/login.jsp");
                return;
            }
            session.removeAttribute(DB.SESSION_VCODE);
        }

        // Validate login
        String ip = IpUtil.getClientIp(req);
        if (userService.validateLogin(userId, password)) {
            User user = userService.findById(userId);

            // IP limit check
            String limitToOneIp = DB.getConfig("oj.limit_to_1_ip", "true");
            if ("true".equalsIgnoreCase(limitToOneIp)) {
                String lastIp = userService.getLastLoginIp(userId);
                if (lastIp != null && !lastIp.equals(ip)) {
                    session.invalidate();
                    setAttr(req, "error", "Login from another IP address: " + lastIp + ", auto logout!");
                    forward(req, resp, "/WEB-INF/views/error.jsp");
                    return;
                }
            }

            // Log login
            userService.insertLoginLog(userId, password, ip);

            // Update access time
            userService.updateAccessTime(userId, ip);

            // Set session
            HttpSession newSession = req.getSession(true);
            newSession.setAttribute(DB.SESSION_USER_ID, userId);
            newSession.setAttribute(DB.SESSION_NICK, user.getNick());

            // Check privileges
            if (userService.hasPrivilege(userId, "administrator")) {
                newSession.setAttribute(DB.SESSION_ADministrator, "true");
            }
            if (userService.hasPrivilege(userId, "source_browser")) {
                newSession.setAttribute(DB.SESSION_SOURCE_BROWSER, "true");
            }
            if (userService.hasPrivilege(userId, "problem_editor")) {
                newSession.setAttribute(DB.SESSION_PROBLEM_EDITOR, "true");
            }
            if (userService.hasPrivilege(userId, "problem_verifiter")) {
                newSession.setAttribute(DB.SESSION_PROBLEM_VERIFITER, "true");
            }

            redirect(req, resp, "/");
        } else {
            userService.insertLoginLog(userId, password, ip);
            setAttr(req, "error", "Username or password incorrect");
            forward(req, resp, "/WEB-INF/views/user/login.jsp");
        }
    }
}
