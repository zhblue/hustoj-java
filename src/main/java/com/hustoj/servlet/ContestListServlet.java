package com.hustoj.servlet;

import com.hustoj.db.DB;
import com.hustoj.entity.Contest;
import com.hustoj.service.ContestService;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;

public class ContestListServlet extends BaseServlet {

    private final ContestService contestService = new ContestService();

    public void list(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int page = paramInt(req, "page", 1);
        int limit = 20;
        int offset = (page - 1) * limit;

        String filter = param(req, "filter");

        List<Contest> contests;
        int total;

        if ("running".equals(filter)) {
            contests = contestService.findRunning();
            total = contests.size();
        } else if ("upcoming".equals(filter)) {
            contests = contestService.findUpcoming(limit);
            total = contests.size();
        } else if ("recent".equals(filter)) {
            contests = contestService.findRecent(limit);
            total = contests.size();
        } else {
            contests = contestService.findAll(offset, limit, "N");
            total = contestService.count("N");
        }

        int pageCount = (total + limit - 1) / limit;

        setAttr(req, "contests", contests);
        setAttr(req, "page", page);
        setAttr(req, "pageCount", pageCount);
        setAttr(req, "total", total);
        setAttr(req, "filter", filter);
        setAttr(req, "OJ_NAME", DB.getOjName());
        forward(req, resp, "/WEB-INF/views/contest/contest-list.jsp");
    }
}
