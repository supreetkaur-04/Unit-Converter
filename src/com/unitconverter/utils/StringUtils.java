package com.unitconverter.utils;


public class StringUtils {
    
    
    private StringUtils() {}
    
    
    public static boolean isNullOrEmpty(String str) {
        return str == null || str.trim().isEmpty();
    }
    
    
    public static boolean isBlank(String str) {
        return str == null || str.trim().isEmpty();
    }
    
    
    public static String capitalizeWords(String str) {
        if (isNullOrEmpty(str)) {
            return str;
        }
        
        String[] words = str.split("\\s+");
        StringBuilder result = new StringBuilder();
        
        for (String word : words) {
            if (!word.isEmpty()) {
                result.append(Character.toUpperCase(word.charAt(0)))
                      .append(word.substring(1).toLowerCase())
                      .append(" ");
            }
        }
        
        return result.toString().trim();
    }
    
    
    public static String toTitleCase(String str) {
        if (isNullOrEmpty(str)) {
            return str;
        }
        
        StringBuilder result = new StringBuilder();
        boolean nextTitleCase = true;
        
        for (char c : str.toCharArray()) {
            if (Character.isSpaceChar(c)) {
                nextTitleCase = true;
                result.append(c);
            } else if (nextTitleCase) {
                result.append(Character.toTitleCase(c));
                nextTitleCase = false;
            } else {
                result.append(Character.toLowerCase(c));
            }
        }
        
        return result.toString();
    }
    
    
    public static String removeNonAlphanumeric(String str) {
        if (isNullOrEmpty(str)) {
            return str;
        }
        return str.replaceAll("[^a-zA-Z0-9]", "");
    }
    
    
    public static String truncate(String str, int maxLength) {
        if (isNullOrEmpty(str) || str.length() <= maxLength) {
            return str;
        }
        
        if (maxLength <= 3) {
            return str.substring(0, maxLength);
        }
        
        return str.substring(0, maxLength - 3) + "...";
    }
    
    
    public static String reverse(String str) {
        if (isNullOrEmpty(str)) {
            return str;
        }
        return new StringBuilder(str).reverse().toString();
    }
    
    
    public static int wordCount(String str) {
        if (isNullOrEmpty(str)) {
            return 0;
        }
        String[] words = str.trim().split("\\s+");
        return words.length;
    }
    
    
    public static boolean isNumeric(String str) {
        if (isNullOrEmpty(str)) {
            return false;
        }
        return str.matches("\\d+");
    }
    
    
    public static boolean isDecimal(String str) {
        if (isNullOrEmpty(str)) {
            return false;
        }
        return str.matches("-?\\d+(\\.\\d+)?");
    }
    
    
    public static String padString(String str, int length, char padChar, boolean leftPad) {
        if (str == null) {
            str = "";
        }
        
        if (str.length() >= length) {
            return str;
        }
        
        StringBuilder padded = new StringBuilder();
        int padding = length - str.length();
        
        if (leftPad) {
            for (int i = 0; i < padding; i++) {
                padded.append(padChar);
            }
            padded.append(str);
        } else {
            padded.append(str);
            for (int i = 0; i < padding; i++) {
                padded.append(padChar);
            }
        }
        
        return padded.toString();
    }
    
    
    public static String toSafeFileName(String str) {
        if (isNullOrEmpty(str)) {
            return "untitled";
        }
        
        
        String safeName = str.replaceAll("[^a-zA-Z0-9.-]", "_");
        safeName = safeName.replaceAll("_{2,}", "_"); 
        safeName = safeName.replaceAll("^_+|_+$", ""); 
        
        if (safeName.isEmpty()) {
            return "untitled";
        }
        
        return safeName;
    }
    
    
    public static String formatNumber(double number) {
        java.text.DecimalFormat formatter = new java.text.DecimalFormat("#,###.##");
        return formatter.format(number);
    }
    
    
    public static String join(String delimiter, String... strings) {
        if (strings == null || strings.length == 0) {
            return "";
        }
        
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < strings.length; i++) {
            if (strings[i] != null) {
                result.append(strings[i]);
                if (i < strings.length - 1) {
                    result.append(delimiter);
                }
            }
        }
        
        return result.toString();
    }
    
    
    public static String splitCamelCase(String str) {
        if (isNullOrEmpty(str)) {
            return str;
        }
        
        return str.replaceAll("([a-z])([A-Z])", "$1 $2");
    }
    
    
    public static String createProgressBar(double current, double total, int width) {
        if (total <= 0) return "";
        
        double percentage = Math.min(100, Math.max(0, (current / total) * 100));
        int filledWidth = (int) ((percentage / 100) * width);
        
        StringBuilder bar = new StringBuilder();
        bar.append("[");
        
        for (int i = 0; i < width; i++) {
            if (i < filledWidth) {
                bar.append("=");
            } else if (i == filledWidth) {
                bar.append(">");
            } else {
                bar.append(" ");
            }
        }
        
        bar.append("] ");
        bar.append(String.format("%.1f%%", percentage));
        
        return bar.toString();
    }
}
