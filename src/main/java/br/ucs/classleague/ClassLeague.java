package br.ucs.classleague;

import br.ucs.classleague.presentation.views.GUI;
import com.formdev.flatlaf.themes.FlatMacDarkLaf;
import javax.swing.SwingUtilities;

public class ClassLeague {

    public static void main(String[] args) {
        FlatMacDarkLaf.setup();
        
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new GUI().setVisible(true);
            }
        });
    }
}
