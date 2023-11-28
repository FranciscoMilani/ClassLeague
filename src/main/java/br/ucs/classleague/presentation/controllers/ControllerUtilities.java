package br.ucs.classleague.presentation.controllers;

import br.ucs.classleague.application.services.CSVConverterService;
import java.io.File;
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
        
    public static void convertTableToCSV(JComponent parent, JTable table, String fileName) {
        JFileChooser fc = new JFileChooser();
        fc.setFileSelectionMode(JFileChooser.FILES_ONLY);
        fc.setDialogType(JFileChooser.SAVE_DIALOG);
        fc.setDialogTitle("Salvar em:");
        fc.setSelectedFile(new File(fileName + ".csv"));

        int result = fc.showSaveDialog(parent);
        if (result == JFileChooser.APPROVE_OPTION) {
            try {
                String path = CSVConverterService.exportToCSV(table, fc.getSelectedFile().getPath(), fileName);
                JOptionPane.showMessageDialog(parent, "Arquivo salvo em:\n\"" + path + "\"", "Sucesso!", JOptionPane.INFORMATION_MESSAGE);
            } catch (Exception e) {
                JOptionPane.showMessageDialog(parent, "Erro ao converter:\n" + e.getMessage(), "Erro!", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
}
