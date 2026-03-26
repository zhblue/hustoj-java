package com.hustoj.servlet;

import com.hustoj.db.DB;
import com.hustoj.service.TopicService;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

public class PostServlet extends BaseServlet {

    private final TopicService topicService = new TopicService();

    public void list(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int tid = paramInt(req, "tid", 0);
        int cid = paramInt(req, "cid", 0);
        int pid = paramInt(req, "pid", 0);
        setAttr(req, "tid", tid);
        setAttr(req, "cid", cid);
        setAttr(req, "pid", pid);
        forward(req, resp, "/WEB-INF/views/discuss/post.jsp");
    }
}
