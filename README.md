# Unit Converter Application

A powerful, user-friendly, and feature-rich **Unit Converter Application** built entirely in Java. This project demonstrates robust **Object-Oriented Programming (OOP)** principles and a modern **Swing-based Graphical User Interface (GUI)**. It allows for seamless conversions across multiple categories including Length, Weight, Temperature, Volume, and Area, with features like history tracking and a clean, intuitive design.

## ✨ Highlights

*   **Multi-Category Conversions:** Instantly convert between numerous units in Length, Weight, Temperature, Volume, and Area.
*   **Intuitive Swing GUI:** A clean and responsive interface built with Java Swing, ensuring a smooth user experience.
*   **Conversion History:** Automatically logs all your conversions with timestamps for easy reference.
*   **Data Persistence:** Uses an embedded **SQLite database** to save your conversion history between sessions.
*   **Built with Best Practices:** Showcases core Java OOP concepts, MVC-like architecture, and hand-coded UI components.
*   **Educational Focus:** Perfect for students and developers to understand building a complete desktop application in Java.

## 🚀 Features

- **Supported Conversion Categories:**
    - **Length:** Meter, Kilometer, Centimeter, Millimeter, Mile, Yard, Foot, Inch
    - **Weight:** Kilogram, Gram, Milligram, Pound, Ounce, Ton
    - **Temperature:** Celsius, Fahrenheit, Kelvin
    - **Volume:** Liter, Milliliter, Gallon, Cubic Meter
    - **Area:** Square Meter, Square Kilometer, Acre, Hectare
- **Smart GUI Features:**
    - Dynamic unit dropdowns that change based on the selected category.
    - Real-time input validation.
    - One-click conversion with the "Convert" button or by pressing Enter.
- **History Management:**
    - View a detailed table of past conversions.
    - **Remove individual entries** with a single click.
    - **Clear all history** with a confirmation dialog.
- **Robust Architecture:**
    - Clear separation of logic, data, and UI (MVC pattern).
    - Custom exception handling for better error management.
    - Utility classes for common operations and string formatting.

## 🛠️ Technologies Used

*   **Language:** Java
*   **GUI Toolkit:** Java Swing
*   **Database:** SQLite (via JDBC)
*   **Architecture:** Object-Oriented Programming (OOP), Model-View-Controller (MVC)
*   **Key Concepts:** Polymorphism, Encapsulation, Inheritance, Abstraction, Event Handling, Layout Management, JDBC, Exception Handling

## 📦 Installation & Setup

### Prerequisites

1.  **Java Development Kit (JDK):** Ensure you have JDK 8 or later installed.
    *   You can check by running `java -version` in your terminal/command prompt.
