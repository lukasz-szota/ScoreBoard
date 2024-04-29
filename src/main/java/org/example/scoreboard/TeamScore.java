package org.example.scoreboard;

import lombok.*;

@RequiredArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class TeamScore {

    private final String teamName;

    private int score;
}
