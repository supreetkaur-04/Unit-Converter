# Unit Converter - Java OOP Project

A comprehensive unit conversion application built with Java Swing, demonstrating Object-Oriented Programming principles and modern Java features.

## 📋 Project Overview

This Unit Converter application provides accurate conversions across multiple measurement categories including length, weight, temperature, volume, area, and currency. It features a user-friendly GUI, conversion history tracking, and real-time currency exchange rates.

## 🚀 Features

### Core Conversion Categories
- **Length**: Meter, Kilometer, Centimeter, Millimeter, Mile, Yard, Foot, Inch
- **Weight**: Kilogram, Gram, Milligram, Pound, Ounce, Ton
- **Temperature**: Celsius, Fahrenheit, Kelvin
- **Volume**: Liter, Milliliter, Gallon, Cubic Meter
- **Area**: Square Meter, Square Kilometer, Acre, Hectare
- **Currency**: Real-time exchange rates for 10+ currencies

### Advanced Features
- 📊 Conversion history with database persistence
- 🔄 Real-time currency conversion using web APIs
- 🎯 High-precision mathematical calculations
- 💾 SQLite database for data storage
- 🎨 Modern Swing-based GUI
- ⚡ Multithreaded background processing
- 🌐 Network connectivity for live data
- 🔍 Input validation and error handling

## 🛠️ Technologies Used

- **Java 11+** - Core programming language
- **Swing** - GUI framework
- **SQLite** - Database management
- **JDBC** - Database connectivity
- **Multithreading** - Background processing
- **Networking** - Web API integration
- **OOP Principles** - Inheritance, Polymorphism, Encapsulation, Abstraction

## 📁 Project Structure

UnitConverterProject/
├── src/
│ ├── com/unitconverter/
│ │ ├── Main.java # Application entry point
│ │ ├── ConverterApp.java # Main GUI application
│ │ ├── ConversionLogic.java # Conversion algorithms
│ │ ├── ConverterUtils.java # Utility functions
│ │ ├── DatabaseManager.java # Database operations
│ │ ├── HistoryPanel.java # History display
│ │ ├── AboutDialog.java # About information
│ │ └── exceptions/ # Custom exceptions
│ │ └── InvalidUnitException.java
│ ├── com/unitconverter/models/ # Data models
│ │ ├── UnitCategory.java # Category enum
│ │ ├── ConversionRequest.java # Request object
│ │ └── ConversionResult.java # Result object
│ ├── com/unitconverter/threads/ # Multithreading
│ │ └── BackgroundTask.java # Background processing
│ ├── com/unitconverter/network/ # Networking
│ │ └── CurrencyConverter.java # Currency API
│ └── com/unitconverter/utils/ # Utilities
│ └── StringUtils.java # String operations
├── resources/
│ ├── db/
│ │ └── unitconverter.sql # Database schema
│ └── config.properties # Application configuration
└── README.md

## 🎯 Java Concepts Demonstrated

### Object-Oriented Programming
- Classes and Objects
- Inheritance and Polymorphism
- Encapsulation
- Abstraction
- Interfaces and Abstract Classes

### Advanced Java Features
- Exception Handling
- Collections Framework
- Multithreading with SwingWorker
- JDBC Database Connectivity
- Networking with URLConnection
- String Manipulation
- Event Handling
- Layout Management

### Design Patterns
- MVC (Model-View-Controller)
- Singleton Pattern
- Builder Pattern
- Factory Pattern
- Observer Pattern

## 🔧 Installation & Setup

### Prerequisites
- Java JDK 11 or higher
- SQLite JDBC driver
- Internet connection (for currency conversion)

### Steps to Run
1. **Clone or download** the project files
2. **Compile the project**:
   ```bash
   javac -cp ".:lib/*" com/unitconverter/*.java com/unitconverter/**/*.java