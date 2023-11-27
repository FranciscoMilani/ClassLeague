package br.ucs.classleague.domain;

public class MatchTimer {
    
    private static MatchState state;
    private Integer currentTimeSeconds;
    private Integer currentPeriod;
    
    static {
        MatchTimer.state = MatchState.WAITING;
    }

    public MatchTimer() {
        currentTimeSeconds = 0;
        currentPeriod = 1;
    }
    
    public void addPeriod() {
        currentPeriod++;
    }
     
    public static MatchState getState() {
        return MatchTimer.state;
    }
    
    public static void setState(MatchState state) {
        MatchTimer.state = state;
    }

    public Integer getCurrentTime() {
        return currentTimeSeconds;
    }

    public void addCurrentTime(Integer timeToAddSeconds) {
        currentTimeSeconds += timeToAddSeconds;
    }

    public Integer getCurrentPeriod() {
        return currentPeriod;
    }
    
    public void resetTimer() {
        MatchTimer.state = MatchState.WAITING;
        currentTimeSeconds = 0;
        currentPeriod = 1;
    }
    
    public void prepareNextPeriod() {
        MatchTimer.state = MatchState.INTERVAL;
        currentTimeSeconds = 0;
        currentPeriod++;
    }
    
    public void endTimer() {
        MatchTimer.state = MatchState.ENDED;
    }
}