2.  **SQLite JDBC Driver (Optional for Full Features):** The application has a fallback mode if the driver is not found, but for full history tracking, include the SQLite JDBC JAR file in your project's classpath.
    *   Download it from [https://github.com/xerial/sqlite-jdbc](https://github.com/xerial/sqlite-jdbc)
    *   Place the `sqlite-jdbc-<version>.jar` file in a `lib` folder within your project directory.

### Steps to Run

### Steps to Run from Command Line

1.  **Navigate to the `src` directory:**
    ```powershell
    cd src
    ```

2.  **Compile the Java Source Files:**
    Use the `-cp` (classpath) option to tell the compiler about your source directory and any libraries. The command you've been using is correct:
    ```powershell
    javac -cp ".;..\lib\*" com\unitconverter\*.java com\unitconverter\models\*.java com\unitconverter\exceptions\*.java com\unitconverter\threads\*.java com\unitconverter\network\*.java com\unitconverter\utils\*.java
    ```
    *   `-cp ".;..\lib\*"`: Sets the classpath to include the current directory (`.`) and all JAR files in the `../lib` folder.
    *   The compiler will generate `.class` files in the same directory as your source files by default.

3.  **Run the Application:**
    Use the `java` command with the same classpath argument, specifying the main class:
    ```powershell
    java -cp ".;..\lib\*" com.unitconverter.Main
    ```
    *   The `-cp ".;..\lib\*"` ensures the Java runtime can find both your compiled classes and the database driver.


## 💻 Usage

1.  **Launch the application.** The main window will appear with dropdown menus and input fields.
2.  **Select a Category:** Choose from Length, Weight, Temperature, etc.
3.  **Choose Units:** Select the "From" and "To" units from the dynamically updated dropdowns.
4.  **Enter Value:** Type the numerical value you want to convert in the "Input Value" field.
5.  **Convert:** Click the "Convert" button or press Enter. The result will appear in the "Result" field.
6.  **View History:** Click the "History" button to see a log of all your past conversions. You can remove individual entries or clear the entire history.
7.  **Clear:** Use the "Clear" button to reset the input and result fields.

## 🏗️ Project Structure

This project is organized into logical packages for maintainability and clarity .

```
UnitConverterProject/
│
├── src/
│   └── com/unitconverter/
│       ├── Main.java             # Entry point (main method)
│       ├── ConverterApp.java     # JFrame GUI application
│       ├── ConversionLogic.java  # Conversion formulas & methods
│       ├── ConverterUtils.java   # Helper functions (validation, error handling)
│       ├── DatabaseManager.java  # JDBC connectivity (logs user conversions)
│       ├── HistoryPanel.java     # Shows previous conversions using JTable
│       ├── AboutDialog.java      # Simple About dialog (Project info)
│       ├── exceptions/           # Custom exception package
│       │   └── InvalidUnitException.java
│       ├── models/               # Classes for OOP design
│       │   ├── UnitCategory.java     # Enum or class for categories (Length, Mass, etc.)
│       │   ├── ConversionRequest.java# Stores input unit, output unit, value
│       │   └── ConversionResult.java # Stores result
│       ├── threads/
│       │   └── BackgroundTask.java   # Example: running conversions in a thread
│       ├── network/
│       │   └── CurrencyConverter.java # Example: fetch currency conversion via URL
│       └── utils/
│           └── StringUtils.java      # Extra string handling (upper/lower, format)
│
├── resources/
│   └── db/                       
│       └── unitconverter.sql     # SQL file for conversion history table
│
├── lib/                          # External jars (if needed)
│
└── README.md                     # Project Documentation
```

### OOP Concepts Demonstrated 

*   **Encapsulation:** Data (like in `ConversionRequest`) is private and accessed via public getters/setters.
*   **Inheritance:** `ConverterApp` extends `JFrame`; custom exceptions extend `Exception`.
*   **Polymorphism:** Method overloading in `ConversionLogic`; method overriding in listeners.
*   **Abstraction:** The `ConversionLogic` class provides a simple `convert` method, hiding the complex formulas behind it.

## 🔧 Code Overview

The application follows Swing best practices, including performing UI updates on the Event Dispatch Thread (EDT) .

**Main Entry Point (`Main.java`):**
```java
public static void main(String[] args) {
    javax.swing.SwingUtilities.invokeLater(new Runnable() {
        public void run() {
            new ConverterApp().setVisible(true);
        }
    });
}
```

**Key Design Decisions:**
*   **Separated Logic:** The `ConversionLogic` class is purely responsible for calculations, making it easy to test and maintain.
*   **Database Fallback:** The application gracefully handles missing database drivers by using a fallback manager.
*   **Listener-based Events:** Anonymous inner classes are used for event listeners to keep the code clean and organized .

## 🤝 Contributing

Contributions are welcome! If you have ideas for new units, categories, or UI improvements, please feel free to contribute.

1.  Fork the Project.
2.  Create your Feature Branch (`git checkout -b feature/AmazingFeature`).
3.  Commit your Changes (`git commit -m 'Add some AmazingFeature'`).
4.  Push to the Branch (`git push origin feature/AmazingFeature`).
5.  Open a Pull Request.

Please ensure your code adheres to the existing style and includes appropriate comments.

**Note:** During compilation, you may see a deprecation warning from `CurrencyConverter.java`. This is expected and doesn't affect the application's functionality. The warning relates to internal Java networking APIs that are still fully functional.

## 📄 License

This project is licensed under the MIT License - see the LICENSE file for details.

## 👨‍💻 Author

**Your Name**
*   GitHub: [@supreetkaur-04]((https://github.com/supreetkaur-04/Unit-Converter))

## 🙏 Acknowledgments

*   Inspiration from various online tutorials and Java documentation.
*   Thanks to the Java and SQLite communities for excellent tools and libraries.

---
**Happy Converting!** If you find this project useful, please give it a ⭐!
