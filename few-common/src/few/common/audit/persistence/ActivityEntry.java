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
        this(Integer.valueOf(Context.get().getUserID()), level, type, text);
    }

    public ActivityEntry(int user_id, Integer level, String type, String text) {
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
