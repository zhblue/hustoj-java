package com.hustoj.servlet;

import com.hustoj.service.SolutionService;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

public class ReinfoServlet extends BaseServlet {

    private final SolutionService solutionService = new SolutionService();

    public void list(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int solutionId = paramInt(req, "sid", 0);
        if (solutionId <= 0) {
            setAttr(req, "error", "Invalid solution ID");
            forward(req, resp, "/WEB-INF/views/error.jsp");
            return;
        }

        String info = solutionService.getRuntimeInfo(solutionId);
        setAttr(req, "info", info != null ? info : "");
        forward(req, resp, "/WEB-INF/views/status/reinfo.jsp");
    }
}
