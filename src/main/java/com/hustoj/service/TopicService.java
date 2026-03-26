package com.hustoj.service;

import com.hustoj.db.DB;
import com.hustoj.entity.Reply;
import com.hustoj.entity.Topic;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.List;

public class TopicService {

    private final JdbcTemplate jdbc;

    public TopicService() {
        this.jdbc = DB.getJdbcTemplate();
    }

    public TopicService(JdbcTemplate jdbc) {
        this.jdbc = jdbc;
    }

    public Topic findById(int tid) {
        String sql = "SELECT * FROM topic WHERE tid=?";
        List<Topic> list = jdbc.query(sql, new TopicRowMapper(), tid);
        return list.isEmpty() ? null : list.get(0);
    }

    public List<Topic> findAll(int offset, int limit) {
        String sql = "SELECT * FROM topic ORDER BY tid DESC LIMIT ? OFFSET ?";
        return jdbc.query(sql, new TopicRowMapper(), limit, offset);
    }

    public List<Topic> findByProblemId(int problemId, int offset, int limit) {
        String sql = "SELECT * FROM topic WHERE pid=? ORDER BY tid DESC LIMIT ? OFFSET ?";
        return jdbc.query(sql, new TopicRowMapper(), problemId, limit, offset);
    }

    public List<Topic> findByContestId(int contestId, int offset, int limit) {
        String sql = "SELECT * FROM topic WHERE cid=? ORDER BY tid DESC LIMIT ? OFFSET ?";
        return jdbc.query(sql, new TopicRowMapper(), contestId, limit, offset);
    }

    public int count() {
        return jdbc.queryForObject("SELECT COUNT(*) FROM topic", Integer.class);
    }

    public int createTopic(String title, int problemId, Integer contestId, String authorId) {
        String sql = "INSERT INTO topic (title, status, top_level, cid, pid, author_id) VALUES (?, 0, 0, ?, ?, ?)";
        return jdbc.update(sql, title, contestId, problemId, authorId);
    }

    public int addReply(int topicId, String authorId, String content, String ip) {
        String sql = "INSERT INTO reply (author_id, time, content, topic_id, status, ip) VALUES (?, NOW(), ?, ?, 0, ?)";
        return jdbc.update(sql, authorId, content, topicId, ip);
    }

    public List<Reply> getReplies(int topicId) {
        String sql = "SELECT * FROM reply WHERE topic_id=? ORDER BY rid ASC";
        return jdbc.query(sql, new ReplyRowMapper(), topicId);
    }

    private static class TopicRowMapper implements RowMapper<Topic> {
        @Override
        public Topic mapRow(ResultSet rs, int rowNum) throws SQLException {
            Topic t = new Topic();
            t.setTid(rs.getInt("tid"));
            t.setTitle(rs.getString("title"));
            t.setStatus(rs.getInt("status"));
            t.setTopLevel(rs.getInt("top_level"));
            Integer cid = rs.getInt("cid");
            t.setCid(cid == 0 ? null : cid);
            t.setPid(rs.getInt("pid"));
            t.setAuthorId(rs.getString("author_id"));
            return t;
        }
    }

    private static class ReplyRowMapper implements RowMapper<Reply> {
        @Override
        public Reply mapRow(ResultSet rs, int rowNum) throws SQLException {
            Reply r = new Reply();
            r.setRid(rs.getInt("rid"));
            r.setAuthorId(rs.getString("author_id"));
            r.setTime(rs.getTimestamp("time"));
            r.setContent(rs.getString("content"));
            r.setTopicId(rs.getInt("topic_id"));
            r.setStatus(rs.getInt("status"));
            r.setIp(rs.getString("ip"));
            return r;
        }
    }
}
