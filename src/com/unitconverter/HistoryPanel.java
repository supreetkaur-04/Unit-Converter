package com.unitconverter;

import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;


public class HistoryPanel extends JPanel {
    private JTable historyTable;
    private HistoryTableModel tableModel;
    private JButton refreshButton;
    private JButton clearAllButton;
    private DatabaseManager dbManager;
    
    public HistoryPanel(DatabaseManager dbManager) {
        this.dbManager = dbManager;
        initializePanel();
        loadHistoryData();
    }
    
    private void initializePanel() {
        setLayout(new BorderLayout(10, 10));
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        
        JLabel titleLabel = new JLabel("Conversion History", JLabel.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 18));
        add(titleLabel, BorderLayout.NORTH);
        
        
        tableModel = new HistoryTableModel();
        historyTable = new JTable(tableModel);
        historyTable.setFillsViewportHeight(true);
        historyTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        historyTable.setRowHeight(30);
        
        
        historyTable.getColumnModel().getColumn(0).setPreferredWidth(150); 
        historyTable.getColumnModel().getColumn(1).setPreferredWidth(100); 
        historyTable.getColumnModel().getColumn(2).setPreferredWidth(80);  
        historyTable.getColumnModel().getColumn(3).setPreferredWidth(80);  
        historyTable.getColumnModel().getColumn(4).setPreferredWidth(100); 
        historyTable.getColumnModel().getColumn(5).setPreferredWidth(100); 
        historyTable.getColumnModel().getColumn(6).setPreferredWidth(80);  
        
        
        historyTable.getColumnModel().getColumn(6).setCellRenderer(new ButtonRenderer());
        historyTable.getColumnModel().getColumn(6).setCellEditor(new ButtonEditor(new JCheckBox()));
        
