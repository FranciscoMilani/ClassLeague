package br.ucs.classleague.application.Services;

import br.ucs.classleague.domain.Match;
import br.ucs.classleague.domain.Team;
import br.ucs.classleague.domain.Tournament;
import br.ucs.classleague.domain.Tournament.TournamentPhase;
import br.ucs.classleague.domain.TournamentTeam;
import br.ucs.classleague.infrastructure.data.DaoProvider;
import br.ucs.classleague.infrastructure.data.MatchDao;
import br.ucs.classleague.infrastructure.data.TournamentDao;
import br.ucs.classleague.infrastructure.data.TournamentTeamDao;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class TournamentService {
    
    private TournamentDao tournamentDao;
    private MatchDao matchDao;
    private TournamentTeamDao ttDao;
    private MatchService matchService;

    public TournamentService() {
        this.tournamentDao = DaoProvider.getTournamentDao();
        this.matchDao = DaoProvider.getMatchDao();
        this.ttDao = DaoProvider.getTournamentTeamDao();
        this.matchService = new MatchService();
    }
    
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
            
            Team[] teamArray = new Team[] {
                teams.get(i*2),
                teams.get(i*2+1)
            };
            
            Match m = new Match(
                    tournament, 
                    null,
                    teamArray[0], 
                    teamArray[1],
                    tournament.getPhase()
            );
            
            matchDao.create(m);
            matchService.registerTeamStudentsForMatch(m, teamArray);
        }
    }
    
    /*
        O campeonato já foi iniciado. Pegando times vencedores dessa fase para criar novos confrontos.
    */
    public void createLaterSimpleClashes(Tournament tournament) {
        List<Team> winners = tournament.getMatches()
            .stream()
            .filter((match) -> match.getPhase() == 
                    TournamentPhase.values()[tournament.getPhase().ordinal() - 1])
            .map(Match::getWinner)
            .collect(Collectors.toList());
        
        for (int i = 0; i < winners.size() / 2; i++) {  
            
            Team[] teamArray = new Team[] {
                winners.get(i*2),
                winners.get(i*2+1)
            };
            
            Match m = new Match(
                    tournament, 
                    null,
                    teamArray[0], 
                    teamArray[1],
                    tournament.getPhase()
            );
            
            matchDao.create(m);
            matchService.registerTeamStudentsForMatch(m, teamArray);
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
                .allMatch((t) -> t.equals(true) 
                        && tournament.getPhase() != TournamentPhase.FINAL);
    }
    
    /**
    * Retorna true se torneio terminou e false caso ainda não tenha terminado.
    */
    public boolean checkEndedTournament(Tournament tournament) {
        if (tournament.getPhase() != TournamentPhase.FINAL) {
            return false;
        } else {
            boolean allEnded = tournament.getMatches()
                    .stream()
                    .map(Match::getEnded)
                    .allMatch((t) -> t.equals(true));  
            
            return allEnded;
        }
    }
    
    public void startNextPhase(Tournament tournament) {
        int nextPhaseOrdinal = tournament.getPhase().ordinal() + 1;
        TournamentPhase nextPhase = TournamentPhase.values()[nextPhaseOrdinal];
        tournament.setPhase(nextPhase);
        tournamentDao.update(tournament);
        createLaterSimpleClashes(tournament);
    }
    
    public int getHighestPhaseIndex(int teamCount) {
        int phases = (int) (Math.log(teamCount) / Math.log(2));
        return TournamentPhase.values().length - phases;
    }
    
    public int getHighestPhaseIndex(Tournament tournament) {
        int teamCount = tournament.getTournamentTeam().size();
        int phases = (int) (Math.log(teamCount) / Math.log(2));
        return TournamentPhase.values().length - phases;
    }
    
    public String[] getPhasesInTournament(Tournament tournament) {
        int i = getHighestPhaseIndex(tournament);    
        int currIndex = tournament.getPhase().ordinal();
        int diff = i - currIndex;
        String[] arr = new String[Math.abs(diff)];
        
        for (int j = 0; j < arr.length; j++) {
            arr[j] = TournamentPhase.getNameByPhaseIndex(currIndex - j-1);   
        }

        return arr;
    }
    
    public Object[] getTeamsToArray(Long id) {
        List<Team> teams = ttDao.findTeamsByTournamentId(id);
        
        if (teams != null) {
            return teams.toArray();   
        } else {
            return null;
        }
    }
}
