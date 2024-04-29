package org.example.scoreboard;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ScoreBoardServiceTest {

    ScoreBoardService scoreBoardService;
    ScoreBoardImp scoreBoard;

    @BeforeEach
    void setUp() {
        scoreBoard = new ScoreBoardImp();
        scoreBoardService = new ScoreBoardService(scoreBoard);
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void shouldAddMatchWithInitialScoresValueToTheBoard() {
        scoreBoardService.startMatch("home", "away");

        assertEquals(1, scoreBoard.getBoard().size());
        assertEquals("home", scoreBoard.getBoard().get(0).getHomeTeamScore().getTeamName());
        assertEquals(0, scoreBoard.getBoard().get(0).getHomeTeamScore().getScore());
        assertEquals("away", scoreBoard.getBoard().get(0).getAwayTeamScore().getTeamName());
        assertEquals(0, scoreBoard.getBoard().get(0).getAwayTeamScore().getScore());
    }

    @Test
    void shouldThrowExceptionWhenStartMatchWithAlreadyPlayingTeam() {
        // given
        addMatchToTheBoard(new Match(new TeamScore("home"), new TeamScore("away")));

        // when
        Exception exception = assertThrows(IllegalArgumentException.class,
                () -> scoreBoardService.startMatch("test1","home"));

        // then
        assertTrue(exception.getMessage().contains("At least one team is already playing another match!"));
    }

    @Test
    void shouldUpdateScore() {
        // given
        addMatchToTheBoard(new Match(new TeamScore("home"), new TeamScore("away")));

        // when
        scoreBoardService.updateScore(new TeamScore("home", 3), new TeamScore("away", 1));

        // then
        assertEquals(1, scoreBoard.getBoard().size());
        assertEquals("home", scoreBoard.getBoard().get(0).getHomeTeamScore().getTeamName());
        assertEquals(3, scoreBoard.getBoard().get(0).getHomeTeamScore().getScore());
        assertEquals("away", scoreBoard.getBoard().get(0).getAwayTeamScore().getTeamName());
        assertEquals(1, scoreBoard.getBoard().get(0).getAwayTeamScore().getScore());
    }

    @Test
    void shouldThrowExceptionWhenUpdateTeamScoreForNotExistingMatch() {
        // given
        addMatchToTheBoard(new Match(new TeamScore("home"), new TeamScore("away")));

        // when
        Exception exception = assertThrows(IllegalArgumentException.class,
                () -> scoreBoardService.updateScore(new TeamScore("test1", 3), new TeamScore("test2", 1)));

        // then
        assertTrue(exception.getMessage().contains("No match found between test1 and test2!"));
    }

    @Test
    void shouldFinishMatchAndRemoveItFromTheBoard() {
        // given
        addMatchToTheBoard(new Match(new TeamScore("home"), new TeamScore("away")));

        // when
        scoreBoardService.finishMatch("home", "away");

        // then
        assertEquals(0, scoreBoard.getBoard().size());
    }

    @Test
    void shouldThrowExceptionWhenFinishNotExistingMatch() {
        // given
        addMatchToTheBoard(new Match(new TeamScore("home"), new TeamScore("away")));

        // when
        Exception exception = assertThrows(IllegalArgumentException.class,
                () -> scoreBoardService.finishMatch("test1", "test2"));

        // then
        assertTrue(exception.getMessage().contains("No match found between test1 and test2!"));
    }

    @Test
    void shouldReturnAllMatchesFromTheBoard() {
        // given
        addMatchToTheBoard(new Match(new TeamScore("test1"), new TeamScore("test2")));
        addMatchToTheBoard(new Match(new TeamScore("test3"), new TeamScore("test4")));

        // when
        List<Match> matchesInProgressSummary = scoreBoardService.getMatchesInProgressSummary();

        assertEquals(2, matchesInProgressSummary.size());
    }

    @Test
    void shouldReturnAllMatchesFromTheBoardOrderedByTheirTotalScore() {
        // given
        addMatchToTheBoard(new Match(new TeamScore("test1", 0), new TeamScore("test2", 1)));
        addMatchToTheBoard(new Match(new TeamScore("test3", 2), new TeamScore("test4", 1)));

        // when
        List<Match> matchesInProgressSummary = scoreBoardService.getMatchesInProgressSummary();

        assertEquals(2, matchesInProgressSummary.size());
        assertEquals("test3", matchesInProgressSummary.get(0).getHomeTeamScore().getTeamName());
        assertEquals("test4", matchesInProgressSummary.get(0).getAwayTeamScore().getTeamName());
        assertEquals("test1", matchesInProgressSummary.get(1).getHomeTeamScore().getTeamName());
        assertEquals("test2", matchesInProgressSummary.get(1).getAwayTeamScore().getTeamName());
    }

    @Test
    void shouldReturnAllMatchesFromTheBoardOrderedByTheirTotalScoreAndReversedStartTime() {
        // given
        addMatchToTheBoard(new Match(new TeamScore("test1", 0), new TeamScore("test2", 1)));
        addMatchToTheBoard(new Match(new TeamScore("test3", 2), new TeamScore("test4", 1)));
        addMatchToTheBoard(new Match(new TeamScore("test5", 0), new TeamScore("test6", 3)));

        // when
        List<Match> matchesInProgressSummary = scoreBoardService.getMatchesInProgressSummary();

        assertEquals(3, matchesInProgressSummary.size());
        assertEquals("test5", matchesInProgressSummary.get(0).getHomeTeamScore().getTeamName());
        assertEquals("test6", matchesInProgressSummary.get(0).getAwayTeamScore().getTeamName());
        assertEquals("test3", matchesInProgressSummary.get(1).getHomeTeamScore().getTeamName());
        assertEquals("test4", matchesInProgressSummary.get(1).getAwayTeamScore().getTeamName());
        assertEquals("test1", matchesInProgressSummary.get(2).getHomeTeamScore().getTeamName());
        assertEquals("test2", matchesInProgressSummary.get(2).getAwayTeamScore().getTeamName());
    }

    private void addMatchToTheBoard(Match match) {
        scoreBoard.getBoard().add(match);
    }
}