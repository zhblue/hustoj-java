package com.hustoj.servlet;

import com.hustoj.db.DB;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

public class BalloonViewServlet extends BaseServlet {
    public void list(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int cid = paramInt(req, "cid", 0);
        if (cid <= 0) {
            setAttr(req, "error", "Contest ID required");
            forward(req, resp, "/WEB-INF/views/error.jsp");
            return;
        }
        setAttr(req, "cid", cid);
        forward(req, resp, "/balloon_view.jsp");
    }
}
