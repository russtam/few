package few.common.cms.persistence;

import few.Context;

import java.util.Date;

/**
 * Created by IntelliJ IDEA.
 * User: igor
 * Date: 22.12.11
 * Time: 22:21
 * To change this template use File | Settings | File Templates.
 */
public class News {
    public String feed_id;
    public Integer news_id;
    public String title;
    public String text;

    public Integer author_user_id;
    public String author_display_name;

    public Date creation_time;
    public Date modification_time;

    public News() {
    }

    public News(String feed_id, String title, String text) {
        this.feed_id = feed_id;
        this.title = title;
        this.text = text;
        author_user_id = Integer.valueOf(Context.get().getUserID());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        News news = (News) o;

        if (feed_id != null ? !feed_id.equals(news.feed_id) : news.feed_id != null) return false;
        if (news_id != null ? !news_id.equals(news.news_id) : news.news_id != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = feed_id != null ? feed_id.hashCode() : 0;
        result = 31 * result + (news_id != null ? news_id.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return title + " : { " + text + " }";
    }
}
