package few.common.users.model;

import few.ModelBean;
import few.common.CustomUserProfile;
import few.core.ServiceRegistry;
import few.utils.ListWrapper;

import java.util.Collections;
import java.util.List;

/**
 * User: igor
 * Date: 04.01.12
 */
@ModelBean(name = "user_profile_fields")
public class UserProfileFields extends ListWrapper<CustomUserProfile.CustomField>{

    private UserProfileFields(List<CustomUserProfile.CustomField> customFields) {
        super(customFields);
    }

    static private CustomUserProfile profile = ServiceRegistry.get(CustomUserProfile.class);
    public static UserProfileFields build() {
        if( profile != null)
            return new UserProfileFields(profile.getCustomFields()) ;
        else
            return new UserProfileFields(Collections.<CustomUserProfile.CustomField>emptyList());
    }

}
