package br.ucs.classleague.infrastructure.presentation.controllers;

import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

public class ControllerUtilities {
    
    public static void resetTable(JTable table) {
        DefaultTableModel model = (DefaultTableModel) table.getModel();
        model.setRowCount(0);
        table.revalidate();
    }
}
