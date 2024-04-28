package org.example.scoreboard;

import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
public class ScoreBoardService {

    private final ScoreBoard scoreBoard;

    public void startMatch(String homeTeam, String awayTeam) {
        scoreBoard.addMatch(new Match(new TeamScores(homeTeam), new TeamScores(awayTeam)));
    }

    public void updateSore(TeamScores homeTeamScores, TeamScores awayTeamScores) {

    }

    public void finishMatch(String homeTeam, String awayTeam) {

    }

    public List<Match> getMatchesInProgressSummary() {
        return scoreBoard.getBoard();
    }
}
