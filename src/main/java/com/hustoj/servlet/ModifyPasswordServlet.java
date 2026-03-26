package com.hustoj.servlet;

import com.hustoj.db.DB;
import com.hustoj.entity.User;
import com.hustoj.service.UserService;
import com.hustoj.util.SecurityUtil;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

public class ModifyPasswordServlet extends BaseServlet {

    private final UserService userService = new UserService();

    public void list(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if (getUserId(req) == null) {
            redirect(req, resp, "/loginpage");
            return;
        }
        forward(req, resp, "/WEB-INF/views/user/modifypassword.jsp");
    }

    public void submit(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String userId = getUserId(req);
        if (userId == null) {
            redirect(req, resp, "/loginpage");
            return;
        }

        String oldPwd = param(req, "old_password");
        String newPwd = param(req, "password");
        String rptPwd = param(req, "rptpassword");

        if (newPwd == null || !newPwd.equals(rptPwd)) {
            setAttr(req, "error", "Passwords do not match");
            forward(req, resp, "/WEB-INF/views/user/modifypassword.jsp");
            return;
        }

        User user = userService.findById(userId);
        if (!SecurityUtil.pwCheck(oldPwd, user.getPassword())) {
            setAttr(req, "error", "Old password incorrect");
            forward(req, resp, "/WEB-INF/views/user/modifypassword.jsp");
            return;
        }

        if (!SecurityUtil.isPasswordStrongEnough(newPwd)) {
            setAttr(req, "error", "New password too simple");
            forward(req, resp, "/WEB-INF/views/user/modifypassword.jsp");
            return;
        }

        userService.updatePassword(userId, newPwd);
        setAttr(req, "message", "Password changed successfully");
        forward(req, resp, "/WEB-INF/views/user/modifypassword.jsp");
    }
}
