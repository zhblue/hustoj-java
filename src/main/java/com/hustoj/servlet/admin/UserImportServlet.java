package com.hustoj.servlet.admin;
import com.hustoj.servlet.BaseServlet;

import com.hustoj.db.DB;
import com.hustoj.entity.User;
import com.hustoj.service.UserService;
import com.hustoj.util.SecurityUtil;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class UserImportServlet extends BaseServlet {

    private final UserService userService = new UserService();

    public void submit(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if (!isAdmin(req)) {
            redirect(req, resp, "/loginpage");
            return;
        }

        Part filePart = req.getPart("file");
        if (filePart == null) {
            setAttr(req, "error", "No file uploaded");
            forward(req, resp, "/WEB-INF/views/admin/user_import.jsp");
            return;
        }

        String format = param(req, "format");
        List<User> users = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new InputStreamReader(filePart.getInputStream(), StandardCharsets.UTF_8))) {
            String line;
            while ((line = reader.readLine()) != null) {
                line = line.trim();
                if (line.isEmpty()) continue;

                String[] parts;
                if ("csv".equals(format)) {
                    parts = line.split(",");
                } else {
                    parts = line.split("\\s+");
                }

                if (parts.length >= 1) {
                    User u = new User();
                    u.setUserId(parts[0]);
                    if (parts.length >= 2) u.setPassword(SecurityUtil.pwGen(parts[1], false));
                    if (parts.length >= 3) u.setNick(parts[2]);
                    if (parts.length >= 4) u.setSchool(parts[3]);
                    if (parts.length >= 5) u.setEmail(parts[4]);
                    users.add(u);
                }
            }
        }

        int count = userService.batchImport(users);
        setAttr(req, "count", count);
        setAttr(req, "message", "Imported " + count + " users");
        forward(req, resp, "/WEB-INF/views/admin/user_import.jsp");
    }
}
