package few.common.audit.persistence;

import java.util.Date;

/**
 * Created by IntelliJ IDEA.
 * User: igor
 * Date: 22.12.11
 * Time: 20:47
 * To change this template use File | Settings | File Templates.
 */
public class ActivityEntry {

    public Integer user_id;

    public String display_name;
    public String role;

    public Integer level;
    public String type;
    public String text;
    public Date timestamp;


}
