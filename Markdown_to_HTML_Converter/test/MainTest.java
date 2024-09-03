import org.junit.After;
import org.junit.Before;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.*;

import static org.junit.jupiter.api.Assertions.*;

class MainTest {

    private final ByteArrayInputStream inputStream = new ByteArrayInputStream("".getBytes());
    private final ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
    private BufferedWriter writer;
    private BufferedReader reader;

    @BeforeEach
    public void setUp() throws IOException {
        System.setIn(inputStream);
        System.setOut(new PrintStream(outputStream));
        writer = new BufferedWriter(new OutputStreamWriter(outputStream));
        reader = new BufferedReader(new InputStreamReader(inputStream));
    }

    @Test
    public void testValidMarkdownInput() throws IOException {
        String markdownInput = "# Header\nThis is a **bold** statement.\n";
        String expectedHtmlOutput = "<h1>Header</h1>\n<p>This is a <b>bold</b> statement.</p>";

        setInput(markdownInput);
        Main.main(new String[]{});

        String actualHtmlOutput = outputStream.toString().trim();
        assertEquals(expectedHtmlOutput, actualHtmlOutput);
    }

    @Test
    public void testEmptyMarkdownInput() throws IOException {
        String markdownInput = "\n";
        String expectedHtmlOutput = "";

        setInput(markdownInput);
        Main.main(new String[]{});

        String actualHtmlOutput = outputStream.toString().trim();
        assertEquals(expectedHtmlOutput, actualHtmlOutput);
    }

    @Test
    public void testMarkdownWithErrors() throws IOException {
        String markdownInput = "#Header\nThis is a [broken link](example.com).\n";
        String expectedErrorOutput = "Errors found:\nInvalid header syntax\nInvalid link syntax";

        setInput(markdownInput);
        Main.main(new String[]{});

        String actualOutput = outputStream.toString().trim();
        assertEquals(expectedErrorOutput, actualOutput);
    }

    @Test
    public void testMultipleLinesMarkdownInput() throws IOException {
        String markdownInput = "# Header\nThis is **bold** text.\n_Italic text_ here.";
        String expectedHtmlOutput = "<h1>Header</h1>\n<p>This is <b>bold</b> text.</p>\n<p><i>Italic text</i> here.</p>";

        setInput(markdownInput);
        Main.main(new String[]{});

        String actualHtmlOutput = outputStream.toString().trim();
        assertEquals(expectedHtmlOutput, actualHtmlOutput);
    }

    @Test
    public void testMarkdownWithNoSpecialElements() throws IOException {
        String markdownInput = "Just a plain text.";
        String expectedHtmlOutput = "<p>Just a plain text.</p>";

        setInput(markdownInput);
        Main.main(new String[]{});

        String actualHtmlOutput = outputStream.toString().trim();
        assertEquals(expectedHtmlOutput, actualHtmlOutput);
    }

    private void setInput(String data) {
        System.setIn(new ByteArrayInputStream(data.getBytes()));
    }
}