package few;

/**
 * User: gerbylev
 * Date: 02.11.11
 */
public class Message {
    public static final String NO_SOURCE = "all";

    public static Severity INFO    = Severity.INFO;
    public static Severity WARNING = Severity.WARNING;
    public static Severity ERROR   = Severity.ERROR;

    private Severity severity;
    private String source;
    private String message;


    public Message(Severity severity, String message) {
        this.severity = severity;
        this.source = NO_SOURCE;
        this.message = message;
    }

    public Message(Severity severity, String source, String message) {
        this.severity = severity;
        this.source = source;
        this.message = message;
    }

    public Severity getSeverity() {
        return severity;
    }

    public String getSource() {
        return source;
    }

    public String getMessage() {
        return message;
    }
}
