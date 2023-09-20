package br.ucs.classleague.domain;

/**
 *
 * @author Francisco
 */
public class Sport {
    private Long id;
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

    public Sport() {
    }

    public Sport(Integer matchDurationMinutes, Integer halfAmount, 
            Boolean pointsPerPlayer) {
        this.matchDurationMinutes = matchDurationMinutes;
        this.halfAmount = halfAmount;
        this.pointsPerPlayer = pointsPerPlayer;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public SportsEnum getSport() {
        return sport;
    }

    public void setSport(SportsEnum sport) {
        this.sport = sport;
    }

    public Integer getMatchDurationMinutes() {
        return matchDurationMinutes;
    }

    public void setMatchDurationMinutes(Integer matchDurationMinutes) {
        this.matchDurationMinutes = matchDurationMinutes;
    }

    public Integer getHalfAmount() {
        return halfAmount;
    }

    public void setHalfAmount(Integer halfAmount) {
        this.halfAmount = halfAmount;
    }

    public Boolean getPointsPerPlayer() {
        return pointsPerPlayer;
    }

    public void setPointsPerPlayer(Boolean pointsPerPlayer) {
        this.pointsPerPlayer = pointsPerPlayer;
    }
}
