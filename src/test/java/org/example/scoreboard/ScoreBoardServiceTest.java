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
    void shouldUpdateTeamScores() {
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
    void shouldReturnAllMatchesFromTheBoard() {
        // given
        addMatchToTheBoard(new Match(new TeamScore("test1"), new TeamScore("test2")));
        addMatchToTheBoard(new Match(new TeamScore("test3"), new TeamScore("test4")));

        // when
        List<Match> matchesInProgressSummary = scoreBoardService.getMatchesInProgressSummary();

        assertEquals(2, matchesInProgressSummary.size());
    }

    private void addMatchToTheBoard(Match match) {
        scoreBoard.getBoard().add(match);
    }
}