package br.ucs.classleague.domain;

public abstract class Sport {
    
    private SportsEnum sport;
    private Integer matchDurationMinutes;
    private Integer periodAmount;         // quantidade de "períodos" e.g. sets, tempo, quadro...
    private Boolean pointsPerPlayer;
    private Boolean hasOvertime;
    
    public static enum SportsEnum {
        FOOTBALL("Futebol"),
        HANDBALL("Handebol"),
        BASKETBALL("Basquete"),
        FUTSAL("Futsal");

        private final String name;

        private SportsEnum(String name) {
            this.name = name;
        }
        
        public String getName(){
            return name;
        }
    }

    public Sport(SportsEnum sport, Integer matchDurationMinutes, Integer periodAmount, 
            Boolean pointsPerPlayer) {
        this.sport = sport;
        this.matchDurationMinutes = matchDurationMinutes;
        this.periodAmount = periodAmount;
        this.pointsPerPlayer = pointsPerPlayer;
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

    public Integer getPeriodAmount() {
        return periodAmount;
    }

    public void setPeriodAmount(Integer periodAmount) {
        this.periodAmount = periodAmount;
    }

    public Boolean getPointsPerPlayer() {
        return pointsPerPlayer;
    }

    public void setPointsPerPlayer(Boolean pointsPerPlayer) {
        this.pointsPerPlayer = pointsPerPlayer;
    }

    public Boolean getHasOvertime() {
        return hasOvertime;
    }
}
