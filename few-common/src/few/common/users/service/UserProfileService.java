package few.common.users.service;

import few.common.CustomUserProfile;
import few.common.users.dto.CustomField;
import few.core.ServiceRegistry;

import java.util.*;

/**
 * User: igor
 * Date: 04.01.12
 */
public class UserProfileService {

    private static UserProfileService instance = new UserProfileService();
    public static UserProfileService get() {
        return instance;
    }

    CustomUserProfile userProfile = ServiceRegistry.get(CustomUserProfile.class);
    Map<String, CustomUserProfile.CustomField> customFieldMap;

    private UserProfileService() {
        customFieldMap = new HashMap<String, CustomUserProfile.CustomField>();
        if( userProfile != null ) {
            List<CustomUserProfile.CustomField> fields = userProfile.getCustomFields();

            for (CustomUserProfile.CustomField field : fields) {
                customFieldMap.put(field.getField_id(), field);
            }
        }
        customFieldMap = Collections.unmodifiableMap(customFieldMap);
    }

    public List<CustomField> validateProfile(Map<String, String[]> fields) {
        if( userProfile == null && !fields.isEmpty() )
            return null;

        List<CustomField> list = new ArrayList<CustomField>(fields.size());
        for (Map.Entry<String, String[]> entry : fields.entrySet()) {
            CustomUserProfile.CustomField f = getCustomFields().get(entry.getKey());
            if( f != null ) {
                if( !f.validate(entry.getValue()[0]) )
                    return null;

                list.add(new CustomField(entry.getKey(), entry.getValue()[0]));
            }
        }
        return list;
    }

    public Map<String, CustomUserProfile.CustomField> getCustomFields() {
        return customFieldMap;
    }

}
