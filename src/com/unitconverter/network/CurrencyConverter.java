package com.unitconverter.network;

import javax.swing.*;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

public class CurrencyConverter {
    private static final String API_BASE_URL = "https://api.exchangerate-api.com/v4/latest/";
    private static final String FALLBACK_API_URL = "https://api.frankfurter.app/latest?from=";
    
    private static final Map<String, String> CURRENCY_SYMBOLS = new HashMap<>();
    private static final Map<String, Double> CACHE_RATES = new HashMap<>();
    private static long CACHE_TIMESTAMP = 0;
    private static final long CACHE_DURATION = 30 * 60 * 1000; // 30 minutes
    
    static {
        CURRENCY_SYMBOLS.put("USD", "$");
        CURRENCY_SYMBOLS.put("EUR", "€");
        CURRENCY_SYMBOLS.put("GBP", "£");
        CURRENCY_SYMBOLS.put("JPY", "¥");
        CURRENCY_SYMBOLS.put("INR", "₹");
        CURRENCY_SYMBOLS.put("CAD", "C$");
        CURRENCY_SYMBOLS.put("AUD", "A$");
        CURRENCY_SYMBOLS.put("CNY", "¥");
        CURRENCY_SYMBOLS.put("KRW", "₩");
        CURRENCY_SYMBOLS.put("RUB", "₽");
    }
    
    public static final String[] SUPPORTED_CURRENCIES = {
        "USD", "EUR", "GBP", "JPY", "INR", "CAD", "AUD", "CNY", "KRW", "RUB"
    };
    
    /**
     * Converts currency using online exchange rates
     * @param fromCurrency Source currency code
     * @param toCurrency Target currency code
     * @param amount Amount to convert
     * @return Converted amount
     * @throws Exception if conversion fails
     */
    public static double convertCurrency(String fromCurrency, String toCurrency, double amount) throws Exception {
        if (fromCurrency.equals(toCurrency)) {
            return amount;
        }
        
        String cacheKey = fromCurrency + "_" + toCurrency;
        if (isCacheValid() && CACHE_RATES.containsKey(cacheKey)) {
            return amount * CACHE_RATES.get(cacheKey);
        }
        
        double rate = fetchExchangeRate(fromCurrency, toCurrency, API_BASE_URL);
        
        if (rate <= 0) {
            rate = fetchExchangeRate(fromCurrency, toCurrency, FALLBACK_API_URL);
        }
        
        if (rate <= 0) {
            throw new Exception("Unable to fetch exchange rates. Please check your internet connection.");
        }
        
        CACHE_RATES.put(cacheKey, rate);
        CACHE_TIMESTAMP = System.currentTimeMillis();
        
        return amount * rate;
    }
    
    /**
     * Fetches exchange rate from API
     * @param fromCurrency Source currency
     * @param toCurrency Target currency
     * @param apiUrl API endpoint
     * @return Exchange rate
     */
    private static double fetchExchangeRate(String fromCurrency, String toCurrency, String apiUrl) {
        try {
            String urlString;
            if (apiUrl.contains("frankfurter")) {
                urlString = apiUrl + fromCurrency + "&to=" + toCurrency;
            } else {
                urlString = apiUrl + fromCurrency;
            }
            
            URL url = new URL(urlString);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setConnectTimeout(5000);
            connection.setReadTimeout(5000);
            connection.setRequestProperty("User-Agent", "UnitConverter/1.0");
            
            int responseCode = connection.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {
                BufferedReader reader = new BufferedReader(
                    new InputStreamReader(connection.getInputStream())
                );
                
                StringBuilder response = new StringBuilder();
                String line;
                while ((line = reader.readLine()) != null) {
                    response.append(line);
                }
                reader.close();
                
                return parseExchangeRate(response.toString(), fromCurrency, toCurrency, apiUrl);
            }
        } catch (Exception e) {
            System.err.println("Error fetching from " + apiUrl + ": " + e.getMessage());
        }
        
        return 0;
    }
    
