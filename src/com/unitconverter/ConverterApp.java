package com.unitconverter;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.HashMap;
import java.util.Map;


public class ConverterApp extends JFrame {
    private JComboBox<String> categoryComboBox;
    private JComboBox<String> fromUnitComboBox;
    private JComboBox<String> toUnitComboBox;
    private JTextField inputField;
    private JTextField resultField;
    private JButton convertButton;
    private JButton clearButton;
    private JButton historyButton;
    
    
    private Map<String, String[]> unitCategories;
    private DatabaseManager dbManager;
    
    public ConverterApp() {
        initializeUnitCategories();
        initializeDatabase();
        createGUI();
        setupEventHandlers();
    }
    
    private void initializeUnitCategories() {
        unitCategories = new HashMap<>();
        
        
        unitCategories.put("Length", new String[]{"Meter", "Kilometer", "Centimeter", "Millimeter", "Mile", "Yard", "Foot", "Inch"});
        
        
        unitCategories.put("Weight", new String[]{"Kilogram", "Gram", "Milligram", "Pound", "Ounce", "Ton"});
        
        
        unitCategories.put("Temperature", new String[]{"Celsius", "Fahrenheit", "Kelvin"});
        
        
        unitCategories.put("Volume", new String[]{"Liter", "Milliliter", "Gallon", "Cubic Meter"});
        
        
        unitCategories.put("Area", new String[]{"Square Meter", "Square Kilometer", "Acre", "Hectare"});

        
        
    }
    
    
    
    
    
    
    
    
    
    
    
    private void initializeDatabase() {
        try {
            dbManager = new DatabaseManager();
        } catch (Exception e) {
            System.out.println("Database initialization failed: " + e.getMessage());
            
            dbManager = new DatabaseManager() {
                @Override
                public void saveConversion(String category, String fromUnit, String toUnit, 
                                        double inputValue, double resultValue) {
                    
                    System.out.println("Conversion not saved (database unavailable)");
                }
                
                @Override
                public void showHistory(Component parent) {
                    JOptionPane.showMessageDialog(parent, 
                        "History feature unavailable without database.\n" +
                        "To enable history, add SQLite JDBC driver to lib folder.", 
                        "History Unavailable", JOptionPane.WARNING_MESSAGE);
                }
            };
        }
    }

    private void createGUI() {
        setTitle("Unit Converter - Java OOP Project");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(500, 400);
        setLocationRelativeTo(null);
        
        
        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        
        
        JLabel titleLabel = new JLabel("Unit Converter", JLabel.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 20));
        mainPanel.add(titleLabel, BorderLayout.NORTH);
        
        
        mainPanel.add(createConversionPanel(), BorderLayout.CENTER);
        
        
        mainPanel.add(createButtonPanel(), BorderLayout.SOUTH);
        
        add(mainPanel);
    }
    
    private JPanel createConversionPanel() {
        JPanel panel = new JPanel(new GridLayout(5, 2, 10, 10));
        
        
        panel.add(new JLabel("Category:"));
        categoryComboBox = new JComboBox<>(unitCategories.keySet().toArray(new String[0]));
        panel.add(categoryComboBox);
        
        
        panel.add(new JLabel("From Unit:"));
        fromUnitComboBox = new JComboBox<>();
        panel.add(fromUnitComboBox);
        
        
        panel.add(new JLabel("To Unit:"));
        toUnitComboBox = new JComboBox<>();
        panel.add(toUnitComboBox);
        
        
        panel.add(new JLabel("Input Value:"));
        inputField = new JTextField();
        panel.add(inputField);
        
        
        panel.add(new JLabel("Result:"));
        resultField = new JTextField();
        resultField.setEditable(false);
        panel.add(resultField);
        
        updateUnitComboBoxes();

        return panel;
    }
    
    private JPanel createButtonPanel() {
        JPanel panel = new JPanel(new FlowLayout());
        
        convertButton = new JButton("Convert");
        clearButton = new JButton("Clear");
        historyButton = new JButton("History");
        
        panel.add(convertButton);
        panel.add(clearButton);
        panel.add(historyButton);
        
        return panel;
    }
    
    private void setupEventHandlers() {
        
        categoryComboBox.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                updateUnitComboBoxes();
            }
        });
        
        
        convertButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                performConversion();
            }
        });
        
        
        clearButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                inputField.setText("");
                resultField.setText("");
            }
        });
        
        
        historyButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                showConversionHistory();
            }
        });
        
        
        inputField.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                performConversion();
            }
        });
    }
    
    private void updateUnitComboBoxes() {
        String category = (String) categoryComboBox.getSelectedItem();
        String[] units = unitCategories.get(category);
        
        fromUnitComboBox.setModel(new DefaultComboBoxModel<>(units));
        toUnitComboBox.setModel(new DefaultComboBoxModel<>(units));
        
        
        if (units.length > 1) {
            toUnitComboBox.setSelectedIndex(1);
        }
    }
    
    private void performConversion() {
        try {
            String category = (String) categoryComboBox.getSelectedItem();
            String fromUnit = (String) fromUnitComboBox.getSelectedItem();
            String toUnit = (String) toUnitComboBox.getSelectedItem();
            double inputValue = Double.parseDouble(inputField.getText());
            
            
            double result = ConversionLogic.convert(category, fromUnit, toUnit, inputValue);
            
            
            resultField.setText(String.format("%.6f", result));
            
            
            if (dbManager != null) {
                dbManager.saveConversion(category, fromUnit, toUnit, inputValue, result);
            }
            
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, 
                "Please enter a valid number!", 
                "Input Error", JOptionPane.ERROR_MESSAGE);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, 
                "Conversion error: " + e.getMessage(), 
                "Conversion Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void showConversionHistory() {
        if (dbManager != null) {
            dbManager.showHistory(this);
        } else {
            JOptionPane.showMessageDialog(this, 
                "Database not available", 
                "History", JOptionPane.INFORMATION_MESSAGE);
        }
    }
}
