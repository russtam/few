package few.sample;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: igor
 * Date: 04.01.12
 * Time: 15:31
 * To change this template use File | Settings | File Templates.
 */
public class CustomUserProfile implements few.common.CustomUserProfile{

    List<CustomField> fields;

    public CustomUserProfile() {
        fields = new LinkedList<CustomField>();

        fields.add(
                new CustomField("sex",          "Пол", true, "М/Ж", true) {
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
