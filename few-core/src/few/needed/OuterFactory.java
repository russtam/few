package few.needed;

/**
 * Created by IntelliJ IDEA.
 * User: gerbylev
 * Date: 29.11.11
 * Time: 18:55
 * To change this template use File | Settings | File Templates.
 */
public class OuterFactory {

    private final static OuterFactory instance = new OuterFactory();

    private OuterFactory() {
    }

    public static OuterFactory get() {
        return instance;
    }

    Credentials credentials;
    Configuration configuration;

    public static void init(
            Configuration configuration,
            Credentials credentials ) {
        instance.credentials = credentials;
        instance.configuration = configuration;
    }

    public Credentials getCredentials() {
        return credentials;
    }

    public Configuration getConfiguration() {
        return configuration;
    }
}
