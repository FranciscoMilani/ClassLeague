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
    
    public void resetTime() {
        currentTimeSeconds = 0;
    }
    
    public void addPeriod() {
        state = MatchState.RUNNING;
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

    public Integer addCurrentTime(Integer currentTimeSeconds) {
        this.currentTimeSeconds += currentTimeSeconds;
        return currentTimeSeconds;
    }

    public Integer getCurrentPeriod() {
        return currentPeriod;
    }
}
