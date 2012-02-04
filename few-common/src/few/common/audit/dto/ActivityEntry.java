package few.common.audit.dto;

import few.Context;

import java.util.Date;

/**
 * User: igor
 * Date: 22.12.11
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
        this(null, level, type, text);
        if( Context.get() != null && Context.get().getRequest().getSession(false) != null && Context.get().getUserID() != null )
            user_id = Integer.valueOf(Context.get().getUserID());
    }

    public ActivityEntry(Integer user_id, Integer level, String type, String text) {
        this.level = level;
        this.type = type;
        this.text = text;
        this.user_id = user_id;
        this.timestamp = System.currentTimeMillis();
    }

    public Integer getUser_id() {
        return user_id;
    }

    public String getDisplay_name() {
        return display_name;
    }

    public String getRole() {
        return role;
    }

    public Integer getLevel() {
        return level;
    }

    public String getType() {
        return type;
    }

    public String getText() {
        return text;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public Date getTime() {
        return new Date(timestamp);
    }

}
