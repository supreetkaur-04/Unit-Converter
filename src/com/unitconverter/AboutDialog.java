package com.unitconverter;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class AboutDialog extends JDialog {
    
    public AboutDialog(JFrame parent) {
        super(parent, "About Unit Converter", true);
        initializeDialog();
    }
    
    private void initializeDialog() {
        setSize(500, 400);
        setLocationRelativeTo(getParent());
        setResizable(false);
        
        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        
        
        JLabel titleLabel = new JLabel("Unit Converter Application", JLabel.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 20));
        titleLabel.setForeground(new Color(0, 102, 204));
        mainPanel.add(titleLabel, BorderLayout.NORTH);
        
        
        mainPanel.add(createContentPanel(), BorderLayout.CENTER);
        
        
        mainPanel.add(createButtonPanel(), BorderLayout.SOUTH);
        
        add(mainPanel);
    }
    
    private JPanel createContentPanel() {
        JPanel contentPanel = new JPanel(new BorderLayout(10, 10));
        
        
        String aboutText = "<html>" +
            "<div style='text-align: center;'>" +
            "<h3>Java OOP Unit Converter Project</h3>" +
            "</div>" +
            "<p>This application provides accurate unit conversions across multiple categories " +
            "including length, weight, temperature, volume, and area.</p>" +
            "<br>" +
            "<b>Features:</b>" +
            "<ul>" +
            "<li>Multi-category unit conversions</li>" +
            "<li>User-friendly graphical interface</li>" +
            "<li>Conversion history tracking</li>" +
            "<li>Database storage</li>" +
            "<li>Real-time validation</li>" +
            "</ul>" +
            "<br>" +
            "<b>Technologies Used:</b>" +
            "<ul>" +
            "<li>Java Swing for GUI</li>" +
            "<li>SQLite for database</li>" +
            "<li>JDBC for database connectivity</li>" +
            "<li>Object-Oriented Programming principles</li>" +
            "</ul>" +
            "<br>" +
            "<b>Educational Purpose:</b><br>" +
            "This project demonstrates comprehensive Java programming concepts " +
            "including OOP, exception handling, database connectivity, and GUI development." +
            "</html>";
        
        JTextPane textPane = new JTextPane();
        textPane.setContentType("text/html");
        textPane.setText(aboutText);
        textPane.setEditable(false);
        textPane.setBackground(getBackground());
        
        JScrollPane scrollPane = new JScrollPane(textPane);
        scrollPane.setBorder(BorderFactory.createTitledBorder("Project Information"));
        contentPanel.add(scrollPane, BorderLayout.CENTER);
        
        return contentPanel;
    }
    
    private JPanel createButtonPanel() {
        JPanel buttonPanel = new JPanel(new FlowLayout());
        
        JButton okButton = new JButton("OK");
        JButton featuresButton = new JButton("View Features");
        JButton technicalButton = new JButton("Technical Details");
        
        
        okButton.setBackground(new Color(70, 130, 180));
        okButton.setForeground(Color.WHITE);
        featuresButton.setBackground(new Color(34, 139, 34));
        featuresButton.setForeground(Color.WHITE);
        technicalButton.setBackground(new Color(138, 43, 226));
        technicalButton.setForeground(Color.WHITE);
        
        
        okButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });
        
        featuresButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showFeaturesDialog();
            }
        });
        
        technicalButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showTechnicalDetails();
            }
        });
        
        buttonPanel.add(featuresButton);
        buttonPanel.add(technicalButton);
        buttonPanel.add(okButton);
        
        return buttonPanel;
    }
    
    private void showFeaturesDialog() {
        String featuresText = "<html>" +
            "<h3>Application Features</h3>" +
            "<b>Supported Categories:</b>" +
            "<ul>" +
            "<li><b>Length:</b> Meter, Kilometer, Centimeter, Millimeter, Mile, Yard, Foot, Inch</li>" +
            "<li><b>Weight:</b> Kilogram, Gram, Milligram, Pound, Ounce, Ton</li>" +
            "<li><b>Temperature:</b> Celsius, Fahrenheit, Kelvin</li>" +
            "<li><b>Volume:</b> Liter, Milliliter, Gallon, Cubic Meter</li>" +
            "<li><b>Area:</b> Square Meter, Square Kilometer, Acre, Hectare</li>" +
            "</ul>" +
            "<br>" +
            "<b>Additional Features:</b>" +
            "<ul>" +
            "<li>Real-time input validation</li>" +
            "<li>Conversion history tracking</li>" +
            "<li>Database persistence</li>" +
            "<li>Error handling and user feedback</li>" +
            "<li>Clean and intuitive interface</li>" +
            "</ul>" +
            "</html>";
            
        JOptionPane.showMessageDialog(this, 
            new JLabel(featuresText), 
            "Application Features", 
            JOptionPane.INFORMATION_MESSAGE);
    }
    
    private void showTechnicalDetails() {
        String technicalText = "<html>" +
            "<h3>Technical Implementation</h3>" +
            "<b>Java Concepts Demonstrated:</b>" +
            "<ul>" +
            "<li>Object-Oriented Programming (OOP)</li>" +
            "<li>Inheritance and Polymorphism</li>" +
            "<li>Exception Handling</li>" +
            "<li>Collections Framework</li>" +
            "<li>JDBC Database Connectivity</li>" +
            "<li>Swing GUI Components</li>" +
            "<li>Event Handling</li>" +
            "<li>String Manipulation</li>" +
            "<li>Package Organization</li>" +
            "</ul>" +
            "<br>" +
            "<b>Design Patterns:</b>" +
            "<ul>" +
            "<li>MVC (Model-View-Controller) pattern</li>" +
            "<li>Singleton pattern for database manager</li>" +
            "<li>Utility classes with static methods</li>" +
            "</ul>" +
            "</html>";
            
        JOptionPane.showMessageDialog(this, 
            new JLabel(technicalText), 
            "Technical Details", 
            JOptionPane.INFORMATION_MESSAGE);
    }
}
