package br.ucs.classleague.domain;

import br.ucs.classleague.domain.Sport.SportsEnum;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import java.time.LocalDate;

@Entity
public class Coach extends Person {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private SportsEnum sport;

    public Coach() {
    }

    public Coach(SportsEnum sport, String name, String surname, LocalDate birthDate, String gender, String telephone, String cpf) {
        super(name, surname, birthDate, gender, telephone, cpf);
        this.sport = sport;
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
    
    public String getDisplayName() {
        return super.getName();
    }
}
