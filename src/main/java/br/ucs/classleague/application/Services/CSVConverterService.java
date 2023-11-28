package br.ucs.classleague.application.services;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import javax.swing.JTable;
import javax.swing.table.TableModel;

public class CSVConverterService {

    public static String exportToCSV(JTable table, String dirPath, String fileName) {  
        
        if (!fileName.toLowerCase().endsWith(".csv")) {
            fileName += ".csv";
        }
        
        Path path = Paths.get(dirPath);
        try (FileWriter csv = new FileWriter(path.toFile())) {
            TableModel model = table.getModel();

            for (int i = 0; i < model.getColumnCount(); i++) {
                csv.write(escapeNull(model.getColumnName(i)) + ",");
            }

            csv.write("\n");

            for (int i = 0; i < model.getRowCount(); i++) {
                for (int j = 0; j < model.getColumnCount(); j++) {
                    csv.write(escapeNull(model.getValueAt(i, j)) + ",");
                }
                csv.write("\n");
            }
            csv.close();
        } catch (IOException e) {
            e.printStackTrace();
            return e.getMessage();
        }

        return path.toString();
    }
    
    private static String escapeNull(Object value) {
        return (value != null) ? value.toString() : "";
    }
    
    private static void prepareDirectory(File directory) {
        try {
            if (!directory.exists()) {
                directory.mkdir();
            }     
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
