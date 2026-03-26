package com.hustoj.service;

import com.hustoj.entity.Contest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabase;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;

import java.sql.Timestamp;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ContestServiceTest {

    private ContestService contestService;
    private EmbeddedDatabase db;

    @BeforeEach
    void setUp() {
        db = new EmbeddedDatabaseBuilder()
            .setType(EmbeddedDatabaseType.H2)
            .addScript("schema.sql")
            .build();
        JdbcTemplate jdbc = new JdbcTemplate(db);
        contestService = new ContestService(jdbc);
    }

    @AfterEach
    void tearDown() {
        if (db != null) db.shutdown();
    }

    @Test
    void testInsertAndFindById() {
        Contest c = new Contest();
        c.setTitle("Test Contest");
        c.setStartTime(new Timestamp(System.currentTimeMillis()));
        c.setEndTime(new Timestamp(System.currentTimeMillis() + 3600000));
        c.setDescription("Test Description");
        c.setUserId("admin");
        c.setDefunct("N");
        c.setPrivate_(0);
        c.setContestType(0);
        c.setLangMask(0);
        c.setPassword("");
        c.setSubnet("");

        int id = contestService.insert(c);
        assertTrue(id > 0);

        Contest found = contestService.findById(id);
        assertNotNull(found);
        assertEquals("Test Contest", found.getTitle());
        assertEquals("Test Description", found.getDescription());
    }

    @Test
    void testUpdate() {
        Contest c = new Contest();
        c.setTitle("Original Title");
        c.setStartTime(new Timestamp(System.currentTimeMillis()));
        c.setEndTime(new Timestamp(System.currentTimeMillis() + 3600000));
        c.setUserId("admin");
        c.setDefunct("N");
        c.setPrivate_(0);
        c.setContestType(0);
        c.setLangMask(0);
        int id = contestService.insert(c);

        c = contestService.findById(id);
        c.setTitle("Updated Title");
        contestService.update(c);

        assertEquals("Updated Title", contestService.findById(id).getTitle());
    }

    @Test
    void testSetDefunct() {
        Contest c = new Contest();
        c.setTitle("Test");
        c.setStartTime(new Timestamp(System.currentTimeMillis()));
        c.setEndTime(new Timestamp(System.currentTimeMillis() + 3600000));
        c.setUserId("admin");
        c.setDefunct("N");
        c.setPrivate_(0);
        c.setContestType(0);
        c.setLangMask(0);
        int id = contestService.insert(c);

        contestService.setDefunct(id, "Y");
        assertEquals("Y", contestService.findById(id).getDefunct());
    }

    @Test
    void testContestProblems() {
        Contest c = new Contest();
        c.setTitle("Test");
        c.setStartTime(new Timestamp(System.currentTimeMillis()));
        c.setEndTime(new Timestamp(System.currentTimeMillis() + 3600000));
        c.setUserId("admin");
        c.setDefunct("N");
        c.setPrivate_(0);
        c.setContestType(0);
        c.setLangMask(0);
        int cid = contestService.insert(c);

        contestService.addContestProblem(cid, 1001, "Problem A", 0);
        contestService.addContestProblem(cid, 1002, "Problem B", 1);
        contestService.addContestProblem(cid, 1003, "Problem C", 2);

        List<com.hustoj.entity.ContestProblem> problems = contestService.getContestProblems(cid);
        assertEquals(3, problems.size());
        assertEquals(1001, problems.get(0).getProblemId());
        assertEquals(0, problems.get(0).getNum());
    }

    @Test
    void testGetContestProblem() {
        Contest c = new Contest();
        c.setTitle("Test");
        c.setStartTime(new Timestamp(System.currentTimeMillis()));
        c.setEndTime(new Timestamp(System.currentTimeMillis() + 3600000));
        c.setUserId("admin");
        c.setDefunct("N");
        c.setPrivate_(0);
        c.setContestType(0);
        c.setLangMask(0);
        int cid = contestService.insert(c);

        contestService.addContestProblem(cid, 1001, "Problem A", 0);

        var cp = contestService.getContestProblem(cid, 0);
        assertNotNull(cp);
        assertEquals(1001, cp.getProblemId());

        var cpByPid = contestService.getContestProblemByPid(cid, 1001);
        assertNotNull(cpByPid);
        assertEquals(0, cpByPid.getNum());
    }

    @Test
    void testIncrementContestProblem() {
        Contest c = new Contest();
        c.setTitle("Test");
        c.setStartTime(new Timestamp(System.currentTimeMillis()));
        c.setEndTime(new Timestamp(System.currentTimeMillis() + 3600000));
        c.setUserId("admin");
        c.setDefunct("N");
        c.setPrivate_(0);
        c.setContestType(0);
        c.setLangMask(0);
        int cid = contestService.insert(c);

        contestService.addContestProblem(cid, 1001, "Problem A", 0);

        var cp = contestService.getContestProblem(cid, 0);
        assertEquals(0, cp.getcAccepted());
        assertEquals(0, cp.getcSubmit());

        contestService.incrementContestProblemSubmit(cid, 1001);
        cp = contestService.getContestProblem(cid, 0);
        assertEquals(1, cp.getcSubmit());

        contestService.incrementContestProblemAccepted(cid, 1001);
        cp = contestService.getContestProblem(cid, 0);
        assertEquals(1, cp.getcAccepted());
    }

    @Test
    void testContestRunning() {
        Contest c = new Contest();
        c.setTitle("Running Contest");
        c.setStartTime(new Timestamp(System.currentTimeMillis() - 1000));
        c.setEndTime(new Timestamp(System.currentTimeMillis() + 3600000));
        c.setUserId("admin");
        c.setDefunct("N");
        c.setPrivate_(0);
        c.setContestType(0);
        c.setLangMask(0);
        contestService.insert(c);

        List<Contest> running = contestService.findRunning();
        assertTrue(running.size() >= 1);
    }
}
