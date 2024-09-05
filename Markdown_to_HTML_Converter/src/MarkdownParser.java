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

            if (containsMultipleElements(line)) {

                elements.add(new MarkdownElement("multiple", line));

            }else if (isValidHeader(line)) {

                elements.add(new MarkdownElement("header", line));

            } else if (isValidBold(line)) {

                elements.add(new MarkdownElement("bold", line));

            } else if (isValidItalic(line)) {

                elements.add(new MarkdownElement("italic", line));

            } else if (isValidLink(line)) {

                elements.add(new MarkdownElement("link", line));

            } else if (isValidParagraph(line)) {

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

            return true;

        } else {

            errorHandler.addError("Invalid bold syntax: " + line);

            return false;
        }
    }

    private boolean isValidItalic(String line) {

        if (line.startsWith("_") && line.endsWith("_")) {

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

    private boolean isValidParagraph(String line) {

        Pattern pattern = Pattern.compile("^(?!<(h\\d|strong|em|a)).+");

        Matcher matcher = pattern.matcher(line);

        if (matcher.matches()) {

            return true;

        } else {

            errorHandler.addError("Invalid header syntax: " + line);

            return false;
        }
    }

    private boolean containsMultipleElements(String line) {

        int numOfElements = 0;

        // Patterns for bold, italic, links, and headers
        String headerPattern = "^(#{1,6}) (.*)$";
        String boldPattern = "(\\*\\*.*?\\*\\*)";
        String italicPattern = "(_.*?_)|(\\*.*?\\*)";
        String linkPattern = "\\[.*?\\]\\(.*?\\)";
        //String paragraphPattern = "^(#{1,6} .*)$|\\*\\*[^*]+\\*\\*|_[^_]+_|\\*[^*]+\\*|\\[[^\\]]+\\]\\([^\\)]+\\)";

        // Compile each pattern
        Pattern header = Pattern.compile(headerPattern);
        Pattern bold = Pattern.compile(boldPattern);
        Pattern italic = Pattern.compile(italicPattern);
        Pattern link = Pattern.compile(linkPattern);
        //Pattern paragraph = Pattern.compile(paragraphPattern);

        // Check for matches
        Matcher headerMatcher = header.matcher(line);
        Matcher boldMatcher = bold.matcher(line);
        Matcher italicMatcher = italic.matcher(line);
        Matcher linkMatcher = link.matcher(line);
        //Matcher paragraphMatcher = paragraph.matcher(line);

        if (headerMatcher.find()) {
            numOfElements++;
        }
        if (boldMatcher.find()) {
            numOfElements++;
        }
        if (italicMatcher.find()) {
            numOfElements++;
        }
        if (linkMatcher.find()) {
            numOfElements++;
        }
/*        if (paragraphMatcher.find()) {
            numOfElements++;
        }*/

        boolean isValidMixedLine = true;

        if ((isValidHeader(line)
                || isValidBold(line)
                || isValidItalic(line)
                || isValidLink(line)
                || isValidParagraph(line))
            && numOfElements <= 1 ){

                isValidMixedLine = false;

        } else if ((isValidHeader(line)
                || isValidBold(line)
                || isValidItalic(line)
                || isValidLink(line)
                || isValidParagraph(line))
                && numOfElements >= 2) {

            isValidMixedLine = true;
        }

        return isValidMixedLine;
    }
}