package br.ucs.classleague.infrastructure.presentation.controllers;

import br.ucs.classleague.application.Services.CSVConverterService;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import javax.swing.JComponent;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

public class ControllerUtilities {
    
    public static void resetTable(JTable table) {
        DefaultTableModel model = (DefaultTableModel) table.getModel();
        model.setRowCount(0);
        table.revalidate();
        
    }
    
    public static String getFormattedDate(LocalDateTime ldt) {
        LocalDate date = ldt.toLocalDate();
        
        DateTimeFormatter formatter = DateTimeFormatter.ofLocalizedDate(
                FormatStyle.LONG
        );
        
        return date.format(formatter);
    }
    
        public static String getFormattedTime(LocalDateTime ldt) {
        LocalTime time = ldt.toLocalTime();
       
        DateTimeFormatter formatterTime = DateTimeFormatter.ofLocalizedTime(
                FormatStyle.SHORT
        );
        
        return time.format(formatterTime);
    }
        
    public static void convertTableToCSV(JComponent parent, JTable table) {
        JFileChooser fc = new JFileChooser();
        fc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        fc.setDialogType(JFileChooser.SAVE_DIALOG);
        fc.setDialogTitle("Salvar em");
        
        int result = fc.showOpenDialog(parent);
        if (result == JFileChooser.APPROVE_OPTION) {
            try {
                String path = CSVConverterService.exportToCSV(table, fc.getSelectedFile().getPath(), "output");
                JOptionPane.showMessageDialog(parent, "Sucesso ao converter. Salvo em:\n" + path);
            } catch (Exception e) {
                JOptionPane.showMessageDialog(parent, "Erro ao converter:\n" + e.getMessage());
            }
        }
    }
}
