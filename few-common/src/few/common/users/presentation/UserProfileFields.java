package few.common.users.presentation;

import few.ModelBean;
import few.Restriction;
import few.common.CustomUserProfile;
import few.core.ServiceRegistry;
import few.utils.ListWrapper;

import java.util.Collections;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: igor
 * Date: 04.01.12
 * Time: 16:54
 * To change this template use File | Settings | File Templates.
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
