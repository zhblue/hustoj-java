package com.hustoj.servlet;

import com.hustoj.db.DB;
import com.hustoj.entity.Contest;
import com.hustoj.entity.ContestProblem;
import com.hustoj.entity.Solution;
import com.hustoj.entity.User;
import com.hustoj.service.ContestService;
import com.hustoj.service.SolutionService;
import com.hustoj.service.UserService;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.*;

public class ContestRankOIServlet extends BaseServlet {

    private final ContestService contestService = new ContestService();
    private final SolutionService solutionService = new SolutionService();
    private final UserService userService = new UserService();

    public void list(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int contestId = paramInt(req, "cid", 0);
        if (contestId <= 0) {
            setAttr(req, "error", "Invalid contest ID");
            forward(req, resp, "/WEB-INF/views/error.jsp");
            return;
        }

        Contest contest = contestService.findById(contestId);
        if (contest == null) {
            setAttr(req, "error", "Contest not found");
            forward(req, resp, "/WEB-INF/views/error.jsp");
            return;
        }

        List<ContestProblem> problems = contestService.getContestProblems(contestId);
        List<Solution> solutions = solutionService.findByContestId(contestId, 0, 10000);

        // OI mode: best score per problem per user
        Map<String, OIRank> rankMap = new LinkedHashMap<>();
        Timestamp startTime = contest.getStartTime();

        for (Solution s : solutions) {
            String uid = s.getUserId();
            int num = s.getNum();
            if (num < 0 || num >= problems.size()) continue;

            OIRank rank = rankMap.computeIfAbsent(uid, k -> new OIRank(uid, problems.size()));
            
            // OI: use pass_rate as score (0.0 to 1.0)
            double score = s.getPassRate();
            if (score > rank.scores[num]) {
                rank.scores[num] = score;
            }
        }

        // Sort by total score
        List<OIRank> ranks = new ArrayList<>(rankMap.values());
        ranks.sort((a, b) -> Double.compare(b.totalScore, a.totalScore));

        // Attach user info
        List<Map<String, Object>> rankList = new ArrayList<>();
        int pos = 1;
        for (OIRank cr : ranks) {
            User user = userService.findById(cr.userId);
            if (user == null) continue;
            Map<String, Object> row = new LinkedHashMap<>();
            row.put("rank", pos++);
            row.put("userId", cr.userId);
            row.put("nick", user.getNick());
            row.put("school", user.getSchool());
            row.put("totalScore", cr.totalScore);
            row.put("scores", cr.scores);
            rankList.add(row);
        }

        setAttr(req, "contest", contest);
        setAttr(req, "problems", problems);
        setAttr(req, "rankList", rankList);
        setAttr(req, "OJ_NAME", DB.getOjName());
        forward(req, resp, "/WEB-INF/views/contest/contest-rank-oi.jsp");
    }

    private static class OIRank {
        String userId;
        double[] scores;
        double totalScore;

        OIRank(String userId, int problemCount) {
            this.userId = userId;
            this.scores = new double[problemCount];
        }
    }
}
