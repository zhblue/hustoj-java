package com.hustoj.service;

import com.hustoj.db.DB;
import com.hustoj.entity.Problem;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.List;

public class ProblemService {

    private final JdbcTemplate jdbc;

    public ProblemService() {
        this.jdbc = DB.getJdbcTemplate();
    }

    public ProblemService(JdbcTemplate jdbc) {
        this.jdbc = jdbc;
    }

    public Problem findById(int problemId) {
        String sql = "SELECT * FROM problem WHERE problem_id=?";
        List<Problem> list = jdbc.query(sql, new ProblemRowMapper(), problemId);
        return list.isEmpty() ? null : list.get(0);
    }

    public Problem findByIdNotDefunct(int problemId) {
        String sql = "SELECT * FROM problem WHERE problem_id=? AND defunct='N'";
        List<Problem> list = jdbc.query(sql, new ProblemRowMapper(), problemId);
        return list.isEmpty() ? null : list.get(0);
    }

    public List<Problem> findAll(int offset, int limit) {
        String sql = "SELECT * FROM problem WHERE defunct='N' ORDER BY problem_id ASC LIMIT ? OFFSET ?";
        return jdbc.query(sql, new ProblemRowMapper(), limit, offset);
    }

    public List<Problem> findAll(int offset, int limit, String defunct) {
        String sql = defunct == null
            ? "SELECT * FROM problem ORDER BY problem_id ASC LIMIT ? OFFSET ?"
            : "SELECT * FROM problem WHERE defunct=? ORDER BY problem_id ASC LIMIT ? OFFSET ?";
        if (defunct == null) {
            return jdbc.query(sql, new ProblemRowMapper(), limit, offset);
        }
        return jdbc.query(sql, new ProblemRowMapper(), defunct, limit, offset);
    }

    public List<Problem> findByKeyword(String keyword, int offset, int limit) {
        String sql = "SELECT * FROM problem WHERE defunct='N' AND (title LIKE ? OR source LIKE ?) ORDER BY problem_id ASC LIMIT ? OFFSET ?";
        String kw = "%" + keyword + "%";
        return jdbc.query(sql, new ProblemRowMapper(), kw, kw, limit, offset);
    }

    public int count(String defunct) {
        String sql = defunct == null
            ? "SELECT COUNT(*) FROM problem"
            : "SELECT COUNT(*) FROM problem WHERE defunct=?";
        return defunct == null
            ? jdbc.queryForObject(sql, Integer.class)
            : jdbc.queryForObject(sql, Integer.class, defunct);
    }

    public int countByKeyword(String keyword) {
        String sql = "SELECT COUNT(*) FROM problem WHERE defunct='N' AND (title LIKE ? OR source LIKE ?)";
        String kw = "%" + keyword + "%";
        return jdbc.queryForObject(sql, Integer.class, kw, kw);
    }

    public int insert(Problem p) {
        String sql = "INSERT INTO problem (title, description, input, output, sample_input, sample_output, " +
                     "spj, hint, source, in_date, time_limit, memory_limit, defunct, accepted, submit, solved, remote_oj, remote_id) " +
                     "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, NOW(), ?, ?, 'N', 0, 0, 0, ?, ?)";
        return jdbc.update(sql, p.getTitle(), p.getDescription(), p.getInput(), p.getOutput(),
                p.getSampleInput(), p.getSampleOutput(), p.getSpj(), p.getHint(), p.getSource(),
                p.getTimeLimit(), p.getMemoryLimit(), p.getRemoteOj(), p.getRemoteId());
    }

    public int update(Problem p) {
        String sql = "UPDATE problem SET title=?, description=?, input=?, output=?, sample_input=?, sample_output=?, " +
                     "spj=?, hint=?, source=?, time_limit=?, memory_limit=?, defunct=? WHERE problem_id=?";
        return jdbc.update(sql, p.getTitle(), p.getDescription(), p.getInput(), p.getOutput(),
                p.getSampleInput(), p.getSampleOutput(), p.getSpj(), p.getHint(), p.getSource(),
                p.getTimeLimit(), p.getMemoryLimit(), p.getDefunct(), p.getProblemId());
    }

    public int setDefunct(int problemId, String defunct) {
        String sql = "UPDATE problem SET defunct=? WHERE problem_id=?";
        return jdbc.update(sql, defunct, problemId);
    }

    public int delete(int problemId) {
        String sql = "DELETE FROM problem WHERE problem_id=?";
        return jdbc.update(sql, problemId);
    }

    public int incrementAccepted(int problemId) {
        String sql = "UPDATE problem SET accepted=accepted+1 WHERE problem_id=?";
        return jdbc.update(sql, problemId);
    }

    public int incrementSubmit(int problemId) {
        String sql = "UPDATE problem SET submit=submit+1 WHERE problem_id=?";
        return jdbc.update(sql, problemId);
    }

    public int incrementSolved(int problemId) {
        String sql = "UPDATE problem SET solved=solved+1 WHERE problem_id=?";
        return jdbc.update(sql, problemId);
    }

    public int copy(int problemId, String userId) {
        Problem original = findById(problemId);
        if (original == null) return -1;
        original.setProblemId(0);
        original.setTitle(original.getTitle() + " (Copy)");
        return insert(original);
    }

    public int changeId(int oldId, int newId) {
        String sql = "UPDATE problem SET problem_id=? WHERE problem_id=?";
        return jdbc.update(sql, newId, oldId);
    }

    public int getMaxProblemId() {
        String sql = "SELECT MAX(problem_id) FROM problem";
        Integer max = jdbc.queryForObject(sql, Integer.class);
        return max == null ? 0 : max;
    }

    public List<Problem> getHotProblems(int limit) {
        String sql = "SELECT * FROM problem WHERE defunct='N' ORDER BY accepted DESC LIMIT ?";
        return jdbc.query(sql, new ProblemRowMapper(), limit);
    }

    public List<Problem> getRecentProblems(int limit) {
        String sql = "SELECT * FROM problem WHERE defunct='N' ORDER BY in_date DESC LIMIT ?";
        return jdbc.query(sql, new ProblemRowMapper(), limit);
    }

    private static class ProblemRowMapper implements RowMapper<Problem> {
        @Override
        public Problem mapRow(ResultSet rs, int rowNum) throws SQLException {
            Problem p = new Problem();
            p.setProblemId(rs.getInt("problem_id"));
            p.setTitle(rs.getString("title"));
            p.setDescription(rs.getString("description"));
            p.setInput(rs.getString("input"));
            p.setOutput(rs.getString("output"));
            p.setSampleInput(rs.getString("sample_input"));
            p.setSampleOutput(rs.getString("sample_output"));
            p.setSpj(rs.getString("spj"));
            p.setHint(rs.getString("hint"));
            p.setSource(rs.getString("source"));
            p.setInDate(rs.getTimestamp("in_date"));
            p.setTimeLimit(rs.getBigDecimal("time_limit"));
            p.setMemoryLimit(rs.getInt("memory_limit"));
            p.setDefunct(rs.getString("defunct"));
            p.setAccepted(rs.getInt("accepted"));
            p.setSubmit(rs.getInt("submit"));
            p.setSolved(rs.getInt("solved"));
            p.setRemoteOj(rs.getString("remote_oj"));
            p.setRemoteId(rs.getString("remote_id"));
            return p;
        }
    }
}
