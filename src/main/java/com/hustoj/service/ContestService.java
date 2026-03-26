package com.hustoj.service;

import com.hustoj.db.DB;
import com.hustoj.entity.Contest;
import com.hustoj.entity.ContestProblem;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.List;

public class ContestService {

    private final JdbcTemplate jdbc;

    public ContestService() {
        this.jdbc = DB.getJdbcTemplate();
    }

    public ContestService(JdbcTemplate jdbc) {
        this.jdbc = jdbc;
    }

    public Contest findById(int contestId) {
        String sql = "SELECT * FROM contest WHERE contest_id=?";
        List<Contest> list = jdbc.query(sql, new ContestRowMapper(), contestId);
        return list.isEmpty() ? null : list.get(0);
    }

    public List<Contest> findAll(int offset, int limit) {
        String sql = "SELECT * FROM contest ORDER BY contest_id DESC LIMIT ? OFFSET ?";
        return jdbc.query(sql, new ContestRowMapper(), limit, offset);
    }

    public List<Contest> findAll(int offset, int limit, String defunct) {
        String sql = defunct == null
            ? "SELECT * FROM contest ORDER BY contest_id DESC LIMIT ? OFFSET ?"
            : "SELECT * FROM contest WHERE defunct=? ORDER BY contest_id DESC LIMIT ? OFFSET ?";
        if (defunct == null) {
            return jdbc.query(sql, new ContestRowMapper(), limit, offset);
        }
        return jdbc.query(sql, new ContestRowMapper(), defunct, limit, offset);
    }

    public List<Contest> findRunning() {
        String sql = "SELECT * FROM contest WHERE defunct='N' AND start_time<=NOW() AND end_time>NOW() ORDER BY contest_id DESC";
        return jdbc.query(sql, new ContestRowMapper());
    }

    public List<Contest> findUpcoming(int limit) {
        String sql = "SELECT * FROM contest WHERE defunct='N' AND start_time>NOW() ORDER BY start_time ASC LIMIT ?";
        return jdbc.query(sql, new ContestRowMapper(), limit);
    }

    public List<Contest> findRecent(int limit) {
        String sql = "SELECT * FROM contest WHERE defunct='N' AND end_time<NOW() ORDER BY end_time DESC LIMIT ?";
        return jdbc.query(sql, new ContestRowMapper(), limit);
    }

    public int count(String defunct) {
        String sql = defunct == null
            ? "SELECT COUNT(*) FROM contest"
            : "SELECT COUNT(*) FROM contest WHERE defunct=?";
        return defunct == null
            ? jdbc.queryForObject(sql, Integer.class)
            : jdbc.queryForObject(sql, Integer.class, defunct);
    }

