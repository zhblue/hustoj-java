package com.hustoj.service;

import com.hustoj.db.DB;
import com.hustoj.entity.News;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.List;

public class NewsService {

    private final JdbcTemplate jdbc;

    public NewsService() {
        this.jdbc = DB.getJdbcTemplate();
    }

    public NewsService(JdbcTemplate jdbc) {
        this.jdbc = jdbc;
    }

    public News findById(int newsId) {
        String sql = "SELECT * FROM news WHERE news_id=?";
        List<News> list = jdbc.query(sql, new NewsRowMapper(), newsId);
        return list.isEmpty() ? null : list.get(0);
    }

    public List<News> findAll(int offset, int limit) {
        String sql = "SELECT * FROM news WHERE defunct='N' ORDER BY news_id DESC LIMIT ? OFFSET ?";
        return jdbc.query(sql, new NewsRowMapper(), limit, offset);
    }

    public List<News> findAll(int offset, int limit, String defunct) {
        String sql = defunct == null
            ? "SELECT * FROM news ORDER BY news_id DESC LIMIT ? OFFSET ?"
            : "SELECT * FROM news WHERE defunct=? ORDER BY news_id DESC LIMIT ? OFFSET ?";
        if (defunct == null) {
            return jdbc.query(sql, new NewsRowMapper(), limit, offset);
        }
        return jdbc.query(sql, new NewsRowMapper(), defunct, limit, offset);
    }

    public List<News> findByMenu(int menu, int offset, int limit) {
        String sql = "SELECT * FROM news WHERE defunct='N' AND menu=? ORDER BY news_id DESC LIMIT ? OFFSET ?";
        return jdbc.query(sql, new NewsRowMapper(), menu, limit, offset);
    }

    public List<News> findImportant(int limit) {
        String sql = "SELECT * FROM news WHERE defunct='N' AND importance>0 ORDER BY importance DESC, news_id DESC LIMIT ?";
        return jdbc.query(sql, new NewsRowMapper(), limit);
    }

    public int count(String defunct) {
        String sql = defunct == null
            ? "SELECT COUNT(*) FROM news"
            : "SELECT COUNT(*) FROM news WHERE defunct=?";
        return defunct == null
            ? jdbc.queryForObject(sql, Integer.class)
            : jdbc.queryForObject(sql, Integer.class, defunct);
    }

    public int insert(News n) {
        String sql = "INSERT INTO news (user_id, title, content, time, importance, menu, defunct) VALUES (?, ?, ?, NOW(), ?, ?, ?)";
        return jdbc.update(sql, n.getUserId(), n.getTitle(), n.getContent(), n.getImportance(), n.getMenu(), n.getDefunct() != null ? n.getDefunct() : "N");
    }

    public int update(News n) {
        String sql = "UPDATE news SET title=?, content=?, importance=?, menu=?, defunct=? WHERE news_id=?";
        return jdbc.update(sql, n.getTitle(), n.getContent(), n.getImportance(), n.getMenu(), n.getDefunct(), n.getNewsId());
    }

    public int setDefunct(int newsId, String defunct) {
        String sql = "UPDATE news SET defunct=? WHERE news_id=?";
        return jdbc.update(sql, defunct, newsId);
    }

    public int delete(int newsId) {
        String sql = "DELETE FROM news WHERE news_id=?";
        return jdbc.update(sql, newsId);
    }

    private static class NewsRowMapper implements RowMapper<News> {
        @Override
        public News mapRow(ResultSet rs, int rowNum) throws SQLException {
            News n = new News();
            n.setNewsId(rs.getInt("news_id"));
            n.setUserId(rs.getString("user_id"));
            n.setTitle(rs.getString("title"));
            n.setContent(rs.getString("content"));
            n.setTime(rs.getTimestamp("time"));
            n.setImportance(rs.getInt("importance"));
            n.setMenu(rs.getInt("menu"));
            n.setDefunct(rs.getString("defunct"));
            return n;
        }
    }
}
