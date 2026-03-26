package com.hustoj.servlet;

import com.hustoj.db.DB;
import com.hustoj.service.SolutionService;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;
import java.util.Map;

public class CompareSourceServlet extends BaseServlet {
    private final SolutionService solutionService = new SolutionService();

    public void list(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int sid1 = paramInt(req, "sid1", 0);
        int sid2 = paramInt(req, "sid2", 0);

        String src1 = solutionService.getSourceCode(sid1);
        String src2 = solutionService.getSourceCode(sid2);

        setAttr(req, "sid1", sid1);
        setAttr(req, "sid2", sid2);
        setAttr(req, "src1", src1);
        setAttr(req, "src2", src2);
        forward(req, resp, "/comparesource.jsp");
    }
}
