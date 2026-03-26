package com.hustoj.servlet.admin;
import com.hustoj.servlet.BaseServlet;

import com.hustoj.db.DB;
import com.hustoj.entity.News;
import com.hustoj.service.NewsService;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

public class NewsEditServlet extends BaseServlet {

    private final NewsService newsService = new NewsService();

    public void list(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if (!isAdmin(req)) {
            redirect(req, resp, "/loginpage");
            return;
        }

        int newsId = paramInt(req, "id", 0);
        News news = newsService.findById(newsId);
        if (news == null) {
            setAttr(req, "error", "News not found");
            forward(req, resp, "/WEB-INF/views/error.jsp");
            return;
        }

        setAttr(req, "news", news);
        forward(req, resp, "/WEB-INF/views/admin/news_edit.jsp");
    }

    public void submit(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if (!isAdmin(req)) {
            redirect(req, resp, "/loginpage");
            return;
        }

        int newsId = paramInt(req, "news_id", 0);
        News n = newsService.findById(newsId);
        if (n == null) {
            setAttr(req, "error", "News not found");
            forward(req, resp, "/WEB-INF/views/error.jsp");
            return;
        }

        n.setTitle(param(req, "title"));
        n.setContent(param(req, "content"));
        n.setImportance(paramInt(req, "importance", 0));
        n.setMenu(paramInt(req, "menu", 0));
        n.setDefunct(param(req, "defunct", "N"));

        newsService.update(n);
        redirect(req, resp, "/admin/news-list");
    }
}
