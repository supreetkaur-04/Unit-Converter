package com.unitconverter;

import javax.swing.*;
import java.awt.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;


public class DatabaseManager {
    private Connection connection;
    private static final String DB_URL = "jdbc:sqlite:unit_converter.db";
    
    public DatabaseManager() {
        initializeDatabase();
    }
    
    
    private void initializeDatabase() {
        try {
            
            Class.forName("org.sqlite.JDBC");
            
            
            connection = DriverManager.getConnection(DB_URL);
            
            
            String createTableSQL = "CREATE TABLE IF NOT EXISTS conversion_history (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "category TEXT NOT NULL, " +
                "from_unit TEXT NOT NULL, " +
                "to_unit TEXT NOT NULL, " +
                "input_value REAL NOT NULL, " +
                "result_value REAL NOT NULL, " +
                "conversion_date DATETIME DEFAULT CURRENT_TIMESTAMP" +
                ")";
            
            Statement statement = connection.createStatement();
            statement.execute(createTableSQL);
            statement.close();
            
            System.out.println("Database initialized successfully");
            
        } catch (ClassNotFoundException e) {
            System.err.println("SQLite JDBC driver not found: " + e.getMessage());
        } catch (SQLException e) {
            System.err.println("Database initialization failed: " + e.getMessage());
        }
    }
    
    
    public void saveConversion(String category, String fromUnit, String toUnit, 
                             double inputValue, double resultValue) {
        String sql = "INSERT INTO conversion_history (category, from_unit, to_unit, input_value, result_value) " +
                    "VALUES (?, ?, ?, ?, ?)";
        
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, category);
            pstmt.setString(2, fromUnit);
            pstmt.setString(3, toUnit);
            pstmt.setDouble(4, inputValue);
            pstmt.setDouble(5, resultValue);
            
            pstmt.executeUpdate();
            System.out.println("Conversion saved to database");
            
        } catch (SQLException e) {
            System.err.println("Error saving conversion: " + e.getMessage());
        }
    }
    
    
    public List<ConversionRecord> getConversionHistory() {
        List<ConversionRecord> history = new ArrayList<>();
        String sql = "SELECT * FROM conversion_history ORDER BY conversion_date DESC LIMIT 50";
        
        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            
            while (rs.next()) {
                ConversionRecord record = new ConversionRecord(
                    rs.getInt("id"),
                    rs.getString("category"),
                    rs.getString("from_unit"),
                    rs.getString("to_unit"),
                    rs.getDouble("input_value"),
                    rs.getDouble("result_value"),
                    rs.getTimestamp("conversion_date")
                );
                history.add(record);
            }
            
        } catch (SQLException e) {
            System.err.println("Error retrieving history: " + e.getMessage());
        }
        
        return history;
    }
    
    
    
    
        
    
    
    
    
    
    
        
    
    
    
        
    
    
    
    
    
    
    
    
    
    
        
    
    
    
    
        
    
    
    
    
    
    
    
    
    public void showHistory(Component parent) {
        
        
        
        
        
        
        
        JDialog historyDialog = new JDialog();
        historyDialog.setTitle("Conversion History");
        historyDialog.setModal(true);
        historyDialog.setSize(700, 500);
        historyDialog.setLocationRelativeTo(parent);
        
        HistoryPanel historyPanel = new HistoryPanel(this);
        historyDialog.add(historyPanel);
        historyDialog.setVisible(true);
    }
    
    public void close() {
        try {
            if (connection != null && !connection.isClosed()) {
                connection.close();
                System.out.println("Database connection closed");
            }
        } catch (SQLException e) {
            System.err.println("Error closing database: " + e.getMessage());
        }
    }
    
    
    public static class ConversionRecord {
        private int id;
        private String category;
        private String fromUnit;
        private String toUnit;
        private double inputValue;
        private double resultValue;
        private Timestamp conversionDate;
        
        public ConversionRecord(int id, String category, String fromUnit, String toUnit,
                              double inputValue, double resultValue, Timestamp conversionDate) {
            this.id = id;
            this.category = category;
            this.fromUnit = fromUnit;
            this.toUnit = toUnit;
            this.inputValue = inputValue;
            this.resultValue = resultValue;
            this.conversionDate = conversionDate;
        }
        
        
        public int getId() { return id; }
        public String getCategory() { return category; }
        public String getFromUnit() { return fromUnit; }
        public String getToUnit() { return toUnit; }
        public double getInputValue() { return inputValue; }
        public double getResultValue() { return resultValue; }
        public Timestamp getConversionDate() { return conversionDate; }
        
        public String getFormattedDate() {
            return new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
                    .format(conversionDate);
        }
    }

    
    public Connection getConnection() throws SQLException {
        if (connection == null || connection.isClosed()) {
            initializeDatabase();
        }
        return connection;
    }

    
    public boolean deleteConversion(int id) {
        String sql = "DELETE FROM conversion_history WHERE id = ?";
        
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            System.err.println("Error deleting conversion: " + e.getMessage());
            return false;
        }
    }

    
    public boolean deleteAllConversions() {
        String sql = "DELETE FROM conversion_history";
        
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected >= 0; 
        } catch (SQLException e) {
            System.err.println("Error deleting all conversions: " + e.getMessage());
            return false;
        }
    }

    
    public List<ConversionRecord> getConversionHistory(int limit) {
        List<ConversionRecord> history = new ArrayList<>();
        String sql = "SELECT * FROM conversion_history ORDER BY conversion_date DESC LIMIT ?";
        
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setInt(1, limit);
            ResultSet rs = pstmt.executeQuery();
            
            while (rs.next()) {
                ConversionRecord record = new ConversionRecord(
                    rs.getInt("id"),
                    rs.getString("category"),
                    rs.getString("from_unit"),
                    rs.getString("to_unit"),
                    rs.getDouble("input_value"),
                    rs.getDouble("result_value"),
                    rs.getTimestamp("conversion_date")
                );
                history.add(record);
            }
            
        } catch (SQLException e) {
            System.err.println("Error retrieving history: " + e.getMessage());
        }
        
        return history;
    }
}

