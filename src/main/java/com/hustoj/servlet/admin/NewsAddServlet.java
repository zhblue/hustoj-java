package com.hustoj.servlet.admin;
import com.hustoj.servlet.BaseServlet;

import com.hustoj.db.DB;
import com.hustoj.entity.News;
import com.hustoj.service.NewsService;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

public class NewsAddServlet extends BaseServlet {

    private final NewsService newsService = new NewsService();

    public void submit(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if (!isAdmin(req)) {
            redirect(req, resp, "/loginpage");
            return;
        }

        String title = param(req, "title");
        String content = param(req, "content");
        int importance = paramInt(req, "importance", 0);
        int menu = paramInt(req, "menu", 0);
        String userId = getUserId(req);

        if (title == null || content == null) {
            setAttr(req, "error", "Title and content are required");
            forward(req, resp, "/WEB-INF/views/admin/news_add.jsp");
            return;
        }

        News n = new News();
        n.setTitle(title);
        n.setContent(content);
        n.setImportance(importance);
        n.setMenu(menu);
        n.setUserId(userId);
        n.setDefunct("N");

        int newsId = newsService.insert(n);
        redirect(req, resp, "/admin/news-list");
    }
}
