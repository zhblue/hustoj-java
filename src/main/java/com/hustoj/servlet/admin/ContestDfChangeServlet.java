package com.hustoj.servlet.admin;
import com.hustoj.servlet.BaseServlet;

import com.hustoj.service.ContestService;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

public class ContestDfChangeServlet extends BaseServlet {
    private final ContestService contestService = new ContestService();

    public void submit(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if (!isAdmin(req)) {
            redirect(req, resp, "/loginpage");
            return;
        }
        int contestId = paramInt(req, "contest_id", 0);
        String defunct = param(req, "defunct", "N");
        if (contestId > 0) {
            contestService.setDefunct(contestId, defunct);
        }
        redirect(req, resp, "/contest");
    }
}
