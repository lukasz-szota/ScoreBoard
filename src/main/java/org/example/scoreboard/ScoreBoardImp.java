package org.example.scoreboard;

import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
class ScoreBoardImp implements ScoreBoard {

    private final List<Match> board =  new ArrayList<>();

    @Override
    public void addMatch(Match match) {
        board.add(match);
    }
}
