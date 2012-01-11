package few.common.cms.presentation;

import few.ModelBean;
import few.common.cms.persistence.SimpleText;
import few.common.cms.service.CMSService;

import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: igor
 * Date: 07.01.12
 * Time: 14:33
 * To change this template use File | Settings | File Templates.
 */
@ModelBean(name = "texts_store")
public class TextsStore {

    public List<SimpleText> selectTexts(String cat) {
        return cmsService.selectSimpleText(cat);
    }

    public SimpleText selectText(String cat, String text) {
        return cmsService.selectSimpleText(cat, text);
    }

    private static CMSService cmsService = CMSService.get();

    public static TextsStore build() {
        return new TextsStore();
    }
}
