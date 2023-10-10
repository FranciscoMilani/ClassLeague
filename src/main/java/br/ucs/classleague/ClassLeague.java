package br.ucs.classleague;

import br.ucs.classleague.domain.Football;
import br.ucs.classleague.domain.Sport;
import br.ucs.classleague.infrastructure.presentation.views.GUI;
import com.formdev.flatlaf.FlatDarculaLaf;

public class ClassLeague {

    public static void main(String[] args) {
        FlatDarculaLaf.setup();
        
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new GUI().setVisible(true);
            }
        });
        
        Sport football = new Football(Sport.SportsEnum.BASKETBALL, 10, 2, false);
        System.out.println(football.getSport());
        football.setSport(Sport.SportsEnum.FUTSAL);
    }
}
