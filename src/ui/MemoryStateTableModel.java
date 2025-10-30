package ui;

import core.MemoryState;

import javax.swing.table.AbstractTableModel;
import java.util.ArrayList;
import java.util.List;

public class MemoryStateTableModel extends AbstractTableModel {
    private final int partitions;
    private final List<MemoryState> rows;

    public MemoryStateTableModel(int partitions) {
        this.partitions = Math.max(1, partitions);
        this.rows = new ArrayList<>();
    }

    public void setRows(List<MemoryState> rows) {
        this.rows.clear();
        if (rows != null) {
            this.rows.addAll(rows);
        }
        fireTableDataChanged();
    }

    @Override
    public int getRowCount() {
        return rows.size();
    }

    @Override
    public int getColumnCount() {
        return 3 + partitions;
    }

    @Override
    public String getColumnName(int column) {
        if (column == 0) return "Passo";
        if (column == 1) return "Atual";
        if (column == 2) return "Saiu";
        int memIdx = column - 3;
        return "Mem[" + memIdx + "]";
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        if (columnIndex == 0) {
            // Coluna do passo
            // Apenas pegar a linha e somar mais 1
            return rowIndex + 1;
        }
        MemoryState state = rows.get(rowIndex);
        if (columnIndex == 1) {
            return state.currentPage;
        }
        if (columnIndex == 2) {
            return state.outPage == -1 ? "" : state.outPage;
        }
        int memIdx = columnIndex - 3;
        if (state.pages != null && memIdx < state.pages.size()) {
            return state.pages.get(memIdx);
        }
        return "";
    }

    @Override
    public Class<?> getColumnClass(int columnIndex) {
        boolean firstColumns = columnIndex == 0 || columnIndex == 1;
        if (firstColumns) return Integer.class;
        return Object.class;
    }

    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        return false;
    }
}
