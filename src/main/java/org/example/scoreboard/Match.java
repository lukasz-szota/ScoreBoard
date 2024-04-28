package org.example.scoreboard;

import lombok.*;

@Getter
@RequiredArgsConstructor
public class Match {

    private final TeamScores homeTeamScores;
    private final TeamScores awayTeamScores;

}
