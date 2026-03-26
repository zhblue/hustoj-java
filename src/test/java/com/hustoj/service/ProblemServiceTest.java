package com.hustoj.service;

import com.hustoj.entity.Problem;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.jdbc.core.JdbcTemplate;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ProblemServiceTest {

    private ProblemService problemService;
    private JdbcTemplate jdbc;

    @BeforeEach
    void setUp() {
        jdbc = MySqlTestUtil.createJdbc();
        MySqlTestUtil.cleanTables(jdbc, "problem", "users");
        problemService = new ProblemService(jdbc);
    }

    @AfterEach
    void tearDown() {
        // Tables already cleaned before each test
    }

    private Problem makeProblem(String title) {
        Problem p = new Problem();
        p.setTitle(title);
        p.setDescription("Description");
        p.setInput("Input");
        p.setOutput("Output");
        p.setHint("Hint");
        p.setTimeLimit(new BigDecimal("1.000"));
        p.setMemoryLimit(256);
        p.setSpj("0");
        p.setSource("Test");
        return p;
    }

    @Test
    void testInsertAndFindById() {
        Problem p = makeProblem("Test Problem");
        p.setDescription("Description");
        p.setInput("Input");
        p.setOutput("Output");
        p.setSampleInput("1 2");
        p.setSampleOutput("3");
        p.setTimeLimit(new BigDecimal("1.000"));
        p.setMemoryLimit(256);
        p.setSpj("0");
        p.setSource("Test");
        p.setHint("Hint");
        p.setRemoteOj("");
        p.setRemoteId("");

        int id = problemService.insert(p);
        assertTrue(id > 0);

        Problem found = problemService.findById(id);
        assertNotNull(found);
        assertEquals("Test Problem", found.getTitle());
        assertEquals("Description", found.getDescription());
        assertEquals(0, new BigDecimal("1.000").compareTo(found.getTimeLimit()));
        assertEquals(256, found.getMemoryLimit());
    }

    @Test
    void testFindByIdNotDefunct() {
        Problem p = makeProblem("Active Problem");
        int id = problemService.insert(p);
        problemService.setDefunct(id, "Y");

        assertNotNull(problemService.findByIdNotDefunct(id));
        assertNull(problemService.findByIdNotDefunct(id));
    }

    @Test
    void testFindAll() {
        for (int i = 0; i < 5; i++) {
            problemService.insert(makeProblem("Problem " + i));
        }
        List<Problem> all = problemService.findAll(0, 10, "N");
        assertTrue(all.size() >= 5);
    }

    @Test
    void testUpdate() {
        Problem p = makeProblem("Original Title");
        int id = problemService.insert(p);

        p = problemService.findById(id);
        p.setTitle("Updated Title");
        problemService.update(p);

        Problem updated = problemService.findById(id);
        assertEquals("Updated Title", updated.getTitle());
    }

    @Test
    void testSetDefunct() {
        Problem p = makeProblem("Test");
        int id = problemService.insert(p);

        problemService.setDefunct(id, "Y");
        assertEquals("Y", problemService.findById(id).getDefunct());
    }

    @Test
    void testIncrementCounts() {
        Problem p = makeProblem("Test");
        int id = problemService.insert(p);

        Problem found = problemService.findById(id);
        assertEquals(0, found.getAccepted());
        assertEquals(0, found.getSubmit());

        problemService.incrementSubmit(id);
        found = problemService.findById(id);
        assertEquals(1, found.getSubmit());

        problemService.incrementAccepted(id);
        found = problemService.findById(id);
        assertEquals(1, found.getAccepted());
    }

    @Test
    void testCount() {
        assertEquals(0, problemService.count(null));
        problemService.insert(makeProblem("Test"));
        assertEquals(1, problemService.count(null));
    }

    @Test
    void testCopy() {
        Problem p = makeProblem("Original");
        int originalId = problemService.insert(p);

        int copyId = problemService.copy(originalId, "admin");
        assertTrue(copyId > originalId);

        Problem original = problemService.findById(originalId);
        Problem copy = problemService.findById(copyId);
        assertEquals(original.getTitle() + " (Copy)", copy.getTitle());
        assertEquals(original.getDescription(), copy.getDescription());
    }

    @Test
    void testGetMaxProblemId() {
        assertEquals(0, problemService.getMaxProblemId());
        int id = problemService.insert(makeProblem("Test"));
        assertEquals(id, problemService.getMaxProblemId());
    }
}