    /**
     * Parses exchange rate from JSON response
     * @param jsonResponse JSON response from API
     * @param fromCurrency Source currency
     * @param toCurrency Target currency
     * @param apiUrl API URL used (for determining response format)
     * @return Exchange rate
     */
    private static double parseExchangeRate(String jsonResponse, String fromCurrency, String toCurrency, String apiUrl) {
        try {
            if (apiUrl.contains("frankfurter")) {
                int ratesIndex = jsonResponse.indexOf("\"rates\"");
                if (ratesIndex != -1) {
                    int currencyIndex = jsonResponse.indexOf("\"" + toCurrency + "\"", ratesIndex);
                    if (currencyIndex != -1) {
                        int colonIndex = jsonResponse.indexOf(":", currencyIndex);
                        int commaIndex = jsonResponse.indexOf(",", colonIndex);
                        int endIndex = jsonResponse.indexOf("}", colonIndex);
                        if (commaIndex != -1 && commaIndex < endIndex) {
                            endIndex = commaIndex;
                        }
                        String rateStr = jsonResponse.substring(colonIndex + 1, endIndex).trim();
                        return Double.parseDouble(rateStr);
                    }
                }
            } else {
                int ratesIndex = jsonResponse.indexOf("\"rates\"");
                if (ratesIndex != -1) {
                    int currencyIndex = jsonResponse.indexOf("\"" + toCurrency + "\"", ratesIndex);
                    if (currencyIndex != -1) {
                        int colonIndex = jsonResponse.indexOf(":", currencyIndex);
                        int commaIndex = jsonResponse.indexOf(",", colonIndex);
                        int endIndex = jsonResponse.indexOf("}", colonIndex);
                        if (commaIndex != -1 && commaIndex < endIndex) {
                            endIndex = commaIndex;
                        }
                        String rateStr = jsonResponse.substring(colonIndex + 1, endIndex).trim();
                        return Double.parseDouble(rateStr);
                    }
                }
            }
        } catch (Exception e) {
            System.err.println("Error parsing JSON response: " + e.getMessage());
        }
        
        return 0;
    }
    
    /**
     * Checks if cache is still valid
     * @return true if cache is valid
     */
    private static boolean isCacheValid() {
        return System.currentTimeMillis() - CACHE_TIMESTAMP < CACHE_DURATION;
    }
    
    /**
     * Gets currency symbol
     * @param currencyCode Currency code
     * @return Currency symbol or code if symbol not found
     */
    public static String getCurrencySymbol(String currencyCode) {
        return CURRENCY_SYMBOLS.getOrDefault(currencyCode, currencyCode);
    }
    
    /**
     * Gets full currency name
     * @param currencyCode Currency code
     * @return Full currency name
     */
    public static String getCurrencyName(String currencyCode) {
        Map<String, String> currencyNames = new HashMap<>();
        currencyNames.put("USD", "US Dollar");
        currencyNames.put("EUR", "Euro");
        currencyNames.put("GBP", "British Pound");
        currencyNames.put("JPY", "Japanese Yen");
        currencyNames.put("INR", "Indian Rupee");
        currencyNames.put("CAD", "Canadian Dollar");
        currencyNames.put("AUD", "Australian Dollar");
        currencyNames.put("CNY", "Chinese Yuan");
        currencyNames.put("KRW", "South Korean Won");
        currencyNames.put("RUB", "Russian Ruble");
        
        return currencyNames.getOrDefault(currencyCode, currencyCode);
    }
    
    /**
     * Tests internet connectivity
     * @return true if internet is available
     */
    public static boolean isInternetAvailable() {
        try {
            URL url = new URL("https://www.google.com");
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setConnectTimeout(3000);
            connection.setReadTimeout(3000);
            connection.setRequestMethod("HEAD");
            int responseCode = connection.getResponseCode();
            return (responseCode == HttpURLConnection.HTTP_OK);
        } catch (Exception e) {
            return false;
        }
    }
    
    /**
     * Shows network status dialog
     * @param parentComponent Parent component
     */
    public static void showNetworkStatus(java.awt.Component parentComponent) {
        boolean online = isInternetAvailable();
        String message = online ? 
            "Internet connection available. Currency conversion will use live exchange rates." :
            "No internet connection. Currency conversion will use cached rates (if available).";
        
        int messageType = online ? JOptionPane.INFORMATION_MESSAGE : JOptionPane.WARNING_MESSAGE;
        
        JOptionPane.showMessageDialog(
            parentComponent,
            message,
            "Network Status",
            messageType
        );
    }
    
    public static void clearCache() {
        CACHE_RATES.clear();
        CACHE_TIMESTAMP = 0;
    }
}