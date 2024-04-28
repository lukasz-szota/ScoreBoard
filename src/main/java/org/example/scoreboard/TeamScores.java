package org.example.scoreboard;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@RequiredArgsConstructor
@Getter
@Setter
public class TeamScores {

    private final String teamName;

    private int scores;
}
