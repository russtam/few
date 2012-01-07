package few.common.audit.persistence;

import few.Context;

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
    public long timestamp;

    public ActivityEntry() {
    }

    public ActivityEntry(Integer level, String type, String text) {
        this.level = level;
        this.type = type;
        this.text = text;

        if( Context.get() != null && Context.get().getUserID() != null )
            user_id = Integer.valueOf(Context.get().getUserID());
        timestamp = System.currentTimeMillis();
    }
}
