package com.hustoj.servlet;

import com.hustoj.db.DB;
import com.hustoj.entity.News;
import com.hustoj.service.NewsService;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;

public class NewsServlet extends BaseServlet {

    private final NewsService newsService = new NewsService();

    public void list(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int page = paramInt(req, "page", 1);
        int limit = 20;
        int offset = (page - 1) * limit;

        List<News> newsList = newsService.findAll(offset, limit);
        int total = newsService.count("N");
        int pageCount = (total + limit - 1) / limit;

        setAttr(req, "newsList", newsList);
        setAttr(req, "page", page);
        setAttr(req, "pageCount", pageCount);
        setAttr(req, "OJ_NAME", DB.getOjName());
        forward(req, resp, "/WEB-INF/views/news/newslist.jsp");
    }

    public void view(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int newsId = paramInt(req, "id", 0);
        if (newsId <= 0) {
            setAttr(req, "error", "Invalid news ID");
            forward(req, resp, "/WEB-INF/views/error.jsp");
            return;
        }

        News news = newsService.findById(newsId);
        if (news == null) {
            setAttr(req, "error", "News not found");
            forward(req, resp, "/WEB-INF/views/error.jsp");
            return;
        }

        setAttr(req, "news", news);
        setAttr(req, "OJ_NAME", DB.getOjName());
        forward(req, resp, "/WEB-INF/views/news/viewnews.jsp");
    }
}
