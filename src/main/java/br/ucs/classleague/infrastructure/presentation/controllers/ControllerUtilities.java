package br.ucs.classleague.infrastructure.presentation.controllers;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
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
}
