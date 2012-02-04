package few.common.cms.dao;

import few.common.BaseMyBatisServiceImpl;
import few.common.cms.dto.News;
import few.common.cms.dto.SimpleText;
import few.utils.MapBuilder;

import java.util.List;

/**
 * User: igor
 * Date: 22.12.11
 */
public class CMSService extends BaseMyBatisServiceImpl {

    private static CMSService instance = new CMSService();
    public static CMSService get() {
        return instance;
    }
    private CMSService() {
    }


    public List<SimpleText> selectSimpleText(String cat_id) {
        return session().selectList("few.common.selectSimpleText", new MapBuilder().add("cat_id", cat_id));
    }
    public SimpleText selectSimpleText(String cat_id, String text_id) {
        return (SimpleText) session().selectOne("few.common.selectSimpleText",
                new MapBuilder().add("cat_id", cat_id).add("text_id", text_id));
    }
    public void insetSimpleText(SimpleText text) {
        session().insert("few.common.insertSimpleText", text);
        session().commit();
    }
    public void updateSimpleText(SimpleText text) {
        session().update("few.common.updateSimpleText", text);
        session().commit();
    }
    public void deleteSimpleText(String cat_id, String text_id) {
        session().delete("few.common.deleteSimpleText",
                new MapBuilder().add("cat_id", cat_id).add("text_id", text_id));
        session().commit();
    }


    public List<News> selectNewsFeed(String feed_id) {
        return session().selectList("few.common.selectNews", new MapBuilder().add("feed_id", feed_id));
    }
    public News selectNews(Integer news_id) {
        return (News) session().selectOne("few.common.selectNews", new MapBuilder().add("news_id", news_id));
    }
    public void insertNews(News post) {
        post.news_id = (Integer) session().selectOne("few.common.select_uid");
        session().insert("few.common.insertNews", post);
        session().commit();
    }
    public void updateNews(int news_id, String title, String text) {
        session().update("few.common.updateNews",
                new MapBuilder().add("news_id", news_id).add("title", title).add("text", text));
        session().commit();
    }
    public void updateNews(News post) {
        session().update("few.common.updateNews", post);
        session().commit();
    }
    public void deleteNews(Integer id) {
        session().delete("few.common.deleteNews", id);
        session().commit();
    }

}
