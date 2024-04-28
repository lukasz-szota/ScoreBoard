package org.example.scoreboard;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

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
    void shouldAddMatchToBoard() {
        scoreBoardService.startMatch("home", "away");

        assertEquals(1, scoreBoard.getBoard().size());
    }
}