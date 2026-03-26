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

public class ProblemAddServlet extends BaseServlet {

    private final ProblemService problemService = new ProblemService();

    public void list(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if (!isAdmin(req)) {
            redirect(req, resp, "/loginpage");
            return;
        }
        setAttr(req, "OJ_NAME", DB.getOjName());
        forward(req, resp, "/WEB-INF/views/admin/problem_add.jsp");
    }

    public void submit(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if (!isAdmin(req)) {
            redirect(req, resp, "/loginpage");
            return;
        }

        String title = param(req, "title");
        String description = param(req, "description");
        String input = param(req, "input");
        String output = param(req, "output");
        String sampleInput = param(req, "sample_input");
        String sampleOutput = param(req, "sample_output");
        String hint = param(req, "hint");
        String source = param(req, "source");
        String spj = param(req, "spj");
        BigDecimal timeLimit = new BigDecimal(param(req, "time_limit", "1"));
        int memoryLimit = paramInt(req, "memory_limit", 256);

        if (title == null || title.isEmpty()) {
            setAttr(req, "error", "Title is required");
            forward(req, resp, "/WEB-INF/views/admin/problem_add.jsp");
            return;
        }

        Problem p = new Problem();
        p.setTitle(title);
        p.setDescription(description != null ? description : "");
        p.setInput(input != null ? input : "");
        p.setOutput(output != null ? output : "");
        p.setSampleInput(sampleInput != null ? sampleInput : "");
        p.setSampleOutput(sampleOutput != null ? sampleOutput : "");
        p.setHint(hint != null ? hint : "");
        p.setSource(source != null ? source : "");
        p.setSpj(spj != null ? spj : "0");
        p.setTimeLimit(timeLimit);
        p.setMemoryLimit(memoryLimit);

        int problemId = problemService.insert(p);

        redirect(req, resp, "/admin/problem-edit?id=" + problemId);
    }
}
