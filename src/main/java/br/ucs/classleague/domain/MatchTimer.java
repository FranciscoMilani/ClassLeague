package br.ucs.classleague.domain;

public class MatchTimer {
    
    private static MatchState state;
    private Integer currentTimeSeconds;
    private Integer currentPeriod;
    
    static {
        state = MatchState.WAITING;
    }

    public MatchTimer() {
        currentTimeSeconds = 0;
        currentPeriod = 1;
    }
    
    public static enum MatchState {
        WAITING,
        RUNNING,
        STOPPED,
        INTERVAL
    }
    
    public void addPeriod() {
        currentPeriod++;
    }
     
    public static MatchState getState() {
        return state;
    }
    
    public static void setState(MatchState state) {
        state = state;
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
        state = MatchState.WAITING;
        currentTimeSeconds = 0;
        currentPeriod = 1;
    }
    
    public void prepareNextPeriod() {
        state = MatchState.INTERVAL;
        currentTimeSeconds = 0;
        currentPeriod++;
    }
}
