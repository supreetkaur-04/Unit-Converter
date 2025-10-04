package com.unitconverter;


public class ConversionLogic {
    
    
    private ConversionLogic() {}
    
    
    public static double convert(String category, String fromUnit, String toUnit, double value) {
        if (fromUnit.equals(toUnit)) {
            return value; 
        }
        
        switch (category) {
            case "Length":
                return convertLength(fromUnit, toUnit, value);
            case "Weight":
                return convertWeight(fromUnit, toUnit, value);
            case "Temperature":
                return convertTemperature(fromUnit, toUnit, value);
            case "Volume":
                return convertVolume(fromUnit, toUnit, value);
            case "Area":
                return convertArea(fromUnit, toUnit, value);
            default:
                throw new IllegalArgumentException("Unknown category: " + category);
        }
    }
    
    
    private static double convertLength(String fromUnit, String toUnit, double value) {
        
        double meters = toMeters(fromUnit, value);
        
        
        return fromMeters(toUnit, meters);
    }
    
    private static double toMeters(String unit, double value) {
        switch (unit) {
            case "Meter": return value;
            case "Kilometer": return value * 1000;
            case "Centimeter": return value * 0.01;
            case "Millimeter": return value * 0.001;
            case "Mile": return value * 1609.344;
            case "Yard": return value * 0.9144;
            case "Foot": return value * 0.3048;
            case "Inch": return value * 0.0254;
            default: throw new IllegalArgumentException("Unknown length unit: " + unit);
        }
    }
    
    private static double fromMeters(String unit, double meters) {
        switch (unit) {
            case "Meter": return meters;
            case "Kilometer": return meters / 1000;
            case "Centimeter": return meters / 0.01;
            case "Millimeter": return meters / 0.001;
            case "Mile": return meters / 1609.344;
            case "Yard": return meters / 0.9144;
            case "Foot": return meters / 0.3048;
            case "Inch": return meters / 0.0254;
            default: throw new IllegalArgumentException("Unknown length unit: " + unit);
        }
    }
    
    
    private static double convertWeight(String fromUnit, String toUnit, double value) {
        
        double kilograms = toKilograms(fromUnit, value);
        
        
        return fromKilograms(toUnit, kilograms);
    }
    
    private static double toKilograms(String unit, double value) {
        switch (unit) {
            case "Kilogram": return value;
            case "Gram": return value * 0.001;
            case "Milligram": return value * 0.000001;
            case "Pound": return value * 0.453592;
            case "Ounce": return value * 0.0283495;
            case "Ton": return value * 1000;
            default: throw new IllegalArgumentException("Unknown weight unit: " + unit);
        }
    }
    
    private static double fromKilograms(String unit, double kilograms) {
        switch (unit) {
            case "Kilogram": return kilograms;
            case "Gram": return kilograms / 0.001;
            case "Milligram": return kilograms / 0.000001;
            case "Pound": return kilograms / 0.453592;
            case "Ounce": return kilograms / 0.0283495;
            case "Ton": return kilograms / 1000;
            default: throw new IllegalArgumentException("Unknown weight unit: " + unit);
        }
    }
    
    
    private static double convertTemperature(String fromUnit, String toUnit, double value) {
        
        double celsius = toCelsius(fromUnit, value);
        
        
        return fromCelsius(toUnit, celsius);
    }
    
    private static double toCelsius(String unit, double value) {
        switch (unit) {
            case "Celsius": return value;
            case "Fahrenheit": return (value - 32) * 5/9;
            case "Kelvin": return value - 273.15;
            default: throw new IllegalArgumentException("Unknown temperature unit: " + unit);
        }
    }
    
    private static double fromCelsius(String unit, double celsius) {
        switch (unit) {
            case "Celsius": return celsius;
            case "Fahrenheit": return (celsius * 9/5) + 32;
            case "Kelvin": return celsius + 273.15;
            default: throw new IllegalArgumentException("Unknown temperature unit: " + unit);
        }
    }
    
    
    private static double convertVolume(String fromUnit, String toUnit, double value) {
        
        double liters = toLiters(fromUnit, value);
        
        
        return fromLiters(toUnit, liters);
    }
    
    private static double toLiters(String unit, double value) {
        switch (unit) {
            case "Liter": return value;
            case "Milliliter": return value * 0.001;
            case "Gallon": return value * 3.78541;
            case "Cubic Meter": return value * 1000;
            default: throw new IllegalArgumentException("Unknown volume unit: " + unit);
        }
    }
    
    private static double fromLiters(String unit, double liters) {
        switch (unit) {
            case "Liter": return liters;
            case "Milliliter": return liters / 0.001;
            case "Gallon": return liters / 3.78541;
            case "Cubic Meter": return liters / 1000;
            default: throw new IllegalArgumentException("Unknown volume unit: " + unit);
        }
    }
    
    
    private static double convertArea(String fromUnit, String toUnit, double value) {
        
        double sqMeters = toSquareMeters(fromUnit, value);
        
        
        return fromSquareMeters(toUnit, sqMeters);
    }
    
    private static double toSquareMeters(String unit, double value) {
        switch (unit) {
            case "Square Meter": return value;
            case "Square Kilometer": return value * 1000000;
            case "Acre": return value * 4046.86;
            case "Hectare": return value * 10000;
            default: throw new IllegalArgumentException("Unknown area unit: " + unit);
        }
    }
    
    private static double fromSquareMeters(String unit, double sqMeters) {
        switch (unit) {
            case "Square Meter": return sqMeters;
            case "Square Kilometer": return sqMeters / 1000000;
            case "Acre": return sqMeters / 4046.86;
            case "Hectare": return sqMeters / 10000;
            default: throw new IllegalArgumentException("Unknown area unit: " + unit);
        }
    }
}
