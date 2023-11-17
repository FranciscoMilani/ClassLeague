package br.ucs.classleague.application.Services;

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
            JOptionPane.showMessageDialog(null, "O arquivo foi gerado no seu diretório 'C:/Temp'.", "Sucesso!", JOptionPane.INFORMATION_MESSAGE);
        } catch (IOException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Erro ao gerar arquivo .CSV'.", "ERRO!", JOptionPane.ERROR);

            return e.getMessage();
        }

        return path.toString();
    }
}
