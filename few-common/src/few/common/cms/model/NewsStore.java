package few.common.cms.model;

import few.ModelBean;
import few.common.cms.dto.News;
import few.common.cms.dao.CMSService;

import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: igor
 * Date: 07.01.12
 * Time: 14:25
 * To change this template use File | Settings | File Templates.
 */
@ModelBean(name = "news_store")
public class NewsStore {

    public List<News> selectFeed(String id) {
        return cmsService.selectNewsFeed(id);
    }

    public News selectNews(Integer id) {
        return cmsService.selectNews(id);
    }

    private static CMSService cmsService = CMSService.get();

    public static NewsStore build() {
        return new NewsStore();
    }
}
