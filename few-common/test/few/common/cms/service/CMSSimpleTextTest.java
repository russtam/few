package few.common.cms.service;

import few.common.BaseTest;
import few.common.cms.persistence.SimpleText;

import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: igor
 * Date: 07.01.12
 * Time: 13:12
 * To change this template use File | Settings | File Templates.
 */
public class CMSSimpleTextTest extends BaseTest {

    CMSService cmsService = CMSService.get();

    public static final String CAT = "test";
    public static final String NAME = "name";

    public void testCRUD() {
        SimpleText s = new SimpleText(CAT, NAME, "testCRUD");

        cmsService.insetSimpleText(s);

        List<SimpleText> list = cmsService.selectSimpleText(CAT);
        assertTrue(list.contains(s));

        SimpleText s2 = cmsService.selectSimpleText(CAT, NAME);
        assertEquals(s, s2);
        assertEquals(s.text, s2.text);

        s2.text = "1111";
        cmsService.updateSimpleText(s2);

        SimpleText s3 = cmsService.selectSimpleText(CAT, NAME);
        assertEquals(s2, s3);
        assertEquals(s2.text, s3.text);

        cmsService.deleteSimpleText(CAT, NAME);

        list = cmsService.selectSimpleText(CAT);
        assertFalse(list.contains(s));
    }

    @Override
    protected void tearDown() throws Exception {
        List<SimpleText> list = cmsService.selectSimpleText(CAT);
        for (SimpleText simpleText : list) {
            cmsService.deleteSimpleText(simpleText.cat_id, simpleText.text_id);
        }
    }
}
