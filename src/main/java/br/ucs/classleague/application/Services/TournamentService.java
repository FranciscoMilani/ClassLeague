package br.ucs.classleague.application.Services;

import br.ucs.classleague.domain.Match;
import br.ucs.classleague.infrastructure.data.DaoFactory;
import br.ucs.classleague.infrastructure.data.TournamentDao;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class TournamentService {
    
    private TournamentDao tournamentDao = DaoFactory.getTournamentDao();
    
    // Cria confrontos entre times
    // Esse é um método simples: embaralha uma lista de times e cria partidas, sem repetição
    // Esse método não "semeia"/distribui times baseado na pontuação do cabeça de chave sobrevivente, que se chamaria "Seeded Tournament Brackets"
    public static void createSimpleClashes() {
        //List<Match> targetList = new ArrayList<>(tournament.getMatches());
        List<Integer> list = new ArrayList<>(Arrays.asList(1,2,3,4,5,6,7,8));
        List<Match> matches = new ArrayList<>();
        System.out.println(matches.get(0).getTeam(0).getName());
        
        Collections.shuffle(list);
        for (int i = 0; i < list.size()/2; i++){  
            //System.out.println(i*2 + "   vs   " + ((i*2)+1));
            //MatchExample me = new MatchExample(i*2, (i*2)+1);
            //matches.add(me);
        }
    }
    
    static class MatchExample {
        private int t1;
        private int t2;
        
        MatchExample(int t1, int t2){
            this.t1 = t1;
            this.t2 = t2;
        }
    }
}
