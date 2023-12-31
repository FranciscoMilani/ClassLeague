package br.ucs.classleague.domain;

import br.ucs.classleague.domain.Sport.SportsEnum;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.PostLoad;
import jakarta.persistence.Transient;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Set;

@Entity
public class Tournament implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;

    @Transient
    private Sport sport;

    private LocalDate startTime;
    private LocalDate endTime;
    private SportsEnum sportEnum;
    private TournamentPhase phase;
        
    @OneToMany(mappedBy = "tournament")
    private Set<Match> matches;
    
    @OneToMany(mappedBy = "tournament", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Set<TournamentTeam> tournamentTeam;

    public static enum TournamentPhase {
        ROUND_OF_SIXTEEN("Oitavas de final"),
        QUARTERFINALS("Quartas de final"),
        SEMIFINALS("Semifinais"),
        FINAL("Final");
        
        private final String name;
        
        TournamentPhase(String name) {
            this.name = name;
        }

        public String getName() {
            return name;
        }
        
        public static String getNameByPhaseIndex(int index) {
            return TournamentPhase.values()[index].getName();
        }
        
        public static TournamentPhase fromString(String text) {
            for (TournamentPhase tp : TournamentPhase.values()) {
                if (tp.name.equalsIgnoreCase(text)) {
                    return tp;
                }
            }
            
            throw new RuntimeException("Nenhuma constante de nome " + text + " encontrada");
        }
    }
    
    public static enum TournamentStatus {
        IN_PROGRESS("Em progresso"),
        ENDED("Finalizado");
        
        private String status;
        
        TournamentStatus(String status) {
            this.status = status;
        }

        public String getStatus() {
            return status;
        }
        
        public static String mapBoolToStatus(boolean val) {
            return val ? TournamentStatus.values()[1].getStatus() 
                    : TournamentStatus.values()[0].getStatus();
        }
    }

    public Tournament() {
    }

    public Tournament(String name, LocalDate startTime, LocalDate endTime, SportsEnum sport, TournamentPhase startPhase) {
        this.name = name;
        this.startTime = startTime;
        this.endTime = endTime;
        this.sportEnum = sport;
        this.phase = startPhase;
    }
    
    @PostLoad
    private void loadSport(){
        sport = SportFactory.createSport(sportEnum);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    
    public Sport getSport(){
        return sport;
    }
    
    public void setSport(Sport sport) {
        this.sport = sport;
        this.sportEnum = sport.getSport();
    }
    
    public LocalDate getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalDate startTime) {
        this.startTime = startTime;
    }

    public LocalDate getEndTime() {
        return endTime;
    }

    public void setEndTime(LocalDate endTime) {
        this.endTime = endTime;
    }

    public SportsEnum getSportEnum() {
        return sportEnum;
    }

    public void setSportEnum(SportsEnum sportEnum) {
        this.sportEnum = sportEnum;
    }

    public Set<Match> getMatches() {
        return matches;
    }

    public void setMatches(Set<Match> matches) {
        this.matches = matches;
    }

    public TournamentPhase getPhase() {
        return phase;
    }

    public void setPhase(TournamentPhase phase) {
        this.phase = phase;
    }

    public Set<TournamentTeam> getTournamentTeam() {
        return tournamentTeam;
    }

    public void setTournamentTeam(Set<TournamentTeam> tournamentTeam) {
        this.tournamentTeam = tournamentTeam;
    }
}
