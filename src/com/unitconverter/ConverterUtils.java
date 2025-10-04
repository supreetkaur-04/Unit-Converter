package com.unitconverter;

import javax.swing.JTextField;
import javax.swing.JComboBox;


public class ConverterUtils {
    
    
    private ConverterUtils() {}
    
    
    public static boolean isValidNumber(String text) {
        if (text == null || text.trim().isEmpty()) {
            return false;
        }
        
        try {
            Double.parseDouble(text.trim());
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }
    
    
    public static boolean areUnitsDifferent(String fromUnit, String toUnit) {
        return !fromUnit.equals(toUnit);
    }
    
    
    public static String formatResult(double value) {
        if (value == 0) {
            return "0";
        }
        
        
        if (Math.abs(value) < 0.000001 || Math.abs(value) > 1000000) {
            return String.format("%.6e", value);
        }
        
        
        if (Math.abs(value) < 0.1) {
            return String.format("%.8f", value).replaceAll("0*$", "").replaceAll("\\.$", "");
        } else if (Math.abs(value) < 1) {
            return String.format("%.6f", value).replaceAll("0*$", "").replaceAll("\\.$", "");
        } else if (Math.abs(value) < 100) {
            return String.format("%.4f", value).replaceAll("0*$", "").replaceAll("\\.$", "");
        } else {
            return String.format("%.2f", value);
        }
    }
    
    
    public static void clearFields(JTextField... fields) {
        for (JTextField field : fields) {
            field.setText("");
        }
    }
    
    
    public static String capitalize(String str) {
        if (str == null || str.isEmpty()) {
            return str;
        }
        return str.substring(0, 1).toUpperCase() + str.substring(1).toLowerCase();
    }
    
    
    public static boolean areFieldsValid(JTextField inputField, 
                                       JComboBox<?> fromUnit, 
                                       JComboBox<?> toUnit) {
        return isValidNumber(inputField.getText()) &&
               fromUnit.getSelectedItem() != null &&
               toUnit.getSelectedItem() != null &&
               areUnitsDifferent(fromUnit.getSelectedItem().toString(), 
                               toUnit.getSelectedItem().toString());
    }
    
    
    public static String getCategoryDescription(String category) {
        switch (category) {
            case "Length": return "Distance Measurements";
            case "Weight": return "Mass Measurements";
            case "Temperature": return "Temperature Scales";
            case "Volume": return "Capacity Measurements";
            case "Area": return "Surface Area Measurements";
            default: return "Unit Conversions";
        }
    }
}
