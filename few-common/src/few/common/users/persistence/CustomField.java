package few.common.users.persistence;

/**
 * Created by IntelliJ IDEA.
 * User: igor
 * Date: 04.01.12
 * Time: 16:14
 * To change this template use File | Settings | File Templates.
 */
public class CustomField {
    public String field_id;
    public String value;

    public CustomField() {
    }

    public CustomField(String field_id, String value) {
        this.field_id = field_id;
        this.value = value;
    }
}
