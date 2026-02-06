import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Logger {
    private final String filename;
    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    public Logger() {
        this("app.log");
    }

    public Logger(String filename) {
        this.filename = filename;
    }

    public void log(String message, String level) {
        String timestamp = LocalDateTime.now().format(formatter);
        String entry = "[" + timestamp + "] [" + level + "] " + message + System.lineSeparator();

        try (FileWriter fw = new FileWriter(filename, true)) {
            fw.write(entry);
        } catch (IOException e) {
            throw new RuntimeException("Failed to write log entry", e);
        }

        System.out.print(entry);
    }

    public void info(String message) {
        log(message, "INFO");
    }

    public void error(String message) {
        log(message, "ERROR");
    }

    public static void main(String[] args) {
        Logger logger = new Logger();
        logger.info("Application started");
        logger.error("An error occurred");
    }
}
