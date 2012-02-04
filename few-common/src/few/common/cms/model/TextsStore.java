package few.common.cms.model;

import few.ModelBean;
import few.common.cms.dto.SimpleText;
import few.common.cms.dao.CMSService;

import java.util.List;

/**
 * User: igor
 * Date: 07.01.12
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
