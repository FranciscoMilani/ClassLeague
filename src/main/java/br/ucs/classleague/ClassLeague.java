package br.ucs.classleague;

import br.ucs.classleague.infrastructure.presentation.views.GUI;
import com.formdev.flatlaf.FlatDarculaLaf;
import javax.swing.SwingUtilities;

public class ClassLeague {

    public static void main(String[] args) {
        FlatDarculaLaf.setup();
        
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new GUI().setVisible(true);
            }
        });
    }
}
