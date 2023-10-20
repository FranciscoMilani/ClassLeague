package br.ucs.classleague.application.Services;

import br.ucs.classleague.domain.Tournament;
import br.ucs.classleague.infrastructure.data.DaoFactory;
import br.ucs.classleague.infrastructure.data.TournamentDao;

public class TournamentService {
    
    private TournamentDao tournamentDao = DaoFactory.getTournamentDao();
    
    // cria confrontos (partidas)
    public void createClashes(Tournament tId) {
        //tournamentDao.
    }
}
