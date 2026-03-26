package com.hustoj.servlet.admin;
import com.hustoj.servlet.BaseServlet;

import com.hustoj.service.ProblemService;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

public class ProblemChangeIdServlet extends BaseServlet {
    private final ProblemService problemService = new ProblemService();

    public void submit(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if (!isAdmin(req)) {
            redirect(req, resp, "/loginpage");
            return;
        }
        int oldId = paramInt(req, "old_id", 0);
        int newId = paramInt(req, "new_id", 0);
        if (oldId > 0 && newId > 0) {
            problemService.changeId(oldId, newId);
        }
        redirect(req, resp, "/problemset");
    }
}
