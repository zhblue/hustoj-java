package com.hustoj.servlet.admin;
import com.hustoj.servlet.BaseServlet;

import com.hustoj.db.DB;
import com.hustoj.entity.User;
import com.hustoj.service.UserService;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;

public class UserManageServlet extends BaseServlet {

    private final UserService userService = new UserService();

    public void list(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if (!isAdmin(req)) {
            redirect(req, resp, "/loginpage");
            return;
        }

        int page = paramInt(req, "page", 1);
        int limit = 50;
        int offset = (page - 1) * limit;

        String defunct = param(req, "defunct");
        List<User> users = userService.findAll(offset, limit, defunct);
        int total = userService.count(defunct);
        int pageCount = (total + limit - 1) / limit;

        setAttr(req, "users", users);
        setAttr(req, "page", page);
        setAttr(req, "pageCount", pageCount);
        setAttr(req, "total", total);
        setAttr(req, "defunct", defunct);
        setAttr(req, "OJ_NAME", DB.getOjName());
        forward(req, resp, "/WEB-INF/views/admin/user_list.jsp");
    }

    public void edit(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if (!isAdmin(req)) {
            redirect(req, resp, "/loginpage");
            return;
        }

        String userId = param(req, "uid");
        String action = param(req, "action");

        if ("update".equals(action)) {
            String nick = param(req, "nick");
            String school = param(req, "school");
            int volume = paramInt(req, "volume", 1);
            int language = paramInt(req, "language", 1);
            String defunct = param(req, "defunct");
            String password = param(req, "password");

            if (nick != null) userService.updateNick(userId, nick);
            if (school != null) userService.updateSchool(userId, school);
            userService.updateVolume(userId, volume);
            userService.updateLanguage(userId, language);
            if (defunct != null) userService.setDefunct(userId, defunct);
            if (password != null && !password.isEmpty()) userService.updatePassword(userId, password);

            redirect(req, resp, "/admin/user");
            return;
        }

        User user = userService.findById(userId);
        setAttr(req, "user", user);
        forward(req, resp, "/WEB-INF/views/admin/user_edit.jsp");
    }

    public void delete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if (!isAdmin(req)) {
            redirect(req, resp, "/loginpage");
            return;
        }

        String userId = param(req, "uid");
        if (userId != null) {
            userService.setDefunct(userId, "Y");
        }
        redirect(req, resp, "/admin/user");
    }
}
