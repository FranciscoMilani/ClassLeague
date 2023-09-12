package br.ucs.classleague.domain;

/**
 *
 * @author Francisco
 */
public class Sport {
    private String name;
    private Integer matchDurationMinutes;
    // quantidade de "períodos" e.g. sets, tempo, quadro...
    private Integer halfAmount;
    private Boolean pointsPerPlayer;

    public Sport(String name, Integer matchDurationMinutes, Integer halfAmount, 
            Boolean pointsPerPlayer) {
        this.name = name;
        this.matchDurationMinutes = matchDurationMinutes;
        this.halfAmount = halfAmount;
        this.pointsPerPlayer = pointsPerPlayer;
    }
    
    
}
