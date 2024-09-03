import java.util.List;

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
}