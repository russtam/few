package few.common.cms.persistence;

/**
 * Created by IntelliJ IDEA.
 * User: igor
 * Date: 22.12.11
 * Time: 22:09
 * To change this template use File | Settings | File Templates.
 */
public class SimpleText {
    public String cat_id;
    public String text_id;
    public String text;

    public SimpleText() {
    }

    public SimpleText(String cat_id, String textId, String text) {
        this.cat_id = cat_id;
        this.text_id = textId;
        this.text = text;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        SimpleText that = (SimpleText) o;

        if (cat_id != null ? !cat_id.equals(that.cat_id) : that.cat_id != null) return false;
        if (text_id != null ? !text_id.equals(that.text_id) : that.text_id != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = cat_id != null ? cat_id.hashCode() : 0;
        result = 31 * result + (text_id != null ? text_id.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return text;
    }
}
