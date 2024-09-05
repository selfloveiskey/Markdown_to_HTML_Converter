import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/*
 * Responsibilities:
 *  (a) Handle the logic for converting parsed markdown elements into HTML elements.
 *  (b) Ensure the HTML generated is syntactically correct and properly nested.
 *  (c) Map different types of markdown elements to their HTML counterparts.
 */
public class MarkdownConverter {

    public String convertToHtml(List<MarkdownElement> elements) {

        StringBuilder html = new StringBuilder();

        for (MarkdownElement element : elements) {

            switch (element.getType()) {

                case "multiple":
                    html.append(convertMultipleElements(element.getContent())).append("\n\n");
                    break;
                case "header":
                    html.append(convertHeader(element.getContent())).append("\n\n");
                    break;
                case "bold":
                    html.append(convertBold(element.getContent())).append("\n\n");
                    break;
                case "italic":
                    html.append(convertItalic(element.getContent())).append("\n\n");
                    break;
                case "link":
                    html.append(convertLink(element.getContent())).append("\n\n");
                    break;
                case "paragraph":
                    html.append(convertParagraph(element.getContent())).append("\n\n");
                    break;
                default:
                    break;
            }
        }

        return html.toString().trim();
    }

    private String convertHeader(String markdown) {

        for (int i = 6; i >= 1; i--) {

            String pattern = "^(#{"+i+"}) (.*)$";

            String replacement = "<h"+i+">$2</h"+i+">";

            markdown = markdown.replaceAll(pattern, replacement);
        }
        return markdown;
    }

    private String convertBold(String markdown) {

        return "<b>" + markdown.substring(2, markdown.length() - 2) + "</b>";
    }

    private String convertItalic(String markdown) {

        return "<i>" + markdown.substring(1, markdown.length() - 1) + "</i>";
    }

    private String convertLink(String markdown) {

        return markdown.replaceAll("\\[(.*?)\\]\\((.*?)\\)", "<a href=\"$2\">$1</a>");
    }


    private String convertParagraph(String markdown) {

        return "<p>" + markdown + "</p>";
    }

    private String convertMultipleElements(String markdown) {

        //
        StringBuilder htmlOutput = new StringBuilder();
        String[] lines = markdown.split("\n");

        // Patterns for bold, italic, links, and headers
        String headerPattern = "^(#{1,6}) (.*)$";
        String boldPattern = "(\\*\\*.*?\\*\\*)";
        String italicPattern = "(_.*?_)|(\\*.*?\\*)";
        String linkPattern = "\\[.*?\\]\\(.*?\\)";
        String paragraphPattern = "^(#{1,6} .*)$|\\*\\*[^*]+\\*\\*|_[^_]+_|\\*[^*]+\\*|\\[[^\\]]+\\]\\([^\\)]+\\)";

        // Compile each pattern
        Pattern header = Pattern.compile(headerPattern);
        Pattern bold = Pattern.compile(boldPattern);
        Pattern italic = Pattern.compile(italicPattern);
        Pattern link = Pattern.compile(linkPattern);
        Pattern paragraph = Pattern.compile(paragraphPattern);

        // Check for matches
        Matcher headerMatcher = header.matcher(markdown);
        Matcher boldMatcher = bold.matcher(markdown);
        Matcher italicMatcher = italic.matcher(markdown);
        Matcher linkMatcher = link.matcher(markdown);
        Matcher paragraphMatcher = paragraph.matcher(markdown);

        // Loop though each line and convert the entire markdown string to HTML
        for (String line : lines) {

            if (line.isEmpty()) continue;

            //
            if (headerMatcher.find()) {

                htmlOutput.append(convertHeader(line));
            }
            if (boldMatcher.find()) {

                htmlOutput.append(convertBold(line));
            }
            if (italicMatcher.find()) {

                htmlOutput.append(convertItalic(line));
            }
            if (linkMatcher.find()){

                htmlOutput.append(convertLink(line));
            }
            if (!(headerMatcher.find()
                    && boldMatcher.find()
                    && italicMatcher.find()
                    && linkMatcher.find())){

                htmlOutput.append(convertParagraph(line));
            }
        }
        return htmlOutput.toString().trim();
    }
}