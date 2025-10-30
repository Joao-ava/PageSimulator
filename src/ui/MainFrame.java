package ui;

import core.FIFO;
import core.LRU;
import core.MemoryState;

import javax.swing.*;
import java.awt.*;
import java.util.*;
import java.util.List;

public class MainFrame extends JFrame {
    private final JSpinner partitionsSpinner;
    private final JTextField pagesField;
    private final JTextArea resultArea;
    private final JTable table;
    private MemoryStateTableModel tableModel;
    private Map<String, List<MemoryState>> memoryStates;

    public MainFrame() {
        super("Simulador de algoritmos de substituição de páginas");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(900, 600);
        setLocationRelativeTo(null);

        // map com MemoryState
        memoryStates = new HashMap<>();
        memoryStates.put("LRU", new ArrayList<>());
        memoryStates.put("FIFO", new ArrayList<>());

        partitionsSpinner = new JSpinner(new SpinnerNumberModel(5, 1, Integer.MAX_VALUE, 1));
        pagesField = new JTextField("1 2 3 4 5 6 5 4 3 2 1");
        resultArea = new JTextArea(5, 30);
        resultArea.setEditable(false);
        resultArea.setLineWrap(true);
        resultArea.setWrapStyleWord(true);

        // Tabela de passos
        tableModel = new MemoryStateTableModel(((Number) partitionsSpinner.getValue()).intValue());
        table = new JTable(tableModel);
        table.setFillsViewportHeight(true);

        JPanel form = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(6, 6, 6, 6);
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Row 0 - partições
        gbc.gridx = 0; gbc.gridy = 0; gbc.weightx = 0;
        form.add(new JLabel("Tamanho das partições:"), gbc);
        gbc.gridx = 1; gbc.weightx = 1.0;
        form.add(partitionsSpinner, gbc);

        // Row 1 - páginas em ordem
        gbc.gridx = 0; gbc.gridy = 1; gbc.weightx = 0;
        form.add(new JLabel("Ordem das páginas (separadas por espaço):"), gbc);
        gbc.gridx = 1; gbc.weightx = 1.0;
        form.add(pagesField, gbc);

        // Row 2 - botão gerar e processar
        gbc.gridx = 0; gbc.gridy = 2; gbc.gridwidth = 1; gbc.weightx = 0;
        JButton generateButton = new JButton("Gerar exemplo");
        generateButton.addActionListener(e -> onGenerate());
        form.add(generateButton, gbc);

        gbc.gridx = 1; gbc.gridy = 2; gbc.gridwidth = 1; gbc.weightx = 0;
        JButton processButton = new JButton("Processar");
        processButton.addActionListener(e -> onProcess());
        form.add(processButton, gbc);

        JPanel resultPanel = new JPanel(new BorderLayout());
        resultPanel.setBorder(BorderFactory.createTitledBorder("Resultado"));

        JSplitPane split = new JSplitPane(JSplitPane.VERTICAL_SPLIT, new JScrollPane(resultArea), new JScrollPane(table));
        split.setResizeWeight(0.3);
        resultPanel.add(split, BorderLayout.CENTER);

        getContentPane().setLayout(new BorderLayout());
        getContentPane().add(form, BorderLayout.NORTH);
        getContentPane().add(resultPanel, BorderLayout.CENTER);
    }

    private void onGenerate() {
        // Exemplo clássico para demonstrar LRU
        int partitions = 3;
        partitionsSpinner.setValue(partitions);

        String example = "1 2 3 4 1 2 5 1 2 3 4 5";
        pagesField.setText(example);

        // Limpa o resultado anterior para evitar confusão
        resultArea.setText("");
        // Limpa tabela
        if (tableModel != null) {
            tableModel.setRows(Collections.emptyList());
        }
    }

    private void onProcess() {
        try {
            int partitions = ((Number) partitionsSpinner.getValue()).intValue();
            if (partitions <= 0) {
                showError("O tamanho das partições deve ser um número inteiro positivo.");
                return;
            }

            String pagesText = pagesField.getText().trim();
            if (pagesText.isEmpty()) {
                showError("Informe a ordem das páginas (números inteiros separados por espaço).");
                return;
            }

            String[] tokens = pagesText.split("\\s+");
            List<Integer> list = new ArrayList<>();
            for (String t : tokens) {
                if (t.isEmpty()) continue;
                list.add(Integer.parseInt(t));
            }
            if (list.isEmpty()) {
                showError("Informe ao menos um número de página.");
                return;
            }
            int[] pages = new int[list.size()];
            for (int i = 0; i < list.size(); i++) {
                pages[i] = list.get(i);
            }

            // Executa LRU e coleta estados para a tabela
            LRU lru = new LRU(pages, partitions);
            int lruFaults = lru.execute();

            // Atualiza/Cria o modelo da tabela conforme o número de partições
            tableModel = new MemoryStateTableModel(partitions);
            table.setModel(tableModel);
            tableModel.setRows(lru.getMemoryList());

            // Calcula também o FIFO (placeholder atual)
            FIFO fifo = new FIFO(pages, partitions);
            int fifoFaults = fifo.execute();

            StringBuilder sb = new StringBuilder();
            sb.append("LRU: ").append(lruFaults).append(" faltas encontradas\n");
            sb.append("FIFO: ").append(fifoFaults).append(" faltas encontradas");
            resultArea.setText(sb.toString());
        } catch (NumberFormatException ex) {
            showError("Use apenas números inteiros separados por espaço na ordem das páginas.");
        } catch (Exception ex) {
            showError("Erro ao processar: " + ex.getMessage());
        }
    }

    private String makeProcessResult(int[] pages, int partitions) {
        StringBuilder sb = new StringBuilder();
        // LRU
        sb.append("LRU: ");
        LRU lru = new LRU(pages, partitions);
        sb.append(lru.execute()).append(" faltas encontradas");
        sb.append("\n");
        // FIFO
        sb.append("FIFO: ");
        FIFO fifo = new FIFO(pages, partitions);
        sb.append(fifo.execute()).append(" faltas encontradas");

        return sb.toString();
    }

    private void showError(String msg) {
        JOptionPane.showMessageDialog(this, msg, "Erro", JOptionPane.ERROR_MESSAGE);
    }
}