        JScrollPane scrollPane = new JScrollPane(historyTable);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        add(scrollPane, BorderLayout.CENTER);
        
        
        add(createButtonPanel(), BorderLayout.SOUTH);
    }
    
    private JPanel createButtonPanel() {
        JPanel buttonPanel = new JPanel(new FlowLayout());
        
        refreshButton = new JButton("Refresh");
        clearAllButton = new JButton("Clear All History");
        
        
        refreshButton.setBackground(new Color(70, 130, 180));
        refreshButton.setForeground(Color.WHITE);
        clearAllButton.setBackground(new Color(220, 20, 60));
        clearAllButton.setForeground(Color.WHITE);
        
        
        refreshButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                loadHistoryData();
            }
        });
        
        clearAllButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                clearAllHistory();
            }
        });
        
        buttonPanel.add(refreshButton);
        buttonPanel.add(clearAllButton);
        
        return buttonPanel;
    }
    
    
    public void loadHistoryData() {
        if (dbManager == null) {
            JOptionPane.showMessageDialog(this, 
                "Database not available", 
                "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        List<DatabaseManager.ConversionRecord> history = dbManager.getConversionHistory(50);
        tableModel.setHistoryData(history);
        
        // if (history.isEmpty()) {
        //     JOptionPane.showMessageDialog(this, 
        //         "No conversion history found.", 
        //         "Information", JOptionPane.INFORMATION_MESSAGE);
        // }         
    }
    
    
    private void clearAllHistory() {
        if (dbManager == null) {
            JOptionPane.showMessageDialog(this, 
                "Database not available", 
                "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        int confirmation = JOptionPane.showConfirmDialog(this,
            "Are you sure you want to clear ALL conversion history?\nThis action cannot be undone.",
            "Confirm Clear All",
            JOptionPane.YES_NO_OPTION,
            JOptionPane.WARNING_MESSAGE);
            
        if (confirmation == JOptionPane.YES_OPTION) {
            boolean success = dbManager.deleteAllConversions();
            if (success) {
                tableModel.clearData();
                JOptionPane.showMessageDialog(this,
                    "All conversion history cleared successfully",
                    "Success",
                    JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(this,
                    "Error clearing history",
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
            }
        }
    }
    
    
    private void removeConversion(int recordId) {
        int confirmation = JOptionPane.showConfirmDialog(this,
            "Are you sure you want to remove this conversion record?",
            "Confirm Remove",
            JOptionPane.YES_NO_OPTION,
            JOptionPane.QUESTION_MESSAGE);
            
        if (confirmation == JOptionPane.YES_OPTION) {
            boolean success = dbManager.deleteConversion(recordId);
            if (success) {
                loadHistoryData(); 
                JOptionPane.showMessageDialog(this,
                    "Conversion record removed successfully",
                    "Success",
                    JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(this,
                    "Error removing conversion record",
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
            }
        }
    }
    
    
    private class HistoryTableModel extends AbstractTableModel {
        private List<DatabaseManager.ConversionRecord> historyData;
        private final String[] columnNames = {
            "Date & Time", "Category", "From Unit", "To Unit", 
            "Input Value", "Result Value", "Actions"
        };
        
        public HistoryTableModel() {
            this.historyData = new java.util.ArrayList<>();
        }
        
        public void setHistoryData(List<DatabaseManager.ConversionRecord> historyData) {
            this.historyData = historyData;
            fireTableDataChanged();
        }
        
        public void clearData() {
            this.historyData.clear();
            fireTableDataChanged();
        }
        
        @Override
        public int getRowCount() {
            return historyData.size();
        }
        
        @Override
        public int getColumnCount() {
            return columnNames.length;
        }
        
        @Override
        public String getColumnName(int column) {
            return columnNames[column];
        }
        
        @Override
        public Object getValueAt(int rowIndex, int columnIndex) {
            if (rowIndex >= historyData.size()) return null;
            
            DatabaseManager.ConversionRecord record = historyData.get(rowIndex);
            
            switch (columnIndex) {
                case 0: return record.getFormattedDate();
                case 1: return record.getCategory();
                case 2: return record.getFromUnit();
                case 3: return record.getToUnit();
                case 4: return String.format("%.6f", record.getInputValue());
                case 5: return String.format("%.6f", record.getResultValue());
                case 6: return "Remove";
                default: return null;
            }
        }
        
        @Override
        public boolean isCellEditable(int rowIndex, int columnIndex) {
            return columnIndex == 6; 
        }
        
        public DatabaseManager.ConversionRecord getRecordAt(int rowIndex) {
            if (rowIndex >= 0 && rowIndex < historyData.size()) {
                return historyData.get(rowIndex);
            }
            return null;
        }
    }
    
    
    private class ButtonRenderer extends JButton implements javax.swing.table.TableCellRenderer {
        public ButtonRenderer() {
            setOpaque(true);
        }
        
        @Override
        public Component getTableCellRendererComponent(JTable table, Object value,
                boolean isSelected, boolean hasFocus, int row, int column) {
            setText((value == null) ? "" : value.toString());
            setBackground(new Color(220, 20, 60)); 
            setForeground(Color.WHITE);
            setFont(getFont().deriveFont(Font.BOLD));
            return this;
        }
    }
    
    
    private class ButtonEditor extends DefaultCellEditor {
        private JButton button;
        private String label;
        private boolean isPushed;
        private int currentRow;
        
        public ButtonEditor(JCheckBox checkBox) {
            super(checkBox);
            button = new JButton();
            button.setOpaque(true);
            button.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    fireEditingStopped();
                }
            });
        }
        
        @Override
        public Component getTableCellEditorComponent(JTable table, Object value,
                boolean isSelected, int row, int column) {
            label = (value == null) ? "" : value.toString();
            button.setText(label);
            button.setBackground(new Color(200, 0, 0)); 
            button.setForeground(Color.WHITE);
            isPushed = true;
            currentRow = row;
            return button;
        }
        
        @Override
        public Object getCellEditorValue() {
            if (isPushed) {
                
                DatabaseManager.ConversionRecord record = tableModel.getRecordAt(currentRow);
                if (record != null) {
                    removeConversion(record.getId());
                }
            }
            isPushed = false;
            return label;
        }
        
        @Override
        public boolean stopCellEditing() {
            isPushed = false;
            return super.stopCellEditing();
        }
    }
}
