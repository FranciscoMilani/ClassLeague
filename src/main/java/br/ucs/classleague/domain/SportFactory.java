package br.ucs.classleague.domain;

import br.ucs.classleague.domain.Sport.SportsEnum;
import br.ucs.classleague.domain.sports.Basketball;
import br.ucs.classleague.domain.sports.Football;
import br.ucs.classleague.domain.sports.Futsal;
import br.ucs.classleague.domain.sports.Handball;

public class SportFactory {
    
    public static Sport createSport(SportsEnum type) {
        
        switch (type){
            case FOOTBALL: {
                return new Football();
            }
            case FUTSAL: {
                return new Futsal();
            }
            case HANDBALL: {
                return new Handball();
            }
            case BASKETBALL: {
                return new Basketball();
            }
            default:
                return null;
        }
    }
}
