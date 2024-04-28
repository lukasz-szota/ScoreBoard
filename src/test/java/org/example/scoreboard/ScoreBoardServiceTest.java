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
    void shouldAddMatchToTheBoard() {
        scoreBoardService.startMatch("home", "away");

        assertEquals(1, scoreBoard.getBoard().size());
    }

    @Test
    void shouldReturnAllMatchesFromTheBoard() {
        // given
        addMatchToTheBoard(new Match(new TeamScores("test1"), new TeamScores("test2")));
        addMatchToTheBoard(new Match(new TeamScores("test3"), new TeamScores("test4")));

        // when
        List<Match> matchesInProgressSummary = scoreBoardService.getMatchesInProgressSummary();

        assertEquals(2, matchesInProgressSummary.size());
    }

    private void addMatchToTheBoard(Match match) {
        scoreBoard.getBoard().add(match);
    }
}