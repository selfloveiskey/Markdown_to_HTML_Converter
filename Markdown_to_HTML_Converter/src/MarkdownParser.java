import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/*
 * Responsibilities:
 *  (a) Recognize and tokenize Markdown syntax (e.g., headers, bold, italic, lists).
 *  (b) Validate the Markdown syntax and ensure it's well-formed.
 *  (c) Identify different markdown elements and convert them into structured data (MarkdownElement).
 */
public class MarkdownParser {

    private final MarkdownErrorHandler errorHandler;

    public MarkdownParser(MarkdownErrorHandler errorHandler) {
        this.errorHandler = errorHandler;
    }

    public List<MarkdownElement> parse(String markdownText) {

        List<MarkdownElement> elements = new ArrayList<>();

        String[] lines = markdownText.split("\n");

        for (String line : lines) {

            line = line.trim();

            if (line.isEmpty()) continue;

            if (isValidHeader(line)) {

                elements.add(new MarkdownElement("header", line));

            } else if (isValidBold(line)) {

                elements.add(new MarkdownElement("bold", line));

            } else if (isValidItalic(line)) {

                elements.add(new MarkdownElement("italic", line));

            } else if (isValidLink(line)) {

                elements.add(new MarkdownElement("link", line));

            } else if (containsMixedElements(line)) {

                parseMixedElements(line, elements);

            } else {

                elements.add(new MarkdownElement("paragraph", line));
            }
        }

        return elements;
    }

    private boolean isValidHeader(String line) {

        Pattern pattern = Pattern.compile("^(#{1,6}) (.*)$");

        Matcher matcher = pattern.matcher(line);

        if (matcher.matches()) {

            return true;

        } else {

            errorHandler.addError("Invalid header syntax: " + line);

            return false;
        }
    }

    private boolean isValidBold(String line) {

        if (line.startsWith("**") && line.endsWith("**")) {

            //return countOccurrences(line, "**") == 2;
            return true;

        } else {

            errorHandler.addError("Invalid bold syntax: " + line);

            return false;
        }
    }

    private boolean isValidItalic(String line) {

        if (line.startsWith("_") && line.endsWith("_")) {

            //return countOccurrences(line, "_") == 2;
            return true;

        } else {

            errorHandler.addError("Invalid italic syntax: " + line);

            return false;
        }
    }

    private boolean isValidLink(String line) {

        Pattern pattern = Pattern.compile("\\[.*?\\]\\(.*?\\)");

        Matcher matcher = pattern.matcher(line);

        if (matcher.matches()) {

            return true;
        } else {

            errorHandler.addError("Invalid link syntax: " + line);

            return false;
        }
    }

    private boolean containsMixedElements(String line) {

        // Check for a combination of elements within the same line
        return line.contains("**") && line.contains("_") || line.contains("[");
    }

    private void parseMixedElements(String line, List<MarkdownElement> elements) {

        // Handle mixed elements, such as bold and italic in the same line
        String[] tokens = line.split("\\s+");

        for (String token : tokens) {

            if (isValidHeader(token)) {

                elements.add(new MarkdownElement("header", token));

            }else if (isValidBold(token)) {

                elements.add(new MarkdownElement("bold", token));

            } else if (isValidItalic(token)) {

                elements.add(new MarkdownElement("italic", token));

            } else if (isValidLink(token)) {

                elements.add(new MarkdownElement("link", token));

            } else {

                elements.add(new MarkdownElement("text", token));
            }
        }
    }
}