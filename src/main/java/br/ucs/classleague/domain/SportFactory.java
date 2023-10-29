package br.ucs.classleague.domain;

import br.ucs.classleague.domain.Sport.SportsEnum;

public class SportFactory {
    
    public static Sport createSport(SportsEnum type) {
        
        switch (type){
            // retornar classes concretas dos esportes
            case FOOTBALL: {
                return new Football();
            }
            case FUTSAL: {
            }
            case VOLLEYBALL: {
            }
            case BASKETBALL: {
            }
            default:
                return null;
        }
    }
}