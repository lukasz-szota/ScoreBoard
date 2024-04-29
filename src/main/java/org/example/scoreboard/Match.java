package org.example.scoreboard;

import lombok.*;

@Getter
@RequiredArgsConstructor
@ToString
public class Match {

    private final TeamScore homeTeamScore;
    private final TeamScore awayTeamScore;

}
