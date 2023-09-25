package br.ucs.classleague.infrastructure.presentation.controllers;

import br.ucs.classleague.infrastructure.data.ClassDao;
import br.ucs.classleague.infrastructure.presentation.views.GUI;
import java.util.List;
import javax.swing.ComboBoxModel;
import javax.swing.DefaultComboBoxModel;

public class RegisterController {
    
    private GUI frame;
    private ClassDao dao;
    
    public RegisterController(GUI frame){
        this.frame = frame;
    }
    
    public void showClassesNames(){
        DefaultComboBoxModel model = new DefaultComboBoxModel<String>();
        model.addAll(dao.findAll());
        frame.classComboBox2.setModel(model);
    }
}
