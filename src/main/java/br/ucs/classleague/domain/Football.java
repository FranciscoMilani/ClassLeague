package br.ucs.classleague.domain;

public class Football extends Sport {

    private static final SportsEnum sport = SportsEnum.FOOTBALL;
    private static final Integer matchDurationMinutes = 45;
    private static final Integer halfAmount = 2;
    private static final Boolean pointsPerPlayer = true;
    
    public Football() {
        super(sport, matchDurationMinutes, halfAmount, pointsPerPlayer);
    }
    
}
