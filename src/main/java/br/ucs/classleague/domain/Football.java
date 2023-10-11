package br.ucs.classleague.domain;

public class Football extends Sport {

    private static final SportsEnum sport = SportsEnum.FOOTBALL;
    private static Integer matchDurationMinutes = 45;
    private static Integer halfAmount = 2;
    private static final Boolean pointsPerPlayer = true;
    
    public Football(SportsEnum sport, Integer matchDurationMinutes, Integer halfAmount, 
            Boolean pointsPerPlayer) {
        
        super(sport, matchDurationMinutes, halfAmount, pointsPerPlayer);
    }
    
}
