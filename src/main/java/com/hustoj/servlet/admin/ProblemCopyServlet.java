package com.hustoj.servlet.admin;
import com.hustoj.servlet.BaseServlet;

import com.hustoj.service.ProblemService;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

public class ProblemCopyServlet extends BaseServlet {
    private final ProblemService problemService = new ProblemService();

    public void submit(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if (!isAdmin(req)) {
            redirect(req, resp, "/loginpage");
            return;
        }
        int problemId = paramInt(req, "problem_id", 0);
        if (problemId > 0) {
            int newId = problemService.copy(problemId, getUserId(req));
            if (newId > 0) {
                redirect(req, resp, "/admin/problem-edit?id=" + newId);
                return;
            }
        }
        redirect(req, resp, "/problemset");
    }
}
