import java.io.*;
import java.util.List;
import java.util.logging.FileHandler;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

public class Main {
    // Create a logger
    private static final Logger logger = Logger.getLogger(Main.class.getName());

    private static final int MAX_CHAR_LIMIT = 1000; // Set your desired character limit here

    public static void main(String[] args) {

        // Set up logging
        try {
            // A `FileHandler` is added to the logger to write logs to a file named `error_log.log`.
            FileHandler fileHandler = new FileHandler("error_log.log", true);

            fileHandler.setFormatter(new SimpleFormatter());

            logger.addHandler(fileHandler);

            // Disable logging to console
            logger.setUseParentHandlers(false);

        } catch (IOException e) {

            e.printStackTrace();

            // Exit if logging setup fails
            return;
        }

        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(System.out));

        try {
            // Create instances of the required classes
            MarkdownErrorHandler errorHandler = new MarkdownErrorHandler();
            MarkdownParser parser = new MarkdownParser(errorHandler); // Pass errorHandler to the parser
            MarkdownConverter converter = new MarkdownConverter();

            StringBuilder markdownText = new StringBuilder();
            String line;
            int charCount = 0;

            System.out.println("Enter markdown text (end input with Ctrl+D on a new line):");

            while ((line = reader.readLine()) != null) {

                // Break if the input exceeds the maximum character limit
                if (charCount + line.length() > MAX_CHAR_LIMIT) {

                    System.out.println("Character limit exceeded. Please submit your input.");
                    break;
                }

                markdownText.append(line).append("\n");
                charCount += line.length();
            }

            // Process the input with the parser
            List<MarkdownElement> parsedMarkdown = parser.parse(markdownText.toString());

            // Convert the parsed markdown to HTML
            String htmlOutput = converter.convertToHtml(parsedMarkdown);

            // Handle any errors
            if (errorHandler.hasErrors()) {

                logger.severe("Errors found:\n" + errorHandler.getErrors());

            } else {
                // Output the converted HTML
                writer.write(htmlOutput);
            }

            writer.flush();

        } catch (IOException e) {

            logger.severe("IOException encountered: " + e.getMessage());
            e.printStackTrace();

        } finally {
            try {

                reader.close();
                writer.close();

            } catch (IOException e) {

                logger.severe("IOException encountered while closing streams: " + e.getMessage());
                e.printStackTrace();
            }
        }
    }
}