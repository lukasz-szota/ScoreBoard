package org.example.scoreboard;

import java.util.List;

interface ScoreBoard {

    void addMatch(Match match);

    List<Match> getBoard();
}
