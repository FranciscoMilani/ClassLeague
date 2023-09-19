package br.ucs.classleague;

import br.ucs.classleague.infrastructure.presentation.views.GUI;
import com.formdev.flatlaf.FlatDarculaLaf;

/**
 *
 * @author Francisco
 */
public class ClassLeague {

    public static void main(String[] args) {
        FlatDarculaLaf.setup();
        GUI gui = new GUI();
        
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new GUI().setVisible(true);
            }
        });
    }
}
