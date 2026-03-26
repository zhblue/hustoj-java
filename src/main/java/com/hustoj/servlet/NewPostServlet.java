package com.hustoj.servlet;

import com.hustoj.db.DB;
import com.hustoj.entity.Topic;
import com.hustoj.service.TopicService;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

public class NewPostServlet extends BaseServlet {

    private final TopicService topicService = new TopicService();

    public void list(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int pid = paramInt(req, "pid", 0);
        int cid = paramInt(req, "cid", 0);
        setAttr(req, "pid", pid);
        setAttr(req, "cid", cid);
        forward(req, resp, "/WEB-INF/views/discuss/newpost.jsp");
    }

    public void submit(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String userId = getUserId(req);
        if (userId == null) {
            redirect(req, resp, "/loginpage");
            return;
        }

        String title = param(req, "title");
        String content = param(req, "content");
        int pid = paramInt(req, "pid", 0);
        int cid = paramInt(req, "cid", 0);

        if (title == null || content == null || title.isEmpty() || content.isEmpty()) {
            setAttr(req, "error", "Title and content required");
            forward(req, resp, "/WEB-INF/views/discuss/newpost.jsp");
            return;
        }

        int tid = topicService.createTopic(title, pid, cid > 0 ? cid : null, userId);
        redirect(req, resp, "/thread?tid=" + tid);
    }
}
