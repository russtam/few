package few.common.properties;

/**
 * Created by IntelliJ IDEA.
 * User: gerbylev
 * Date: 18.11.11
 * Time: 21:51
 * To change this template use File | Settings | File Templates.
 */
public class SystemProperty {
    public String key;
    public String value;

    public SystemProperty() {
    }

    public SystemProperty(String key, String value) {
        this.key = key;
        this.value = value;
    }
}
