import java.util.ArrayList;
import java.util.List;

/*
 * Responsibilities:
 *  (a) Manage and report errors encountered during parsing or conversion.
 *  (b) Provide meaningful error messages to users to help them correct their markdown input.
 *  (c) Ensure the system fails gracefully without crashing.
 */
public class MarkdownErrorHandler {
    private final List<String> errors;

    public MarkdownErrorHandler() {

        this.errors = new ArrayList<>();
    }

    public void addError(String error) {

        errors.add(error);
    }

    public boolean hasErrors() {

        return !errors.isEmpty();
    }

    public String getErrors() {

        return String.join("\n", errors);
    }

    public void clearErrors() {

        errors.clear();
    }
}