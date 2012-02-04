package few;

/**
 * User: gerbylev
 * Date: 02.11.11
 */
public class Message {
    public static final String NO_SOURCE = "all";

    public static class Severity {
        private String s;
        private Severity(String s) {
            this.s = s;
        }
        public String toString() {
            return s;
        }
    }
    public static Severity INFO    = new Severity("INFO");
    public static Severity WARNING = new Severity("WARNING");
    public static Severity ERROR   = new Severity("ERROR");

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
