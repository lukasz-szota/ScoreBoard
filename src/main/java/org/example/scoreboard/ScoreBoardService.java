package org.example.scoreboard;

import lombok.RequiredArgsConstructor;

import java.util.Collections;
import java.util.List;

@RequiredArgsConstructor
public class ScoreBoardService {

    private final ScoreBoard scoreBoard;

    public void startMatch(String homeTeam, String awayTeam) {
        scoreBoard.addMatch(new Match(homeTeam, awayTeam));
    }

    public void updateSore(String homeTeam, String awayTeam, int homeTeamScore, int awayTeamScore) {

    }

    public void finishMatch(String homeTeam, String awayTeam) {

    }

    public List<Match> getMatchesInProgressSummary() {
        return Collections.emptyList();
    }
}
