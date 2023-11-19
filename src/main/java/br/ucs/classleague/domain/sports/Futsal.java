package br.ucs.classleague.domain.sports;

import br.ucs.classleague.domain.Sport;

public class Futsal extends Sport {
    
    private static final SportsEnum sport = SportsEnum.FUTSAL;
    private static final Integer matchDurationMinutes = 20;
    private static final Integer halfAmount = 2;
    private static final Boolean pointsPerPlayer = true;
    
    public Futsal() {
        super(sport, matchDurationMinutes, halfAmount, pointsPerPlayer);
    }
    
}
