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

    // Contest type bitmask constants (matching PHP)
    private static final int CONTEST_TYPE_ACM = 1;  // ACM mode
    private static final int CONTEST_TYPE_OI = 2;   // OI mode

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

        // Determine contest mode
        boolean isOIMode = (contest.getContestType() & CONTEST_TYPE_OI) != 0;
        boolean isACMMode = (contest.getContestType() & CONTEST_TYPE_ACM) != 0;
        
        Timestamp startTime = contest.getStartTime();
        Timestamp endTime = contest.getEndTime();
        Timestamp now = new Timestamp(System.currentTimeMillis());
        
        // Freeze board check (matching PHP's contest_locked with level=8 or 16)
        boolean frozen = false;
        int freezeDelay = DB.getRankLockDelay(); // seconds after contest start to freeze
        if (contest.isRunning()) {
            long elapsed = now.getTime() - startTime.getTime();
            long freezePoint = freezeDelay * 1000L;
            if (elapsed >= freezePoint) {
                frozen = true;
            }
        } else if (contest.isEnded()) {
            frozen = false; // Unfreeze after contest ends
        }

        // Build rank list
        Map<String, ContestRank> rankMap = new LinkedHashMap<>();
        
        for (Solution s : solutions) {
            String uid = s.getUserId();
            int num = s.getNum();
            if (num < 0 || num >= problems.size()) continue;
            
            ContestRank rank = rankMap.computeIfAbsent(uid, k -> new ContestRank(uid, problems.size()));

            if (isOIMode) {
                // OI mode: accumulate scores based on pass rate
                if (s.getPassRate() > rank.passRates[num]) {
                    rank.passRates[num] = s.getPassRate();
                }
            } else {
                // ACM mode: first AC gets score, penalty for wrong submissions
                if (s.getResult() == 4) { // AC
                    if (!rank.solved[num]) {
                        rank.solved[num] = true;
                        rank.score += 1;
                        // Penalty: time since start + 20 min per wrong submission
                        long penaltyTime = (s.getInDate().getTime() - startTime.getTime()) / 1000;
                        rank.totalTime += penaltyTime + (rank.wrongCount[num] * 20 * 60);
                    }
                } else if (s.getResult() != 0 && s.getResult() != 1) {
                    // Wrong submission (not PD, not PR)
                    if (!rank.solved[num]) {
                        rank.wrongCount[num]++;
                    }
                }
            }
        }

        // Sort by score desc, time asc (ACM) or by score desc (OI)
        List<ContestRank> ranks = new ArrayList<>(rankMap.values());
        if (isOIMode) {
            // OI mode: sort by total score only
            ranks.sort((a, b) -> {
                double scoreA = a.getTotalScore();
                double scoreB = b.getTotalScore();
                if (Math.abs(scoreA - scoreB) > 0.001) {
                    return Double.compare(scoreB, scoreA); // Higher score first
                }
                return a.userId.compareTo(b.userId); // Then alphabetical
            });
        } else {
            // ACM mode: sort by solved desc, time asc
            ranks.sort((a, b) -> {
                if (b.score != a.score) return Integer.compare(b.score, a.score);
                return Long.compare(a.totalTime, b.totalTime); // Lower time = better
            });
        }

        // Attach user info and build final rank list
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
            row.put("score", isOIMode ? cr.getTotalScore() : cr.score);
            row.put("totalTime", isOIMode ? 0 : cr.totalTime);
            row.put("solved", isOIMode ? cr.passRates : cr.solved);
            rankList.add(row);
        }

        setAttr(req, "contest", contest);
        setAttr(req, "problems", problems);
        setAttr(req, "rankList", rankList);
        setAttr(req, "frozen", frozen);
        setAttr(req, "isOIMode", isOIMode);
        setAttr(req, "OJ_NAME", DB.getOjName());
        forward(req, resp, "/WEB-INF/views/contest/contest-rank.jsp");
    }

    private static class ContestRank {
        String userId;
        boolean[] solved;
        int score;
        long totalTime;
        int[] wrongCount;
        double[] passRates;

        ContestRank(String userId, int problemCount) {
            this.userId = userId;
            this.solved = new boolean[problemCount];
            this.wrongCount = new int[problemCount];
            this.passRates = new double[problemCount];
            this.score = 0;
            this.totalTime = 0;
        }

        // Get total OI score (sum of pass rates * 100)
        double getTotalScore() {
            double total = 0;
            for (double rate : passRates) {
                total += rate * 100;
            }
            return total;
        }
    }
}
