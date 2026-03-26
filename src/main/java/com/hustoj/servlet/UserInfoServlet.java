package com.hustoj.servlet;

import com.hustoj.db.DB;
import com.hustoj.entity.User;
import com.hustoj.service.UserService;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;

public class UserInfoServlet extends BaseServlet {

    private final UserService userService = new UserService();

    public void list(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String userId = param(req, "uid");
        if (userId == null || userId.isEmpty()) {
            userId = getUserId(req);
        }
        if (userId == null) {
            redirect(req, resp, "/loginpage");
            return;
        }

        User user = userService.findById(userId);
        if (user == null) {
            setAttr(req, "error", "User not found");
            forward(req, resp, "/WEB-INF/views/error.jsp");
            return;
        }

        setAttr(req, "user", user);
        setAttr(req, "OJ_NAME", DB.getOjName());
        forward(req, resp, "/WEB-INF/views/user/userinfo.jsp");
    }

    public void update(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String userId = getUserId(req);
        if (userId == null) {
            redirect(req, resp, "/loginpage");
            return;
        }

        String nick = param(req, "nick");
        String school = param(req, "school");
        int language = paramInt(req, "language", 1);

        if (nick != null) userService.updateNick(userId, nick);
        if (school != null) userService.updateSchool(userId, school);
        userService.updateLanguage(userId, language);

        redirect(req, resp, "/userinfo?uid=" + userId);
    }
}
