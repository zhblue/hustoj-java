package com.hustoj.servlet.admin;
import com.hustoj.servlet.BaseServlet;

import com.hustoj.db.DB;
import com.hustoj.entity.News;
import com.hustoj.service.NewsService;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;

public class NewsListServlet extends BaseServlet {

    private final NewsService newsService = new NewsService();

    public void list(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if (!isAdmin(req)) {
            redirect(req, resp, "/loginpage");
            return;
        }

        int page = paramInt(req, "page", 1);
        int limit = 50;
        int offset = (page - 1) * limit;

        List<News> newsList = newsService.findAll(offset, limit, null);
        int total = newsService.count(null);
        int pageCount = (total + limit - 1) / limit;

        setAttr(req, "newsList", newsList);
        setAttr(req, "page", page);
        setAttr(req, "pageCount", pageCount);
        setAttr(req, "OJ_NAME", DB.getOjName());
        forward(req, resp, "/WEB-INF/views/admin/news_list.jsp");
    }
}
