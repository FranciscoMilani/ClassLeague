package br.ucs.classleague.application.Services;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.TableModel;

public class CSVConverterService {

    public static String exportToCSV(JTable table, String dirPath, String fileName) {
        Path path = Paths.get(dirPath, fileName + ".csv");
        Path p = Paths.get(dirPath);
        
        prepareDirectory(p.toFile());

        try (FileWriter csv = new FileWriter(path.toFile())) {
            TableModel model = table.getModel();

            for (int i = 0; i < model.getColumnCount(); i++) {
                csv.write(model.getColumnName(i) + ",");
            }

            csv.write("\n");

            for (int i = 0; i < model.getRowCount(); i++) {
                for (int j = 0; j < model.getColumnCount(); j++) {
                    csv.write(model.getValueAt(i, j).toString() + ",");
                }
                csv.write("\n");
            }
            csv.close();
            JOptionPane.showMessageDialog(table.getParent(), "O arquivo foi gerado no seu diretório 'C:/Temp'.", "Sucesso!", JOptionPane.INFORMATION_MESSAGE);
        } catch (IOException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(table.getParent(), "Erro ao gerar arquivo .CSV'.", "Erro!", JOptionPane.ERROR);

            return e.getMessage();
        }

        return path.toString();
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
