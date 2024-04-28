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
        Match match = findMatch(homeTeamScore.getTeamName(), awayTeamScore.getTeamName());
        match.getHomeTeamScore().setScore(homeTeamScore.getScore());
        match.getAwayTeamScore().setScore(awayTeamScore.getScore());
    }

    private Match findMatch(final String homeTeam, final String awayTeam) {
        return scoreBoard.getBoard().stream()
                .filter(m -> m.getHomeTeamScore().getTeamName().equals(homeTeam) && m.getAwayTeamScore().getTeamName().equals(awayTeam))
                .findAny()
                .orElse(null);
    }

    public void finishMatch(String homeTeam, String awayTeam) {

    }

    public List<Match> getMatchesInProgressSummary() {
        return scoreBoard.getBoard();
    }
}
