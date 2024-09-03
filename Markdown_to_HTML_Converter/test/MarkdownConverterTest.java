import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class MarkdownConverterTest {

    private final MarkdownConverter converter = new MarkdownConverter();

    @Test
    public void testConvertHeader() {
        List<MarkdownElement> elements = new ArrayList<>();
        elements.add(new MarkdownElement("header", "# Header"));

        String html = converter.convertToHtml(elements);
        String expectedHtml = "<h1>Header</h1>";

        assertEquals(expectedHtml, html);
    }

    @Test
    public void testConvertBold() {
        List<MarkdownElement> elements = new ArrayList<>();
        elements.add(new MarkdownElement("bold", "**Bold Text**"));

        String html = converter.convertToHtml(elements);
        String expectedHtml = "<b>Bold Text</b>";

        assertEquals(expectedHtml, html);
    }

    @Test
    public void testConvertItalic() {
        List<MarkdownElement> elements = new ArrayList<>();
        elements.add(new MarkdownElement("italic", "_Italic Text_"));

        String html = converter.convertToHtml(elements);
        String expectedHtml = "<i>Italic Text</i>";

        assertEquals(expectedHtml, html);
    }

    @Test
    public void testConvertLink() {
        List<MarkdownElement> elements = new ArrayList<>();
        elements.add(new MarkdownElement("link", "[Google](http://google.com)"));

        String html = converter.convertToHtml(elements);
        String expectedHtml = "<a href=\"http://google.com\">Google</a>";

        assertEquals(expectedHtml, html);
    }

    @Test
    public void testConvertParagraph() {
        List<MarkdownElement> elements = new ArrayList<>();
        elements.add(new MarkdownElement("paragraph", "This is a paragraph"));

        String html = converter.convertToHtml(elements);
        String expectedHtml = "<p>This is a paragraph</p>";

        assertEquals(expectedHtml, html);
    }

    @Test
    public void testConvertMultipleElements() {
        List<MarkdownElement> elements = new ArrayList<>();
        elements.add(new MarkdownElement("header", "# Header"));
        elements.add(new MarkdownElement("bold", "**Bold Text**"));
        elements.add(new MarkdownElement("italic", "_Italic Text_"));
        elements.add(new MarkdownElement("link", "[Google](http://google.com)"));
        elements.add(new MarkdownElement("paragraph", "This is a paragraph"));

        String html = converter.convertToHtml(elements);
        String expectedHtml = "<h1>Header</h1>\n" +
                "\n" +
                "<b>Bold Text</b>\n" +
                "\n" +
                "<i>Italic Text</i>\n" +
                "\n" +
                "<a href=\"http://google.com\">Google</a>\n" +
                "\n" +
                "<p>This is a paragraph</p>";

        assertEquals(expectedHtml, html);
    }
}