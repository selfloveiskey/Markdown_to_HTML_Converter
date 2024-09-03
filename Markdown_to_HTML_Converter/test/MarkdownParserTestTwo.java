import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class MarkdownParserTestTwo {

    @BeforeEach
    void setUp() {
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void testHeaderOne() {
        MarkdownParserTwo markdownParserTwo = new MarkdownParserTwo();
        String markdown = "# Header 1";
        String expectedHtml = "<h1>Header 1</h1>";

        assertEquals(expectedHtml, markdownParserTwo.parse(markdown).trim());
    }

    @Test
    void testHeaderTwo() {
        MarkdownParserTwo markdownParserTwo = new MarkdownParserTwo();
        String markdown = "## Header 2";
        String expectedHtml = "<h2>Header 2</h2>";

        assertEquals(expectedHtml, markdownParserTwo.parse(markdown).trim());
    }

    @Test
    void testHeaderThree() {
        MarkdownParserTwo markdownParserTwo = new MarkdownParserTwo();
        String markdown = "### Header 3";
        String expectedHtml = "<h3>Header 3</h3>";

        assertEquals(expectedHtml, markdownParserTwo.parse(markdown).trim());
    }

    @Test
    void testHeaderFour() {
        MarkdownParserTwo markdownParserTwo = new MarkdownParserTwo();
        String markdown = "#### Header 4";
        String expectedHtml = "<h4>Header 4</h4>";

        assertEquals(expectedHtml, markdownParserTwo.parse(markdown).trim());
    }

    @Test
    void testHeaderFive() {
        MarkdownParserTwo markdownParserTwo = new MarkdownParserTwo();
        String markdown = "##### Header 5";
        String expectedHtml = "<h5>Header 5</h5>";

        assertEquals(expectedHtml, markdownParserTwo.parse(markdown).trim());
    }

    @Test
    void testHeaderSix() {
        MarkdownParserTwo markdownParserTwo = new MarkdownParserTwo();
        String markdown = "###### Header 6";
        String expectedHtml = "<h6>Header 6</h6>";

        assertEquals(expectedHtml, markdownParserTwo.parse(markdown).trim());
    }

    @Test
    void testBoldText() {
        MarkdownParserTwo markdownParserTwo = new MarkdownParserTwo();
        String markdown = "**Bold Text**";
        String expectedHtml = "<b>Bold Text</b>";

        assertEquals(expectedHtml, markdownParserTwo.parse(markdown).trim());
    }

    @Test
    void testItalicText() {
        MarkdownParserTwo markdownParserTwo = new MarkdownParserTwo();
        String markdown = "_Italic Text_";
        String expectedHtml = "<i>Italic Text</i>";

        assertEquals(expectedHtml, markdownParserTwo.parse(markdown).trim());
    }

    @Test
    void testLinks() {
        MarkdownParserTwo markdownParserTwo = new MarkdownParserTwo();
        String markdown = "[Link text](https://www.example.com)";
        String expectedHtml = "<a href=\"https://www.example.com\">Link text</a>";

        assertEquals(expectedHtml, markdownParserTwo.parse(markdown).trim());
    }

    @Test
    void testSentenceWithMultipleMarkdowns(){
        MarkdownParserTwo markdownParserTwo = new MarkdownParserTwo();
        String markdown = "# Hello World\n**Bold Text**\n_Italic Text_";
        String expectedHtml = "<h1>Hello World</h1>\n \n" + "<b>Bold Text</b>\n \n" + "<i>Italic Text</i>";

        assertEquals(expectedHtml, markdownParserTwo.parse(markdown).trim());
    }

    @Test
    void testMultipleMarkdownsAtOnce(){
        MarkdownParserTwo markdownParserTwo = new MarkdownParserTwo();
        String markdown = "# Header one\n" +
                "\n" +
                "Hello there\n" +
                "\n" +
                "How are you?\n What's going on?\n" +
                "\n" +
                "## Another Header\n" +
                "\n" +
                "This is a paragraph [with an inline link](http://google.com). Neat, eh?\n" +
                "\n" +
                "## This is a header [with a link](http://yahoo.com)";
        String expectedHtml = "<h1>Header one</h1>\n" +
                "\n" +
                "<p>Hello there</p>\n" +
                "\n" +
                "<p>How are you?</p>\n" +
                "\n" +
                "What's going on?</p>\n" +
                "\n" +
                "<h2>Another Header</h2>\n" +
                "\n" +
                "<p>This is a paragraph <a href=\"http://google.com\">with an inline link</a>. Neat, eh?</p>\n" +
                "\n" +
                "<h2>This is a header <a href=\"http://yahoo.com\">with a link</a></h2>";

        assertEquals(expectedHtml, markdownParserTwo.parse(markdown).trim());
    }
}