package few.common.users.dto;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * User: igor
 * Date: 06.01.12
 */
public class UserProfile {
    public int user_id;
    public List<CustomField> fieldsList;

    private Map<String, String> fieldsMap;

    public Map<String, String> getFieldsMap() {
        if( fieldsMap == null ) {
            fieldsMap = new HashMap<String, String>();
            for (CustomField field : fieldsList) {
                fieldsMap.put(field.field_id, field.value);
            }
            fieldsMap = Collections.unmodifiableMap(fieldsMap);
        }
        return fieldsMap;
    }

    public void setField(String field, String value) {
        for (CustomField customField : fieldsList) {
            if( customField.field_id.equals(field) ) {
                customField.value = value;
                return;
            }
        }
        fieldsList.add(new CustomField(field, value));
    }
}
