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
        assertEquals("home", scoreBoard.getBoard().get(0).homeTeamScore().getTeamName());
        assertEquals(0, scoreBoard.getBoard().get(0).homeTeamScore().getScore());
        assertEquals("away", scoreBoard.getBoard().get(0).awayTeamScore().getTeamName());
        assertEquals(0, scoreBoard.getBoard().get(0).awayTeamScore().getScore());
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
        assertEquals("home", scoreBoard.getBoard().get(0).homeTeamScore().getTeamName());
        assertEquals(3, scoreBoard.getBoard().get(0).homeTeamScore().getScore());
        assertEquals("away", scoreBoard.getBoard().get(0).awayTeamScore().getTeamName());
        assertEquals(1, scoreBoard.getBoard().get(0).awayTeamScore().getScore());
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
        assertEquals("test3", matchesInProgressSummary.get(0).homeTeamScore().getTeamName());
        assertEquals("test4", matchesInProgressSummary.get(0).awayTeamScore().getTeamName());
        assertEquals("test1", matchesInProgressSummary.get(1).homeTeamScore().getTeamName());
        assertEquals("test2", matchesInProgressSummary.get(1).awayTeamScore().getTeamName());
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
        assertEquals("test5", matchesInProgressSummary.get(0).homeTeamScore().getTeamName());
        assertEquals("test6", matchesInProgressSummary.get(0).awayTeamScore().getTeamName());
        assertEquals("test3", matchesInProgressSummary.get(1).homeTeamScore().getTeamName());
        assertEquals("test4", matchesInProgressSummary.get(1).awayTeamScore().getTeamName());
        assertEquals("test1", matchesInProgressSummary.get(2).homeTeamScore().getTeamName());
        assertEquals("test2", matchesInProgressSummary.get(2).awayTeamScore().getTeamName());
    }

    @Test
    void testScenarioFromTaskDescription() {
        // given
        scoreBoardService.startMatch("Mexico", "Canada");
        scoreBoardService.startMatch("Spain", "Brazil");
        scoreBoardService.startMatch("Germany", "France");
        scoreBoardService.startMatch("Uruguay", "Italy");
        scoreBoardService.startMatch("Argentina", "Australia");

        scoreBoardService.updateScore(new TeamScore("Mexico", 0), new TeamScore("Canada", 5));
        scoreBoardService.updateScore(new TeamScore("Spain", 10), new TeamScore("Brazil", 2));
        scoreBoardService.updateScore(new TeamScore("Germany", 2), new TeamScore("France", 2));
        scoreBoardService.updateScore(new TeamScore("Uruguay", 6), new TeamScore("Italy", 6));
        scoreBoardService.updateScore(new TeamScore("Argentina", 3), new TeamScore("Australia", 1));

        // when
        List<Match> matchesInProgressSummary = scoreBoardService.getMatchesInProgressSummary();

        // then
        assertEquals(5, matchesInProgressSummary.size());
        assertEquals("Uruguay", matchesInProgressSummary.get(0).homeTeamScore().getTeamName());
        assertEquals("Italy", matchesInProgressSummary.get(0).awayTeamScore().getTeamName());

        assertEquals("Spain", matchesInProgressSummary.get(1).homeTeamScore().getTeamName());
        assertEquals("Brazil", matchesInProgressSummary.get(1).awayTeamScore().getTeamName());

        assertEquals("Mexico", matchesInProgressSummary.get(2).homeTeamScore().getTeamName());
        assertEquals("Canada", matchesInProgressSummary.get(2).awayTeamScore().getTeamName());

        assertEquals("Argentina", matchesInProgressSummary.get(3).homeTeamScore().getTeamName());
        assertEquals("Australia", matchesInProgressSummary.get(3).awayTeamScore().getTeamName());

        assertEquals("Germany", matchesInProgressSummary.get(4).homeTeamScore().getTeamName());
        assertEquals("France", matchesInProgressSummary.get(4).awayTeamScore().getTeamName());
    }

    private void addMatchToTheBoard(Match match) {
        scoreBoard.getBoard().add(match);
    }
}