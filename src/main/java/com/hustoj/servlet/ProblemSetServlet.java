package com.hustoj.servlet;

import com.hustoj.db.DB;
import com.hustoj.entity.Problem;
import com.hustoj.service.ProblemService;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;

public class ProblemSetServlet extends BaseServlet {

    private final ProblemService problemService = new ProblemService();

    public void list(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int page = paramInt(req, "page", 1);
        int limit = 50;
        int offset = (page - 1) * limit;
        String keyword = param(req, "search");

        List<Problem> problems;
        int total;

        if (keyword != null && !keyword.isEmpty()) {
            problems = problemService.findByKeyword(keyword, offset, limit);
            total = problemService.countByKeyword(keyword);
        } else {
            problems = problemService.findAll(offset, limit);
            total = problemService.count("N");
        }

        int pageCount = (total + limit - 1) / limit;

        setAttr(req, "problems", problems);
        setAttr(req, "page", page);
        setAttr(req, "pageCount", pageCount);
        setAttr(req, "total", total);
        setAttr(req, "keyword", keyword);
        setAttr(req, "OJ_NAME", DB.getOjName());
        forward(req, resp, "/WEB-INF/views/problem/problemset.jsp");
    }
}
