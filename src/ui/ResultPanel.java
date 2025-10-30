package ui;

import core.MemoryState;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.util.Collections;
import java.util.List;
import java.util.Map;

public class ResultPanel extends JPanel {
    private final JTextArea resultArea;
    private final JTable table;
    private final JComboBox<String> comboBox;
    private MemoryStateTableModel tableModel;
    private final Map<String, List<MemoryState>> memoryStates;
    private String selectedAlgorithm = "Nenhum";

    public ResultPanel(int partitions, Map<String, List<MemoryState>> memoryStates) {
        super();

        this.memoryStates = memoryStates;

        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        setBorder(BorderFactory.createTitledBorder("Resultado"));

        // Texto de resultado
        resultArea = new JTextArea(5, 30);
        resultArea.setEditable(false);
        resultArea.setLineWrap(true);
        resultArea.setWrapStyleWord(true);

        add(new JScrollPane(resultArea));

        // Combo box para visualizar selecionar tabela
        JPanel comboBoxPanel = new JPanel();
        comboBoxPanel.setBorder(new EmptyBorder(15, 0, 15, 0));
        JLabel label = new JLabel("Algoritmo de Substituição:");
        label.setBounds(0, 50, 200, 25);
        comboBoxPanel.add(label);

        // ComboBox com as opções
        String[] algoritmos = {"Nenhum", "LRU", "FIFO", "NFU", "Clock"};
        comboBox = new JComboBox<>(algoritmos);
        comboBox.setBounds(30, 50, 200, 25);
        comboBox.addActionListener(_ -> updateTable());
        comboBoxPanel.add(comboBox);

        add(comboBoxPanel);

        // Tabela de passos
        tableModel = new MemoryStateTableModel(partitions);
        table = new JTable(tableModel);
        table.setFillsViewportHeight(true);

        add(new JScrollPane(table));
    }

    public void updateTableModel(int partitions) {
        tableModel = new MemoryStateTableModel(partitions);
        updateTable();
    }

    public void updateTable() {
        selectedAlgorithm = (String) comboBox.getSelectedItem();
        if (selectedAlgorithm.equals("Nenhum")) {
            tableModel.setRows(Collections.emptyList());
            return;
        }
        tableModel.setRows(memoryStates.get(selectedAlgorithm));
        table.setModel(tableModel);
    }

    public void clearResult() {
        resultArea.setText("");
        tableModel.setRows(Collections.emptyList());
        table.setModel(tableModel);
    }

    public void setResult(String result) {
        resultArea.setText(result);
    }
}
