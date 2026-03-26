package com.hustoj.servlet.admin;
import com.hustoj.servlet.BaseServlet;

import com.hustoj.db.DB;
import com.hustoj.entity.Problem;
import com.hustoj.service.ProblemService;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.math.BigDecimal;

public class ProblemEditServlet extends BaseServlet {

    private final ProblemService problemService = new ProblemService();

    public void list(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if (!isAdmin(req)) {
            redirect(req, resp, "/loginpage");
            return;
        }

        int problemId = paramInt(req, "id", 0);
        Problem problem = problemService.findById(problemId);
        if (problem == null) {
            setAttr(req, "error", "Problem not found");
            forward(req, resp, "/WEB-INF/views/error.jsp");
            return;
        }

        setAttr(req, "problem", problem);
        setAttr(req, "OJ_NAME", DB.getOjName());
        forward(req, resp, "/WEB-INF/views/admin/problem_edit.jsp");
    }

    public void submit(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if (!isAdmin(req)) {
            redirect(req, resp, "/loginpage");
            return;
        }

        int problemId = paramInt(req, "problem_id", 0);
        Problem p = problemService.findById(problemId);
        if (p == null) {
            setAttr(req, "error", "Problem not found");
            forward(req, resp, "/WEB-INF/views/error.jsp");
            return;
        }

        p.setTitle(param(req, "title"));
        p.setDescription(param(req, "description"));
        p.setInput(param(req, "input"));
        p.setOutput(param(req, "output"));
        p.setSampleInput(param(req, "sample_input"));
        p.setSampleOutput(param(req, "sample_output"));
        p.setHint(param(req, "hint"));
        p.setSource(param(req, "source"));
        p.setSpj(param(req, "spj", "0"));
        p.setTimeLimit(new BigDecimal(param(req, "time_limit", "1")));
        p.setMemoryLimit(paramInt(req, "memory_limit", 256));
        p.setDefunct(param(req, "defunct", "N"));

        problemService.update(p);

        setAttr(req, "message", "Problem updated");
        setAttr(req, "problem", p);
        forward(req, resp, "/WEB-INF/views/admin/problem_edit.jsp");
    }
}