    public int insert(Contest c) {
        String sql = "INSERT INTO contest (title, start_time, end_time, defunct, description, private, langmask, password, contest_type, subnet, user_id) " +
                     "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        return jdbc.update(sql, c.getTitle(), c.getStartTime(), c.getEndTime(),
                c.getDefunct() != null ? c.getDefunct() : "N", c.getDescription(),
                c.getPrivate_(), c.getLangMask(), c.getPassword(),
                c.getContestType(), c.getSubnet(), c.getUserId());
    }

    public int update(Contest c) {
        String sql = "UPDATE contest SET title=?, start_time=?, end_time=?, defunct=?, description=?, private=?, langmask=?, password=?, contest_type=?, subnet=? WHERE contest_id=?";
        return jdbc.update(sql, c.getTitle(), c.getStartTime(), c.getEndTime(),
                c.getDefunct(), c.getDescription(), c.getPrivate_(), c.getLangMask(),
                c.getPassword(), c.getContestType(), c.getSubnet(), c.getContestId());
    }

    public int setDefunct(int contestId, String defunct) {
        String sql = "UPDATE contest SET defunct=? WHERE contest_id=?";
        return jdbc.update(sql, defunct, contestId);
    }

    public int delete(int contestId) {
        String sql = "DELETE FROM contest WHERE contest_id=?";
        return jdbc.update(sql, contestId);
    }

    // Contest Problem
    public List<ContestProblem> getContestProblems(int contestId) {
        String sql = "SELECT * FROM contest_problem WHERE contest_id=? ORDER BY num ASC";
        return jdbc.query(sql, new ContestProblemRowMapper(), contestId);
    }

    public ContestProblem getContestProblem(int contestId, int num) {
        String sql = "SELECT * FROM contest_problem WHERE contest_id=? AND num=?";
        List<ContestProblem> list = jdbc.query(sql, new ContestProblemRowMapper(), contestId, num);
        return list.isEmpty() ? null : list.get(0);
    }

    public ContestProblem getContestProblemByPid(int contestId, int problemId) {
        String sql = "SELECT * FROM contest_problem WHERE contest_id=? AND problem_id=?";
        List<ContestProblem> list = jdbc.query(sql, new ContestProblemRowMapper(), contestId, problemId);
        return list.isEmpty() ? null : list.get(0);
    }

    public int addContestProblem(int contestId, int problemId, String title, int num) {
        String sql = "INSERT INTO contest_problem (problem_id, contest_id, title, num) VALUES (?, ?, ?, ?)";
        return jdbc.update(sql, problemId, contestId, title, num);
    }

    public int removeContestProblem(int contestId, int num) {
        String sql = "DELETE FROM contest_problem WHERE contest_id=? AND num=?";
        return jdbc.update(sql, contestId, num);
    }

    public int clearContestProblems(int contestId) {
        String sql = "DELETE FROM contest_problem WHERE contest_id=?";
        return jdbc.update(sql, contestId);
    }

    public int updateContestProblem(int contestId, int num, String title) {
        String sql = "UPDATE contest_problem SET title=? WHERE contest_id=? AND num=?";
        return jdbc.update(sql, title, contestId, num);
    }

    public int incrementContestProblemAccepted(int contestId, int problemId) {
        String sql = "UPDATE contest_problem SET c_accepted=c_accepted+1 WHERE contest_id=? AND problem_id=?";
        return jdbc.update(sql, contestId, problemId);
    }

    public int incrementContestProblemSubmit(int contestId, int problemId) {
        String sql = "UPDATE contest_problem SET c_submit=c_submit+1 WHERE contest_id=? AND problem_id=?";
        return jdbc.update(sql, contestId, problemId);
    }

    public int getProblemNum(int contestId, int problemId) {
        String sql = "SELECT num FROM contest_problem WHERE contest_id=? AND problem_id=?";
        List<Integer> list = jdbc.queryForList(sql, Integer.class, contestId, problemId);
        return list.isEmpty() ? -1 : list.get(0);
    }

    public int getProblemIdByNum(int contestId, int num) {
        String sql = "SELECT problem_id FROM contest_problem WHERE contest_id=? AND num=?";
        List<Integer> list = jdbc.queryForList(sql, Integer.class, contestId, num);
        return list.isEmpty() ? -1 : list.get(0);
    }

    private static class ContestRowMapper implements RowMapper<Contest> {
        @Override
        public Contest mapRow(ResultSet rs, int rowNum) throws SQLException {
            Contest c = new Contest();
            c.setContestId(rs.getInt("contest_id"));
            c.setTitle(rs.getString("title"));
            c.setStartTime(rs.getTimestamp("start_time"));
            c.setEndTime(rs.getTimestamp("end_time"));
            c.setDefunct(rs.getString("defunct"));
            c.setDescription(rs.getString("description"));
            c.setPrivate_(rs.getInt("private"));
            c.setLangMask(rs.getInt("langmask"));
            c.setPassword(rs.getString("password"));
            c.setContestType(rs.getInt("contest_type"));
            c.setSubnet(rs.getString("subnet"));
            c.setUserId(rs.getString("user_id"));
            return c;
        }
    }

    private static class ContestProblemRowMapper implements RowMapper<ContestProblem> {
        @Override
        public ContestProblem mapRow(ResultSet rs, int rowNum) throws SQLException {
            ContestProblem cp = new ContestProblem();
            cp.setProblemId(rs.getInt("problem_id"));
            cp.setContestId(rs.getInt("contest_id"));
            cp.setTitle(rs.getString("title"));
            cp.setNum(rs.getInt("num"));
            cp.setcAccepted(rs.getInt("c_accepted"));
            cp.setcSubmit(rs.getInt("c_submit"));
            return cp;
        }
    }
}
