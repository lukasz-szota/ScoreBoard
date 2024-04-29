package org.example.scoreboard;

import lombok.RequiredArgsConstructor;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

@RequiredArgsConstructor
public class ScoreBoardService {

    private final ScoreBoard scoreBoard;

    public void startMatch(String homeTeam, String awayTeam) {
        scoreBoard.getBoard().stream()
                .filter(m -> m.getHomeTeamScore().getTeamName().equals(homeTeam) || m.getAwayTeamScore().getTeamName().equals(homeTeam)
                                || m.getHomeTeamScore().getTeamName().equals(awayTeam) || m.getAwayTeamScore().getTeamName().equals(awayTeam))
                .findAny()
                .ifPresent(m -> {
                    throw new IllegalArgumentException("At least one team is already playing another match!");
                });

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
                .orElseThrow(() -> new IllegalArgumentException(String.format("No match found between %s and %s!", homeTeam, awayTeam)));
    }

    public void finishMatch(String homeTeam, String awayTeam) {
        Match finishedMatch = scoreBoard.getBoard().stream()
                .filter(m -> m.getHomeTeamScore().getTeamName().equals(homeTeam) && m.getAwayTeamScore().getTeamName().equals(awayTeam))
                .findAny()
                .orElseThrow(() -> new IllegalArgumentException(String.format("No match found between %s and %s!", homeTeam, awayTeam)));

        scoreBoard.getBoard().remove(finishedMatch);
    }

    public List<Match> getMatchesInProgressSummary() {
        Comparator<Match> totalScoreComparator = Comparator.comparing(m -> m.getHomeTeamScore().getScore() + m.getAwayTeamScore().getScore());
        List<Match> resultList = new ArrayList<>(scoreBoard.getBoard());
        Collections.reverse(resultList);

        return resultList.stream()
                .sorted(totalScoreComparator.reversed())
                .toList();
    }
}
