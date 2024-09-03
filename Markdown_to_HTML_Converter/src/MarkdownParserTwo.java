public class MarkdownParserTwo {
    public String parse(String markdownText) {

        // Input check
        if (markdownText == null || markdownText.isEmpty()) {
            return "";
        }

        // Split the entire Markdown text into individual lines using newline characters
        String[] lines = markdownText.split("\n");

        // Initialize a StringBuilder to hold the generated HTML content
        StringBuilder result = new StringBuilder();

        // Process each line
        for (String line : lines) {

            line = line.trim();

            // Skip blank lines
            if (line.isEmpty()) {
                continue;
            }

            // Handle different markdown types:
            if (line.startsWith("#")) {

                result.append(parseHeaders(line)).append("\n \n");

            } else if (line.startsWith("**") && line.endsWith("**")) {

                result.append(parseBold(line)).append("\n \n");

            } else if (line.startsWith("_") && line.endsWith("_")) {

                result.append(parseItalic(line)).append("\n \n");

            } else if (line.matches("\\[.*?\\]\\(.*?\\)")) {

                result.append(parseLinks(line)).append("\n \n");

            } else if (containsMultipleMarkdowns(line)) {

                result.append(parseMultipleMarkdowns(line)).append("\n \n");
            }
            else {

                result.append(parseParagraphs(line)).append("\n \n");
            }
        }
        // Return result
        return result.toString().trim();
    }

    private String parseHeaders(String markdown) {

        // Determine header level
        int headerLevel = markdown.indexOf(' ');

        if (headerLevel == -1) {
            return markdown; // Invalid header, return as is
        }

        // Extract content and format
        String content = markdown.substring(headerLevel + 1);

        return "<h" + headerLevel + ">" + content + "</h" + headerLevel + ">";
    }

    // Convert bold syntax
    private String parseBold(String markdown) {

        return "<b>" + markdown.substring(2, markdown.length() - 2) + "</b>";
    }

    // Strips the _ markers and wraps the content in <i> tags to represent italic text in HTML.
    private String parseItalic(String markdown) {

        return "<i>" + markdown.substring(1, markdown.length() - 1) + "</i>";
    }

    // Uses a regular expression to convert Markdown link syntax into HTML anchor tags.
    private String parseLinks(String markdown) {

        return markdown.replaceAll("\\[(.*?)\\]\\((.*?)\\)", "<a href=\"$2\">$1</a>");
    }
    //  Wraps plain text in <p> tags
    private String parseParagraphs(String markdown) {

        return "<p>" + markdown + "</p>";
    }

    // Determines if a line contains more than one type of markdown element.
    private boolean containsMultipleMarkdowns(String line) {
        return line.contains("#") || line.contains("**") || line.contains("_") || line.contains("[") && line.contains("](");
    }

    // Handle lines containing multiple markdown elements.
    private String parseMultipleMarkdowns(String line) {
        return parseInlineMarkdown(line);
    }
    private String parseInlineMarkdown(String line) {

        StringBuilder result = new StringBuilder();
        int pos = 0;

        // Loop through the line to find and process markdown elements.
        // Identify the next markdown element, appends the plain text up to that element,
        // and then process the markdown element itself.
        while (pos < line.length()) {

            // Find the next markdown delimiter positions
            int nextHeader = line.indexOf("#", pos);
            int nextBold = line.indexOf("**", pos);
            int nextItalic = line.indexOf("_", pos);
            int nextLink = line.indexOf("[", pos);

            // Determine the next position to process
            int nextPos = line.length();

            if (nextHeader != -1 && nextHeader < nextPos) nextPos = nextHeader;
            if (nextBold != -1 && nextBold < nextPos) nextPos = nextBold;
            if (nextItalic != -1 && nextItalic < nextPos) nextPos = nextItalic;
            if (nextLink != -1 && nextLink < nextPos) nextPos = nextLink;

            // Append text segment before the next markdown element
            if (nextPos > pos) {
                result.append(parseParagraphs(line.substring(pos, nextPos)));

                pos = nextPos;
            }

            // Process the markdown element
            if (pos < line.length()) {
                if (line.startsWith("##", pos)) {

                    int endOfHeader = line.indexOf('\n', pos);

                    if (endOfHeader == -1) endOfHeader = line.length();

                    result.append(parseHeaders(line.substring(pos, endOfHeader).trim()));

                    // Update pos to the end of the header.
                    pos = endOfHeader;

                } else if (line.startsWith("**", pos)) {

                    int endBold = line.indexOf("**", pos + 2);

                    if (endBold != -1) {

                        result.append(parseBold(line.substring(pos, endBold + 2)));

                        // Update pos to the end of the bold text.
                        pos = endBold + 2;
                    }
                } else if (line.startsWith("_", pos)) {

                    int endItalic = line.indexOf("_", pos + 1);

                    if (endItalic != -1) {

                        result.append(parseItalic(line.substring(pos, endItalic + 1)));

                        // Update pos to the end of the italic text.
                        pos = endItalic + 1;
                    }
                } else if (line.startsWith("[", pos)) {

                    int endLink = line.indexOf(")", pos);

                    if (endLink != -1) {

                        result.append(parseLinks(line.substring(pos, endLink + 1)));

                        // Update pos to the end of the link.
                        pos = endLink + 1;
                    }
                }
            }
        }
        return result.toString();
    }
}