package br.ucs.classleague;

import br.ucs.classleague.domain.SchoolClass;
import br.ucs.classleague.infrastructure.data.ClassDao;
import br.ucs.classleague.infrastructure.data.Datasource;
import br.ucs.classleague.infrastructure.presentation.views.GUI;
import com.formdev.flatlaf.FlatDarculaLaf;

public class ClassLeague {

    public static void main(String[] args) {
        FlatDarculaLaf.setup();
        GUI gui = new GUI();
        
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new GUI().setVisible(true);
            }
        });
        
        Datasource src = new Datasource();
        ClassDao dao = new ClassDao();
        dao.findById(1L).ifPresent((t) -> {
            System.out.println(t.getName());
        });
    }
}
