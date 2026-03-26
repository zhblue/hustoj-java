package com.hustoj.servlet.admin;
import com.hustoj.servlet.BaseServlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

public class ProblemJudgeServlet extends BaseServlet {
    public void submit(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if (!isAdmin(req)) {
            redirect(req, resp, "/loginpage");
            return;
        }
        // This servlet handles triggering rejudge for specific problem
        redirect(req, resp, "/admin/rejudge");
    }
}
