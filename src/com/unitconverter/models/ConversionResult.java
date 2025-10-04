package com.unitconverter.models;

import java.text.DecimalFormat;
import java.util.Objects;


public class ConversionResult {
    private final ConversionRequest request;
    private final double resultValue;
    private final long calculationTime;
    private final boolean success;
    private final String errorMessage;
    
    
    private static final ThreadLocal<DecimalFormat> DECIMAL_FORMAT = 
        ThreadLocal.withInitial(() -> new DecimalFormat("#.######"));
    
    
    public ConversionResult(ConversionRequest request, double resultValue, long calculationTime) {
        this.request = request;
        this.resultValue = resultValue;
        this.calculationTime = calculationTime;
        this.success = true;
        this.errorMessage = null;
        validateResult();
    }
    
    
    public ConversionResult(ConversionRequest request, String errorMessage) {
        this.request = request;
        this.resultValue = 0;
        this.calculationTime = 0;
        this.success = false;
        this.errorMessage = errorMessage;
    }
    
    
    private void validateResult() {
        if (Double.isNaN(resultValue)) {
            throw new IllegalArgumentException("Result cannot be NaN");
        }
        if (Double.isInfinite(resultValue)) {
            throw new IllegalArgumentException("Result cannot be infinite");
        }
    }
    
    
    public ConversionRequest getRequest() {
        return request;
    }
    
    public double getResultValue() {
        return resultValue;
    }
    
    public long getCalculationTime() {
        return calculationTime;
    }
    
    public boolean isSuccess() {
        return success;
    }
    
    public String getErrorMessage() {
        return errorMessage;
    }
    
    
    public String getFormattedResult() {
        if (!success) {
            return "Conversion failed";
        }
        return DECIMAL_FORMAT.get().format(resultValue);
    }
    
    
    public String getFormattedInput() {
        return DECIMAL_FORMAT.get().format(request.getInputValue());
    }
    
    
    public String getDetailedDescription() {
        if (!success) {
            return String.format("Failed to convert %s: %s", 
                request.getDescription(), errorMessage);
        }
        
        return String.format("Converted %s %s to %s %s (took %d ms)",
            getFormattedInput(), request.getFromUnit(),
            getFormattedResult(), request.getToUnit(),
            calculationTime);
    }
    
    
    public String getSummary() {
        if (!success) {
            return String.format("ERROR: %s", errorMessage);
        }
        
        return String.format("%s %s = %s %s",
            getFormattedInput(), request.getFromUnit(),
            getFormattedResult(), request.getToUnit());
    }
    
    
    public boolean isSelfConversion() {
        return success && request.isSelfConversion();
    }
    
    
    public double getEfficiency() {
        if (!success || calculationTime == 0) {
            return 0;
        }
        
        return 1000.0 / calculationTime;
    }
    
    
    
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        
        ConversionResult that = (ConversionResult) obj;
        return Double.compare(that.resultValue, resultValue) == 0 &&
               calculationTime == that.calculationTime &&
               success == that.success &&
               Objects.equals(request, that.request) &&
               Objects.equals(errorMessage, that.errorMessage);
    }
    
    @Override
    public int hashCode() {
        return Objects.hash(request, resultValue, calculationTime, success, errorMessage);
    }
    
    @Override
    public String toString() {
        if (!success) {
            return String.format(
                "ConversionResult{success=false, error='%s', request=%s}",
                errorMessage, request
            );
        }
        
        return String.format(
            "ConversionResult{success=true, result=%.6f, time=%dms, request=%s}",
            resultValue, calculationTime, request
        );
    }
    
    
    public static class Builder {
        private ConversionRequest request;
        private double resultValue;
        private long calculationTime = -1;
        
        public Builder setRequest(ConversionRequest request) {
            this.request = request;
            return this;
        }
        
        public Builder setResultValue(double resultValue) {
            this.resultValue = resultValue;
            return this;
        }
        
        public Builder setCalculationTime(long calculationTime) {
            this.calculationTime = calculationTime;
            return this;
        }
        
        public ConversionResult build() {
            if (calculationTime < 0) {
                calculationTime = 0; 
            }
            return new ConversionResult(request, resultValue, calculationTime);
        }
    }
    
    
    public static ConversionResult error(ConversionRequest request, String errorMessage) {
        return new ConversionResult(request, errorMessage);
    }
}
