package few.common.audit.service;

/**
 * Created by IntelliJ IDEA.
 * User: igor
 * Date: 07.01.12
 * Time: 19:56
 * To change this template use File | Settings | File Templates.
 */
public class AuditKeys {

    public static final int MINOR = 0;
    public static final int NORMAL = 1;
    public static final int WARNING = 2;
    public static final int CRITICAL = 3;


    public static final String START = "few.common.start";
    public static final String SHUTDOWN = "few.common.shutdown";

    public static final String ERROR_REQUEST_PROCESSING = "few.common.error";

    public static final String CREATE_SESSION = "few.common.create_session";
    public static final String REGISTRATION = "few.common.registration";
    public static final String ACTIVATION = "few.common.activation";
    public static final String LOGIN = "few.common.login";
    public static final String BAD_LOGIN = "few.common.bad_login";
    public static final String LOGOUT = "few.common.logout";
    public static final String RESTORE_PASSWORD = "few.common.password_reminder";
    public static final String UPDATE_PROFILE = "few.common.update_profile";

    public static final String USER_DELETE = "few.users.delete";
    public static final String USER_DISABLE = "few.users.disable";
    public static final String USER_ACTIVATE = "few.users.activate";
    public static final String USER_UPDATE = "few.users.update";
    public static final String USER_ADD = "few.users.add";
    public static final String USER_NEW_PASSWORD = "few.users.new_password";
}
