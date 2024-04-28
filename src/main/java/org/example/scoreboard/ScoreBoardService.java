package org.example.scoreboard;

import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
public class ScoreBoardService {

    private final ScoreBoard scoreBoard;

    public void startMatch(String homeTeam, String awayTeam) {
        scoreBoard.addMatch(new Match(new TeamScore(homeTeam), new TeamScore(awayTeam)));
    }

    public void updateScore(TeamScore homeTeamScore, TeamScore awayTeamScore) {

    }

    public void finishMatch(String homeTeam, String awayTeam) {

    }

    public List<Match> getMatchesInProgressSummary() {
        return scoreBoard.getBoard();
    }
}
