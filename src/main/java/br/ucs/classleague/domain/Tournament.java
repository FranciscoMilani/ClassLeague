package br.ucs.classleague.domain;

import java.time.LocalDateTime;

/**
 *
 * @author Francisco
 */
public class Tournament {
    private String name;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private Sport sport;

    public Tournament(String name, LocalDateTime startTime, LocalDateTime endTime, Sport sport) {
        this.name = name;
        this.startTime = startTime;
        this.endTime = endTime;
        this.sport = sport;
    }
    
    
}
