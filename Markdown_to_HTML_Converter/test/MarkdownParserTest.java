import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class MarkdownParserTest {

    private MarkdownParser parser;
    private MarkdownErrorHandler errorHandler;

    @BeforeEach
    public void setUp() {
        errorHandler = new MarkdownErrorHandler();
        parser = new MarkdownParser(errorHandler);
    }

    @Test
    public void testValidHeader() {
//        MarkdownParser parser = new MarkdownParser();
        String markdown = "# This is a header";

        List<MarkdownElement> elements = parser.parse(markdown);

        assertEquals(1, elements.size());
        assertEquals("header", elements.get(0).getType());
        assertEquals("# This is a header", elements.get(0).getContent());

        // Ensure errors are reported
        assertFalse(errorHandler.hasErrors());
    }

    @Test
    public void testInvalidHeader() {
//        MarkdownParser parser = new MarkdownParser();
        String markdown = "####### Header 7";

        List<MarkdownElement> elements = parser.parse(markdown);

        // Only one valid element should be added
        assertEquals(1, elements.size());

        // Ensure errors are reported
        assertTrue(errorHandler.hasErrors());
        assertTrue(errorHandler.getErrors().contains("Invalid header syntax"));
        assertTrue(errorHandler.getErrors().contains("Invalid italic syntax"));
        assertTrue(errorHandler.getErrors().contains("Invalid bold syntax"));
        assertTrue(errorHandler.getErrors().contains("Invalid link syntax"));
    }

    @Test
    public void testValidBold() {
//        MarkdownParser parser = new MarkdownParser();
        String markdown = "**Bold text**";

        List<MarkdownElement> elements = parser.parse(markdown);

        assertEquals(1, elements.size());
        assertEquals("bold", elements.get(0).getType());
        assertEquals("**Bold text**", elements.get(0).getContent());

        // Ensure errors are reported
        assertTrue(errorHandler.hasErrors());
        assertTrue(errorHandler.getErrors().contains("Invalid header syntax"));
    }

    @Test
    public void testInvalidBold() {
//        MarkdownParser parser = new MarkdownParser();
        String markdown = "**Unbalanced bold text";

        List<MarkdownElement> elements = parser.parse(markdown);

        // Only one valid element should be added
        assertEquals(1, elements.size());

        // Ensure errors are reported
        assertTrue(errorHandler.hasErrors());
        assertTrue(errorHandler.getErrors().contains("Invalid header syntax"));
        assertTrue(errorHandler.getErrors().contains("Invalid italic syntax"));
        assertTrue(errorHandler.getErrors().contains("Invalid bold syntax"));
        assertTrue(errorHandler.getErrors().contains("Invalid link syntax"));
    }

    @Test
    public void testValidItalic() {
//        MarkdownParser parser = new MarkdownParser();
        String markdown = "_Italic text_";

        List<MarkdownElement> elements = parser.parse(markdown);

        assertEquals(1, elements.size());
        assertEquals("italic", elements.get(0).getType());
        assertEquals("_Italic text_", elements.get(0).getContent());

        // Ensure errors are reported
        assertTrue(errorHandler.hasErrors());
        assertTrue(errorHandler.getErrors().contains("Invalid header syntax"));
        assertTrue(errorHandler.getErrors().contains("Invalid bold syntax"));
    }

    @Test
    public void testInvalidItalic() {
//        MarkdownParser parser = new MarkdownParser();
        String markdown = "_Unbalanced italic text";

        List<MarkdownElement> elements = parser.parse(markdown);

        // Only one valid element should be added
        assertEquals(1, elements.size());

        // Ensure errors are reported
        assertTrue(errorHandler.hasErrors());
        assertTrue(errorHandler.getErrors().contains("Invalid header syntax"));
        assertTrue(errorHandler.getErrors().contains("Invalid italic syntax"));
        assertTrue(errorHandler.getErrors().contains("Invalid bold syntax"));
        assertTrue(errorHandler.getErrors().contains("Invalid link syntax"));
    }

    @Test
    public void testValidLink() {
//        MarkdownParser parser = new MarkdownParser();
        String markdown = "[OpenAI](https://openai.com)";

        List<MarkdownElement> elements = parser.parse(markdown);

        assertEquals(1, elements.size());
        assertEquals("link", elements.get(0).getType());
        assertEquals("[OpenAI](https://openai.com)", elements.get(0).getContent());

        // Ensure errors are reported
        assertTrue(errorHandler.hasErrors());
        assertTrue(errorHandler.getErrors().contains("Invalid header syntax"));
        assertTrue(errorHandler.getErrors().contains("Invalid italic syntax"));
        assertTrue(errorHandler.getErrors().contains("Invalid bold syntax"));
    }

    @Test
    public void testInvalidLink() {
//        MarkdownParser parser = new MarkdownParser();
        String markdown = "[OpenAI](https://openai.com";

        List<MarkdownElement> elements = parser.parse(markdown);

        assertEquals(1, elements.size());

        // Ensure errors are reported
        assertTrue(errorHandler.hasErrors());
        assertTrue(errorHandler.getErrors().contains("Invalid header syntax"));
        assertTrue(errorHandler.getErrors().contains("Invalid italic syntax"));
        assertTrue(errorHandler.getErrors().contains("Invalid bold syntax"));
        assertTrue(errorHandler.getErrors().contains("Invalid link syntax"));

    }

    @Test
    public void testMixedElements() {
//        MarkdownParser parser = new MarkdownParser();
        String markdown = "**Bold** _Italic_ [OpenAI](https://openai.com)";

        List<MarkdownElement> elements = parser.parse(markdown);

        assertEquals(3, elements.size());
        assertEquals("bold", elements.get(0).getType());
        assertEquals("italic", elements.get(1).getType());
        assertEquals("link", elements.get(2).getType());

        // Ensure errors are reported
        assertTrue(errorHandler.hasErrors());
        assertTrue(errorHandler.getErrors().contains("Invalid header syntax"));
    }

    @Test
    public void testParagraph() {
//        MarkdownParser parser = new MarkdownParser();
        String markdown = "This is a paragraph.";

        List<MarkdownElement> elements = parser.parse(markdown);

        assertEquals(1, elements.size());
        assertEquals("paragraph", elements.get(0).getType());
        assertEquals("This is a paragraph.", elements.get(0).getContent());

        // Ensure errors are reported
        assertTrue(errorHandler.hasErrors());
        assertTrue(errorHandler.getErrors().contains("Invalid header syntax"));
        assertTrue(errorHandler.getErrors().contains("Invalid italic syntax"));
        assertTrue(errorHandler.getErrors().contains("Invalid bold syntax"));
        assertTrue(errorHandler.getErrors().contains("Invalid link syntax"));
    }

    @Test
    public void testMultipleLines() {
//        MarkdownParser parser = new MarkdownParser();
        String markdown = "# Header\n**Bold text**\nThis is a paragraph.\n[OpenAI](https://openai.com)";

        List<MarkdownElement> elements = parser.parse(markdown);

        assertEquals(4, elements.size());
        assertEquals("header", elements.get(0).getType());
        assertEquals("bold", elements.get(1).getType());
        assertEquals("paragraph", elements.get(2).getType());
        assertEquals("link", elements.get(3).getType());

        // Ensure errors are reported
        assertTrue(errorHandler.hasErrors());
        assertTrue(errorHandler.getErrors().contains("Invalid header syntax"));
        assertTrue(errorHandler.getErrors().contains("Invalid italic syntax"));
        assertTrue(errorHandler.getErrors().contains("Invalid bold syntax"));
        assertTrue(errorHandler.getErrors().contains("Invalid link syntax"));
    }

    @Test
    public void testErrorsAndValidElements() {
//        MarkdownParser parser = new MarkdownParser();
        String markdown = "**Valid Bold**\nInvalid _Italic\n[OpenAI](https://openai.com)\n_Invalid Italic";

        List<MarkdownElement> elements = parser.parse(markdown);

        // Valid elements count
        assertEquals(4, elements.size());
        assertEquals("bold", elements.get(0).getType());
        assertEquals("paragraph", elements.get(1).getType());
        assertEquals("link", elements.get(2).getType());
        assertEquals("paragraph", elements.get(1).getType());

        // Ensure errors are reported
        assertTrue(errorHandler.hasErrors());
        assertTrue(errorHandler.getErrors().contains("Invalid header syntax"));
        assertTrue(errorHandler.getErrors().contains("Invalid italic syntax"));
        assertTrue(errorHandler.getErrors().contains("Invalid bold syntax"));
        assertTrue(errorHandler.getErrors().contains("Invalid link syntax"));
    }
}