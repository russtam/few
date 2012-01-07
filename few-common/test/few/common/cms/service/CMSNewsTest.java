package few.common.cms.service;

import few.Context;
import few.common.BaseTest;
import few.common.TestContext;
import few.common.cms.persistence.News;
import few.core.DispatcherMap;
import few.core.ServiceRegistry;
import few.services.Credentials;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Set;

/**
 * Created by IntelliJ IDEA.
 * User: igor
 * Date: 07.01.12
 * Time: 13:11
 * To change this template use File | Settings | File Templates.
 */
public class CMSNewsTest extends BaseTest{

    public static final String FEED = "test_feed";

    CMSService cmsService = CMSService.get();

    public void testCRUD() {
        News news = new News(FEED, "title", "text");

        cmsService.insertNews(news);
        List<News> list = cmsService.selectNewsFeed(FEED);
        assertTrue(list.contains(news));

        News news2 = cmsService.selectNews(news.news_id);
        assertEquals(news, news2);
        assertEquals(news.title, news2.title);
        assertEquals(news.text, news2.text);

        news2.title = "t2";
        news2.text = "t2";
        cmsService.updateNews(news2);

        News news3 = cmsService.selectNews(news.news_id);
        assertEquals(news2, news3);
        assertEquals(news2.title, news3.title);
        assertEquals(news2.text, news3.text);

        cmsService.updateNews(news2.news_id, "t3", "t3");

        news3 = cmsService.selectNews(news.news_id);
        assertEquals(news2, news3);
        assertEquals("t3", news3.title);
        assertEquals("t3", news3.text);

        cmsService.deleteNews(news.news_id);

        list = cmsService.selectNewsFeed(FEED);
        assertFalse(list.contains(news));

    }

    @Override
    public void tearDown() throws Exception {
        List<News> list = cmsService.selectNewsFeed(FEED);
        for (News news : list) {
            cmsService.deleteNews(news.news_id);
        }

    }
}
