package com.hustoj.servlet;

import com.hustoj.db.DB;
import com.hustoj.entity.Topic;
import com.hustoj.entity.Reply;
import com.hustoj.service.TopicService;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;

public class ThreadServlet extends BaseServlet {

    private final TopicService topicService = new TopicService();

    public void list(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int tid = paramInt(req, "tid", 0);
        if (tid <= 0) {
            setAttr(req, "error", "Invalid thread ID");
            forward(req, resp, "/WEB-INF/views/error.jsp");
            return;
        }

        Topic topic = topicService.findById(tid);
        if (topic == null) {
            setAttr(req, "error", "Thread not found");
            forward(req, resp, "/WEB-INF/views/error.jsp");
            return;
        }

        List<Reply> replies = topicService.getReplies(tid);

        setAttr(req, "topic", topic);
        setAttr(req, "replies", replies);
        setAttr(req, "OJ_NAME", DB.getOjName());
        forward(req, resp, "/WEB-INF/views/discuss/thread.jsp");
    }

    public void reply(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String userId = getUserId(req);
        if (userId == null) {
            redirect(req, resp, "/loginpage");
            return;
        }

        int tid = paramInt(req, "tid", 0);
        String content = param(req, "content");
        String ip = com.hustoj.util.IpUtil.getClientIp(req);

        if (tid <= 0 || content == null || content.isEmpty()) {
            setAttr(req, "error", "Content required");
            forward(req, resp, "/WEB-INF/views/error.jsp");
            return;
        }

        topicService.addReply(tid, userId, content, ip);
        redirect(req, resp, "/thread?tid=" + tid);
    }
}
