package few.common.cms.controller;

import few.*;
import few.common.cms.dto.SimpleText;
import few.common.cms.dao.CMSService;

/**
 * Created by IntelliJ IDEA.
 * User: igor
 * Date: 07.01.12
 * Time: 14:30
 * To change this template use File | Settings | File Templates.
 */
@Controller(name = "simple_text_admin", permission = "cms-admin")
public class SimpleTextController {

    CMSService cmsService = CMSService.get();
    @Action
    public ActionResponse save(
            @RequestParameter(name = "save") String action,
            @RequestParameter(name = "cat_id") String cat_id,
            @RequestParameter(name = "text_id") String text_id,
            @RequestParameter(name = "text") String text
    ) {
        SimpleText s = new SimpleText(cat_id, text_id, text);
        if( cmsService.selectSimpleText(cat_id, text_id) == null )
            cmsService.insetSimpleText(s);
        else
            cmsService.updateSimpleText(s);

        return ActionResponse.referer();
    }

    @Action
    public ActionResponse delete(
            @RequestParameter(name = "delete") String action,
            @RequestParameter(name = "cat_id") String cat_id,
            @RequestParameter(name = "text_id") String text_id
    ) {
        cmsService.deleteSimpleText(cat_id, text_id);

        return ActionResponse.referer();
    }
}
