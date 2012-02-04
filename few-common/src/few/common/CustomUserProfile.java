package few.common;

import few.utils.Utils;

import java.util.List;

/**
 * User: igor
 * Date: 04.01.12
 */
public interface CustomUserProfile {

    List<CustomField> getCustomFields();

    public static class CustomField {
        private String field_id;
        private boolean required;
        private String displayName;
        private boolean displayInProfile;
        private boolean displayInUserList;
        private String hint;

        public boolean validate(String value) {
            if(Utils.isNull(value) && required)
                return false;
            return true;
        }
        public String format(String value) {
            return value;
        }

        public CustomField(String fieldId, String displayName) {
            this.field_id = fieldId;
            this.displayName = displayName;
        }

        public CustomField(String fieldId, String displayName, boolean required, String hint, boolean displayInProfile, boolean displayInUserList) {
            this.field_id = fieldId;
            this.required = required;
            this.displayName = displayName;
            this.displayInUserList = displayInUserList;
            this.displayInProfile = displayInProfile;
            this.hint = hint;
        }

        public String getField_id() {
            return field_id;
        }

        public boolean isRequired() {
            return required;
        }

        public String getDisplayName() {
            return displayName;
        }

        public boolean isDisplayInUserList() {
            return displayInUserList;
        }

        public String getHint() {
            return hint;
        }

        public boolean isDisplayInProfile() {
            return displayInProfile;
        }
    }

}
