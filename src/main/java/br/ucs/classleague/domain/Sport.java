package br.ucs.classleague.domain;

/**
 *
 * @author Francisco
 */
public class Sport {
    private SportsEnum sport;
    private Integer matchDurationMinutes;
    private Integer halfAmount;         // quantidade de "períodos" e.g. sets, tempo, quadro...
    private Boolean pointsPerPlayer;
    
    public static enum SportsEnum {
        FOOTBALL("Futebol"),
        VOLLEYBALL("Vôlei"),
        BASKETBALL("Basquete"),
        FUTSAL("Futsal");

        private String name;

        private SportsEnum(String name) {
            this.name = name;
        } 
    }

    public Sport(Integer matchDurationMinutes, Integer halfAmount, 
            Boolean pointsPerPlayer) {
        this.matchDurationMinutes = matchDurationMinutes;
        this.halfAmount = halfAmount;
        this.pointsPerPlayer = pointsPerPlayer;
    }
 
    
}
