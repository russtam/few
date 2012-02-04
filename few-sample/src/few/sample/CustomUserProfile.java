package few.sample;

import java.util.LinkedList;
import java.util.List;

/**
 * User: igor
 * Date: 04.01.12
 */
public class CustomUserProfile implements few.common.CustomUserProfile{

    List<CustomField> fields;

    public CustomUserProfile() {
        fields = new LinkedList<CustomField>();

        fields.add(
                new CustomField("sex",          "Пол", true, "М/Ж", true, true) {
                    @Override
                    public boolean validate(String v) {
                        return v.equals("М") || v.equals("Ж");
                    }
                }
        );
        fields.add(
                new CustomField("birthday",     "Дата рождения")
        );
        fields.add(
                new CustomField("phone_number", "Номер телефона")
        );
    }

    public List<CustomField> getCustomFields() {
        return fields;
    }

}
