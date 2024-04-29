package org.example.scoreboard;

import lombok.RequiredArgsConstructor;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

@RequiredArgsConstructor
public class ScoreBoardService {

    private final ScoreBoard scoreBoard;

    public synchronized void startMatch(String homeTeam, String awayTeam) {
        scoreBoard.getBoard().stream()
                .filter(m -> m.homeTeamScore().getTeamName().equals(homeTeam) || m.awayTeamScore().getTeamName().equals(homeTeam)
                                || m.homeTeamScore().getTeamName().equals(awayTeam) || m.awayTeamScore().getTeamName().equals(awayTeam))
                .findAny()
                .ifPresent(m -> {
                    throw new IllegalArgumentException("At least one team is already playing another match!");
                });

        scoreBoard.addMatch(new Match(new TeamScore(homeTeam), new TeamScore(awayTeam)));
    }

    public synchronized void updateScore(TeamScore homeTeamScore, TeamScore awayTeamScore) {
        Match match = findMatch(homeTeamScore.getTeamName(), awayTeamScore.getTeamName());
        match.homeTeamScore().setScore(homeTeamScore.getScore());
        match.awayTeamScore().setScore(awayTeamScore.getScore());
    }

    private Match findMatch(final String homeTeam, final String awayTeam) {
        return scoreBoard.getBoard().stream()
                .filter(m -> m.homeTeamScore().getTeamName().equals(homeTeam) && m.awayTeamScore().getTeamName().equals(awayTeam))
                .findAny()
                .orElseThrow(() -> new IllegalArgumentException(String.format("No match found between %s and %s!", homeTeam, awayTeam)));
    }

    public synchronized void finishMatch(String homeTeam, String awayTeam) {
        Match finishedMatch = scoreBoard.getBoard().stream()
                .filter(m -> m.homeTeamScore().getTeamName().equals(homeTeam) && m.awayTeamScore().getTeamName().equals(awayTeam))
                .findAny()
                .orElseThrow(() -> new IllegalArgumentException(String.format("No match found between %s and %s!", homeTeam, awayTeam)));

        scoreBoard.getBoard().remove(finishedMatch);
    }

    public List<Match> getMatchesInProgressSummary() {
        Comparator<Match> totalScoreComparator = Comparator.comparing(m -> m.homeTeamScore().getScore() + m.awayTeamScore().getScore());
        List<Match> resultList = new ArrayList<>(scoreBoard.getBoard());
        Collections.reverse(resultList);

        return resultList.stream()
                .sorted(totalScoreComparator.reversed())
                .toList();
    }
}
