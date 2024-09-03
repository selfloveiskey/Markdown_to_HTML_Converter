public class MarkdownElement {
    private String type;
    private String content;

    public MarkdownElement(String type, String content) {
        this.type = type;
        this.content = content;
    }

    public String getType() {
        return type;
    }

    public String getContent() {
        return content;
    }
}