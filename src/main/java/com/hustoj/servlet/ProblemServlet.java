package com.hustoj.servlet;

import com.hustoj.db.DB;
import com.hustoj.entity.Problem;
import com.hustoj.service.ProblemService;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

public class ProblemServlet extends BaseServlet {

    private final ProblemService problemService = new ProblemService();

    public void list(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int problemId = paramInt(req, "id", 0);
        if (problemId <= 0) {
            setAttr(req, "error", "Invalid problem ID");
            forward(req, resp, "/WEB-INF/views/error.jsp");
            return;
        }

        Problem problem = problemService.findByIdNotDefunct(problemId);
        if (problem == null) {
            problem = problemService.findById(problemId);
            if (problem == null) {
                setAttr(req, "error", "Problem not found");
                forward(req, resp, "/WEB-INF/views/error.jsp");
                return;
            }
            if (!"N".equals(problem.getDefunct()) && !isAdmin(req)) {
                setAttr(req, "error", "Problem is not available");
                forward(req, resp, "/WEB-INF/views/error.jsp");
                return;
            }
        }

        setAttr(req, "problem", problem);
        setAttr(req, "OJ_NAME", DB.getOjName());
        setAttr(req, "OJ_APPENDCODE", DB.isOjAppendCode());
        setAttr(req, "OJ_LANG", DB.getOjLang());
        forward(req, resp, "/WEB-INF/views/problem/problem.jsp");
    }
}
