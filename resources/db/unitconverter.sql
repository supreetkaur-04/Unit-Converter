# Unit Converter Configuration
# Database settings
database.url=jdbc:sqlite:unit_converter.db
database.driver=org.sqlite.JDBC
database.create_tables=true

# Application settings
app.name=Unit Converter
app.version=1.0.0
app.developer=Java OOP Student
app.organization=Computer Engineering Department

# UI Settings
ui.theme=system
ui.language=en
ui.decimal_places=6
ui.auto_save_history=true
ui.show_tooltips=true

# Conversion Settings
conversion.auto_update=true
conversion.precision=high
conversion.scientific_notation_threshold=1e6

# Currency Conversion Settings
currency.api.primary=https://api.exchangerate-api.com/v4/latest/
currency.api.fallback=https://api.frankfurter.app/latest
currency.cache.duration_minutes=30
currency.auto_refresh=true

# Network Settings
network.timeout.connect=5000
network.timeout.read=5000
network.retry.attempts=3

# Logging Settings
logging.enabled=true
logging.level=INFO
logging.file=unit_converter.log
logging.max_size_mb=10

# Feature Flags
feature.currency_conversion=true
feature.history_tracking=true
feature.background_processing=true
feature.network_operations=true

# Security Settings
security.validate_input=true
security.sanitize_output=true