package com.unitconverter.models;

import java.util.Objects;


public class ConversionRequest {
    private final double inputValue;
    private final String fromUnit;
    private final String toUnit;
    private final String category;
    private final long timestamp;
    
    
    public ConversionRequest(double inputValue, String fromUnit, String toUnit, String category) {
        this.inputValue = inputValue;
        this.fromUnit = fromUnit;
        this.toUnit = toUnit;
        this.category = category;
        this.timestamp = System.currentTimeMillis();
        validate();
    }
    
    
    private void validate() {
        if (Double.isNaN(inputValue) || Double.isInfinite(inputValue)) {
            throw new IllegalArgumentException("Input value must be a finite number");
        }
        
        if (fromUnit == null || fromUnit.trim().isEmpty()) {
            throw new IllegalArgumentException("Source unit cannot be null or empty");
        }
        
        if (toUnit == null || toUnit.trim().isEmpty()) {
            throw new IllegalArgumentException("Target unit cannot be null or empty");
        }
        
        if (category == null || category.trim().isEmpty()) {
            throw new IllegalArgumentException("Category cannot be null or empty");
        }
        
        
        if (inputValue < 0 && !"Temperature".equals(category)) {
            throw new IllegalArgumentException(
                "Negative values are not allowed for " + category + " conversions"
            );
        }
    }
    
    
    public double getInputValue() {
        return inputValue;
    }
    
    public String getFromUnit() {
        return fromUnit;
    }
    
    public String getToUnit() {
        return toUnit;
    }
    
    public String getCategory() {
        return category;
    }
    
    public long getTimestamp() {
        return timestamp;
    }
    
    
    public boolean isSelfConversion() {
        return fromUnit.equals(toUnit);
    }
    
    
    public String getDescription() {
        return String.format("Convert %.2f %s to %s (%s)", 
            inputValue, fromUnit, toUnit, category);
    }
    
    
    public String getKey() {
        return String.format("%s:%s:%s:%.6f", category, fromUnit, toUnit, inputValue);
    }
    
    
    
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        
        ConversionRequest that = (ConversionRequest) obj;
        return Double.compare(that.inputValue, inputValue) == 0 &&
               Objects.equals(fromUnit, that.fromUnit) &&
               Objects.equals(toUnit, that.toUnit) &&
               Objects.equals(category, that.category);
    }
    
    @Override
    public int hashCode() {
        return Objects.hash(inputValue, fromUnit, toUnit, category);
    }
    
    @Override
    public String toString() {
        return String.format(
            "ConversionRequest{value=%.2f, from='%s', to='%s', category='%s', timestamp=%d}",
            inputValue, fromUnit, toUnit, category, timestamp
        );
    }
    
    
    public static class Builder {
        private double inputValue;
        private String fromUnit;
        private String toUnit;
        private String category;
        
        public Builder setInputValue(double inputValue) {
            this.inputValue = inputValue;
            return this;
        }
        
        public Builder setFromUnit(String fromUnit) {
            this.fromUnit = fromUnit;
            return this;
        }
        
        public Builder setToUnit(String toUnit) {
            this.toUnit = toUnit;
            return this;
        }
        
        public Builder setCategory(String category) {
            this.category = category;
            return this;
        }
        
        public ConversionRequest build() {
            return new ConversionRequest(inputValue, fromUnit, toUnit, category);
        }
    }
}
