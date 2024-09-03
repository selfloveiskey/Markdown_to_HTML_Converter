import java.io.*;
import java.util.List;

public class Main {

    public static void main(String[] args) {

        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(System.out));

        try {
            // Create instances of the required classes
            MarkdownErrorHandler errorHandler = new MarkdownErrorHandler();
            MarkdownParser parser = new MarkdownParser(errorHandler);
            MarkdownConverter converter = new MarkdownConverter();

            // Prompt user for markdown input
            System.out.print("Enter markdown text (end input with an empty line):");

            StringBuilder markdownText = new StringBuilder();
            String line;

            // Read the user input
            while (!(line = reader.readLine()).isEmpty()) {

                markdownText.append(line).append("\n");
            }

            // Process the input with the parser
            List<MarkdownElement> parsedMarkdown = parser.parse(markdownText.toString());

            // Convert the parsed markdown to HTML
            String htmlOutput = converter.convertToHtml(parsedMarkdown);

            // Handle any errors
            if (errorHandler.hasErrors()) {

                writer.write("Errors found:\n");
                writer.write(errorHandler.getErrors());

            } else {
                // Output the converted HTML
                writer.write(htmlOutput);
            }

            writer.flush();

        } catch (IOException e) {

            e.printStackTrace();
        }
        finally {

            try {

                reader.close();
                writer.close();

            } catch (IOException e) {

                e.printStackTrace();
            }
        }
    }
}