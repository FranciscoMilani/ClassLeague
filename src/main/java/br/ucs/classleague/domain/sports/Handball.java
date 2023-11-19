package br.ucs.classleague.domain.sports;

import br.ucs.classleague.domain.Sport;

public class Handball extends Sport {
    
    private static final SportsEnum sport = SportsEnum.HANDBALL;
    private static final Integer matchDurationMinutes = 20;
    private static final Integer halfAmount = 2;
    private static final Boolean pointsPerPlayer = true;
    
    public Handball() {
        super(sport, matchDurationMinutes, halfAmount, pointsPerPlayer);
    }
    
}
