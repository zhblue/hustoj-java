package com.hustoj.servlet;

import com.hustoj.db.DB;
import com.hustoj.service.SolutionService;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

public class ShowSourceServlet extends BaseServlet {

    private final SolutionService solutionService = new SolutionService();

    public void list(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int solutionId = paramInt(req, "sid", 0);
        if (solutionId <= 0) {
            setAttr(req, "error", "Invalid solution ID");
            forward(req, resp, "/WEB-INF/views/error.jsp");
            return;
        }

        String userId = getUserId(req);
        boolean isAdmin = isAdmin(req);
        boolean isSourceBrowser = sessionAttr(req, DB.SESSION_SOURCE_BROWSER) != null;

        if (!isAdmin && !isSourceBrowser) {
            String sourceUserId = solutionService.findById(solutionId).getUserId();
            if (sourceUserId != null && !sourceUserId.equals(userId)) {
                setAttr(req, "error", "Access denied");
                forward(req, resp, "/WEB-INF/views/error.jsp");
                return;
            }
        }

        String source = solutionService.getSourceCode(solutionId);
        setAttr(req, "source", source);
        forward(req, resp, "/WEB-INF/views/status/showsource.jsp");
    }
}
