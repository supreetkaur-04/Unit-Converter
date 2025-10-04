package com.unitconverter.exceptions;


public class InvalidUnitException extends Exception {
    
    
    public InvalidUnitException(String message) {
        super(message);
    }
    
    
    public InvalidUnitException(String message, Throwable cause) {
        super(message, cause);
    }
    
    
    public static InvalidUnitException createCategoryMismatch(String fromCategory, String toCategory) {
        String message = String.format(
            "Cannot convert between different categories: %s to %s", 
            fromCategory, toCategory
        );
        return new InvalidUnitException(message);
    }
    
    
    public static InvalidUnitException createUnknownUnit(String unit, String category) {
        String message = String.format(
            "Unknown unit '%s' for category '%s'", 
            unit, category
        );
        return new InvalidUnitException(message);
    }
    
    
    public static InvalidUnitException createNegativeValue(double value, String unit) {
        String message = String.format(
            "Negative values are not allowed for %s: %.2f", 
            unit, value
        );
        return new InvalidUnitException(message);
    }
    
    
    public static InvalidUnitException createAbsoluteZeroViolation(double value, String unit) {
        String message = String.format(
            "Temperature cannot be below absolute zero: %.2f %s", 
            value, unit
        );
        return new InvalidUnitException(message);
    }
}
