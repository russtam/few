package few.common.users.dto;

/**
 * User: igor
 * Date: 04.01.12
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
