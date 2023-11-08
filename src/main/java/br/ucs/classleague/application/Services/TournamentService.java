package br.ucs.classleague.application.Services;

import br.ucs.classleague.domain.Match;
import br.ucs.classleague.domain.Team;
import br.ucs.classleague.domain.Tournament;
import br.ucs.classleague.domain.Tournament.TournamentPhase;
import br.ucs.classleague.domain.TournamentTeam;
import br.ucs.classleague.infrastructure.data.DaoFactory;
import br.ucs.classleague.infrastructure.data.MatchDao;
import br.ucs.classleague.infrastructure.data.TournamentDao;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class TournamentService {
    
    private TournamentDao tournamentDao = DaoFactory.getTournamentDao();
    private MatchDao matchDao = DaoFactory.getMatchDao();
    
    /*
        Cria confrontos entre times
        Esse é um método simples: embaralha uma lista de times e cria partidas, sem repetição
        Esse método não "semeia"/distribui times baseado na pontuação do cabeça de chave sobrevivente
    */
    public void createInitialSimpleClashes(Tournament tournament) {
        List<TournamentTeam> ttList = new ArrayList<>(tournament.getTournamentTeam());
        List<Team> teams = ttList.stream()
                .map(TournamentTeam::getTeam)
                .collect(Collectors.toList());
        
        Collections.shuffle(teams);

        for (int i = 0; i < teams.size() / 2; i++) {  
            Match m = new Match(
                    tournament, 
                    LocalDateTime.now(), 
                    teams.get(i*2), 
                    teams.get(i*2+1),
                    tournament.getPhase()
            );
            
            matchDao.create(m);
        }
    }
    
    // O campeonato já foi iniciado. Pegando times vencedores dessa fase para criar novos confrontos.
    public void createLaterSimpleClashes(Tournament tournament) {
        List<Team> winners = tournament.getMatches()
            .stream()
            .filter((match) -> match.getPhase() == 
                    TournamentPhase.values()[tournament.getPhase().ordinal() - 1])
            .map(Match::getWinner)
            .collect(Collectors.toList());

        for (int i = 0; i < winners.size() / 2; i++) {  
            Match m = new Match(
                    tournament, 
                    LocalDateTime.now(), 
                    winners.get(i*2), 
                    winners.get(i*2+1),
                    tournament.getPhase()
            );
            
            matchDao.create(m);
        }
        
        tournamentDao.refresh(tournament);
    }
    
    /**
    * Retorna true caso todas as partidas tenham encerrado, e false caso ainda há partidas não encerradas.
    */
    public boolean checkNextPhase(Tournament tournament) {
        return tournament.getMatches()
                .stream()
                .map(Match::getEnded)
                .allMatch((t) -> t.equals(true));
    }
    
    public void startNextPhase(Tournament tournament) {
        int nextPhaseOrdinal = tournament.getPhase().ordinal() + 1;
        TournamentPhase nextPhase = TournamentPhase.values()[nextPhaseOrdinal];
        tournament.setPhase(nextPhase);
        tournamentDao.update(tournament);
        createLaterSimpleClashes(tournament);
    }
}
