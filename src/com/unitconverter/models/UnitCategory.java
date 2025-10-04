package com.unitconverter.models;

import java.util.Arrays;
import java.util.List;


public enum UnitCategory {
    LENGTH("Length", "Distance measurements", Arrays.asList(
        "Meter", "Kilometer", "Centimeter", "Millimeter", 
        "Mile", "Yard", "Foot", "Inch"
    )),
    
    WEIGHT("Weight", "Mass measurements", Arrays.asList(
        "Kilogram", "Gram", "Milligram", "Pound", "Ounce", "Ton"
    )),
    
    TEMPERATURE("Temperature", "Temperature scales", Arrays.asList(
        "Celsius", "Fahrenheit", "Kelvin"
    )),
    
    VOLUME("Volume", "Capacity measurements", Arrays.asList(
        "Liter", "Milliliter", "Gallon", "Cubic Meter"
    )),
    
    AREA("Area", "Surface area measurements", Arrays.asList(
        "Square Meter", "Square Kilometer", "Acre", "Hectare"
    ));
    
    private final String displayName;
    private final String description;
    private final List<String> supportedUnits;
    
    
    UnitCategory(String displayName, String description, List<String> supportedUnits) {
        this.displayName = displayName;
        this.description = description;
        this.supportedUnits = supportedUnits;
    }
    
    
    public String getDisplayName() {
        return displayName;
    }
    
    public String getDescription() {
        return description;
    }
    
    public List<String> getSupportedUnits() {
        return supportedUnits;
    }
    
    public String[] getSupportedUnitsArray() {
        return supportedUnits.toArray(new String[0]);
    }
    
    
    public boolean supportsUnit(String unit) {
        return supportedUnits.contains(unit);
    }
    
    
    public int getUnitCount() {
        return supportedUnits.size();
    }
    
    
    public static UnitCategory fromDisplayName(String displayName) {
        for (UnitCategory category : values()) {
            if (category.getDisplayName().equalsIgnoreCase(displayName)) {
                return category;
            }
        }
        return null;
    }
    
    
    public static String[] getAllDisplayNames() {
        UnitCategory[] categories = values();
        String[] names = new String[categories.length];
        for (int i = 0; i < categories.length; i++) {
            names[i] = categories[i].getDisplayName();
        }
        return names;
    }
    
    
    public static boolean isValidUnitForCategory(String categoryName, String unit) {
        UnitCategory category = fromDisplayName(categoryName);
        return category != null && category.supportsUnit(unit);
    }
    
    @Override
    public String toString() {
        return displayName + " - " + description;
    }
}
