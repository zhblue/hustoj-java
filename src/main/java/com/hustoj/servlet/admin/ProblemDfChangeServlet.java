package com.hustoj.servlet.admin;
import com.hustoj.servlet.BaseServlet;

import com.hustoj.service.ProblemService;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

public class ProblemDfChangeServlet extends BaseServlet {
    private final ProblemService problemService = new ProblemService();

    public void submit(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if (!isAdmin(req)) {
            redirect(req, resp, "/loginpage");
            return;
        }
        int problemId = paramInt(req, "problem_id", 0);
        String defunct = param(req, "defunct", "N");
        if (problemId > 0) {
            problemService.setDefunct(problemId, defunct);
        }
        redirect(req, resp, "/problemset");
    }
}
