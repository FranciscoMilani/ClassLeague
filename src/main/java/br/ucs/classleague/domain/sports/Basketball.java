package br.ucs.classleague.domain.sports;

import br.ucs.classleague.domain.Sport;

public class Basketball extends Sport {
    
    private static final SportsEnum sport = SportsEnum.BASKETBALL;
    private static final Integer matchDurationMinutes = 10;
    private static final Integer halfAmount = 4;
    private static final Boolean pointsPerPlayer = true;
    
    public Basketball() {
        super(sport, matchDurationMinutes, halfAmount, pointsPerPlayer);
    }
    
}
