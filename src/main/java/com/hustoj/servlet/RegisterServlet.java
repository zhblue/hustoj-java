package com.hustoj.servlet;

import com.hustoj.db.DB;
import com.hustoj.service.UserService;
import com.hustoj.util.IpUtil;
import com.hustoj.util.SecurityUtil;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.util.concurrent.CopyOnWriteArrayList;

public class RegisterServlet extends BaseServlet {

    private final UserService userService = new UserService();

    public void list(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if (!DB.isOjRegister()) {
            setAttr(req, "error", "Registration is disabled");
            forward(req, resp, "/WEB-INF/views/error.jsp");
            return;
        }
        HttpSession session = req.getSession(true);
        
        // Initialize CSRF token list like PHP does
        CopyOnWriteArrayList<String> csrfKeys = new CopyOnWriteArrayList<>();
        csrfKeys.add(SecurityUtil.generateToken());
        session.setAttribute("HUSTOJ_csrf_keys", csrfKeys);
        
        setAttr(req, "csrfToken", csrfKeys.get(0));
        setAttr(req, "OJ_NAME", DB.getOjName());
        forward(req, resp, "/WEB-INF/views/user/register.jsp");
    }

    public void submit(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if (!DB.isOjRegister()) {
            setAttr(req, "error", "Registration is disabled");
            forward(req, resp, "/WEB-INF/views/error.jsp");
            return;
        }

        String userId = param(req, "user_id");
        String password = param(req, "password");
        String rpPassword = param(req, "rptpassword");
        String email = param(req, "email");
        String nick = param(req, "nick");
        String school = param(req, "school");

        // Validate CSRF token
        HttpSession session = req.getSession(false);
        if (session == null) {
            setAttr(req, "error", "Session expired");
            forward(req, resp, "/WEB-INF/views/error.jsp");
            return;
        }

        @SuppressWarnings("unchecked")
        CopyOnWriteArrayList<String> csrfKeys = 
            (CopyOnWriteArrayList<String>) session.getAttribute("HUSTOJ_csrf_keys");
        String paramToken = param(req, "csrf");
        if (csrfKeys == null || paramToken == null || !csrfKeys.contains(paramToken)) {
            setAttr(req, "error", "Invalid CSRF token");
            forward(req, resp, "/WEB-INF/views/error.jsp");
            return;
        }

        // Username length check (3-48 chars)
        int userIdLen = userId != null ? userId.length() : 0;
        if (userIdLen < 3) {
            setAttr(req, "error", "User ID too short (minimum 3 characters)");
            forward(req, resp, "/WEB-INF/views/user/register.jsp");
            return;
        }
        if (userIdLen > 48) {
            setAttr(req, "error", "User ID too long (maximum 48 characters)");
            forward(req, resp, "/WEB-INF/views/user/register.jsp");
            return;
        }

        // Username format validation
        if (!SecurityUtil.isValidUserName(userId)) {
            setAttr(req, "error", "Invalid user ID format (only letters, numbers, -, _ allowed)");
            forward(req, resp, "/WEB-INF/views/user/register.jsp");
            return;
        }

        // Bad words check for user_id
        if (SecurityUtil.hasBadWords(userId)) {
            setAttr(req, "error", "User ID contains forbidden words");
            forward(req, resp, "/WEB-INF/views/user/register.jsp");
            return;
        }

        // Password match check
        if (!password.equals(rpPassword)) {
            setAttr(req, "error", "Passwords do not match");
            forward(req, resp, "/WEB-INF/views/user/register.jsp");
            return;
        }

        // Password length check (min 6)
        if (password.length() < 6) {
            setAttr(req, "error", "Password too short (minimum 6 characters)");
            forward(req, resp, "/WEB-INF/views/user/register.jsp");
            return;
        }

        // Nick length check (max 20)
        if (nick != null && nick.length() > 20) {
            setAttr(req, "error", "Nick too long (maximum 20 characters)");
            forward(req, resp, "/WEB-INF/views/user/register.jsp");
            return;
        }
        if (nick == null || nick.isEmpty()) {
            nick = userId;
        }

        // School length check (max 20)
        if (school != null && school.length() > 20) {
            setAttr(req, "error", "School name too long (maximum 20 characters)");
            forward(req, resp, "/WEB-INF/views/user/register.jsp");
            return;
        }

        // Bad words check for school
        if (school != null && SecurityUtil.hasBadWords(school)) {
            setAttr(req, "error", "School contains forbidden words");
            forward(req, resp, "/WEB-INF/views/user/register.jsp");
            return;
        }

        // Bad words check for nick
        if (nick != null && SecurityUtil.hasBadWords(nick)) {
            setAttr(req, "error", "Nick contains forbidden words");
            forward(req, resp, "/WEB-INF/views/user/register.jsp");
            return;
        }

        // Email format validation
        if (email == null || email.isEmpty()) {
            setAttr(req, "error", "Email cannot be empty");
            forward(req, resp, "/WEB-INF/views/user/register.jsp");
            return;
        }
        if (email.length() > 100) {
            setAttr(req, "error", "Email too long (maximum 100 characters)");
            forward(req, resp, "/WEB-INF/views/user/register.jsp");
            return;
        }
        if (!isValidEmail(email)) {
            setAttr(req, "error", "Invalid email format");
            forward(req, resp, "/WEB-INF/views/user/register.jsp");
            return;
        }

        // Check if user exists
        if (userService.exists(userId)) {
            setAttr(req, "error", "User ID already exists");
            forward(req, resp, "/WEB-INF/views/user/register.jsp");
            return;
        }

        // Check if email exists
        if (userService.emailExists(email)) {
            setAttr(req, "error", "Email already registered");
            forward(req, resp, "/WEB-INF/views/user/register.jsp");
            return;
        }

        // Password strength check (too_simple)
        if (SecurityUtil.isPasswordStrongEnough(password)) {
            // Password is strong enough - NOT too simple
        } else {
            setAttr(req, "error", "Password is too simple (need at least 8 chars with mixed letters and numbers)");
            forward(req, resp, "/WEB-INF/views/user/register.jsp");
            return;
        }

        String ip = IpUtil.getClientIp(req);
        int result = userService.register(
            userId, password, email, 
            nick != null ? nick : "", 
            school != null ? school : "", 
            ip
        );

        if (result > 0) {
            // Remove used CSRF token on success
            csrfKeys.remove(paramToken);
            redirect(req, resp, "/loginpage?registered=1");
        } else if (result == -1) {
            setAttr(req, "error", "User ID already exists");
            forward(req, resp, "/WEB-INF/views/user/register.jsp");
        } else if (result == -2) {
            setAttr(req, "error", "Email already registered");
            forward(req, resp, "/WEB-INF/views/user/register.jsp");
        } else {
            setAttr(req, "error", "Registration failed");
            forward(req, resp, "/WEB-INF/views/user/register.jsp");
        }
    }

    private boolean isValidEmail(String email) {
        // Use Java's built-in email validation
        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
        return email.matches(emailRegex);
    }
}
