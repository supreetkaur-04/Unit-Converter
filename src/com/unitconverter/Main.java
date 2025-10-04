package com.unitconverter;

public class Main {
    public static void main(String[] args) {
        System.out.println("=== Unit Converter Application Starting ===");
        System.out.println("Developed using Java OOP Concepts");
        
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new ConverterApp().setVisible(true);
            }
        });
    }
}
