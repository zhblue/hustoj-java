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

public class DiscussServlet extends BaseServlet {

    private final TopicService topicService = new TopicService();

    public void list(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int page = paramInt(req, "page", 1);
        int limit = 20;
        int offset = (page - 1) * limit;
        int pid = paramInt(req, "pid", 0);
        int cid = paramInt(req, "cid", 0);

        List<Topic> topics;
        if (pid > 0) {
            topics = topicService.findByProblemId(pid, offset, limit);
        } else if (cid > 0) {
            topics = topicService.findByContestId(cid, offset, limit);
        } else {
            topics = topicService.findAll(offset, limit);
        }

        int total = topicService.count();
        int pageCount = (total + limit - 1) / limit;

        setAttr(req, "topics", topics);
        setAttr(req, "page", page);
        setAttr(req, "pageCount", pageCount);
        setAttr(req, "pid", pid);
        setAttr(req, "cid", cid);
        setAttr(req, "OJ_NAME", DB.getOjName());
        forward(req, resp, "/WEB-INF/views/discuss/discuss.jsp");
    }
}
