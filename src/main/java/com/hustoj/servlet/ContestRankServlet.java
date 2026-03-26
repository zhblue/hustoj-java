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

public class ContestRankServlet extends BaseServlet {

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

        // Build rank list
        Map<String, ContestRank> rankMap = new LinkedHashMap<>();
        Timestamp startTime = contest.getStartTime();
        Timestamp endTime = contest.getEndTime();
        Timestamp now = new Timestamp(System.currentTimeMillis());
        
        // Lock board check
        boolean locked = false;
        if (contest.isEnded()) {
            locked = false;
        } else if (contest.isRunning()) {
            long totalDuration = endTime.getTime() - startTime.getTime();
            long elapsed = now.getTime() - startTime.getTime();
            double lockPercent = DB.getRankLockPercent();
            if (lockPercent > 0 && elapsed > totalDuration * lockPercent) {
                locked = true;
            }
        }

        for (Solution s : solutions) {
            if (s.getResult() != 4 && s.getResult() != 0) continue; // Only AC and pending
            String uid = s.getUserId();
            int num = s.getNum();
            if (num < 0 || num >= problems.size()) continue;

            ContestRank rank = rankMap.computeIfAbsent(uid, k -> new ContestRank(uid));
            
            if (s.getResult() == 4) {
                if (!rank.solved[num]) {
                    rank.solved[num] = true;
                    rank.score += 1;
                    long penalty = (s.getInDate().getTime() - startTime.getTime()) / 1000;
                    rank.totalTime += penalty;
                }
            }
        }

        // Sort by score desc, time asc
        List<ContestRank> ranks = new ArrayList<>(rankMap.values());
        ranks.sort((a, b) -> {
            if (b.score != a.score) return Integer.compare(b.score, a.score);
            return Long.compare(a.totalTime, b.totalTime);
        });

        // Attach user info
        List<Map<String, Object>> rankList = new ArrayList<>();
        int pos = 1;
        for (ContestRank cr : ranks) {
            User user = userService.findById(cr.userId);
            if (user == null) continue;
            Map<String, Object> row = new LinkedHashMap<>();
            row.put("rank", pos++);
            row.put("userId", cr.userId);
            row.put("nick", user.getNick());
            row.put("school", user.getSchool());
            row.put("score", cr.score);
            row.put("totalTime", cr.totalTime);
            row.put("solved", cr.solved);
            rankList.add(row);
        }

        setAttr(req, "contest", contest);
        setAttr(req, "problems", problems);
        setAttr(req, "rankList", rankList);
        setAttr(req, "locked", locked);
        setAttr(req, "OJ_NAME", DB.getOjName());
        forward(req, resp, "/WEB-INF/views/contest/contest-rank.jsp");
    }

    private static class ContestRank {
        String userId;
        boolean[] solved;
        int score;
        long totalTime;

        ContestRank(String userId) {
            this.userId = userId;
        }
    }
}
