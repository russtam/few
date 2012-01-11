package few.common.cms.controller;

import few.*;
import few.common.cms.persistence.SimpleText;
import few.common.cms.service.CMSService;

/**
 * Created by IntelliJ IDEA.
 * User: igor
 * Date: 07.01.12
 * Time: 14:30
 * To change this template use File | Settings | File Templates.
 */
@ActionClass(action = "simple_text_admin")
public class SimpleTextController {

    @ActionMethod( _default = true)
    public void render() {
    }

    CMSService cmsService = CMSService.get();
    @ActionMethod
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

        String refer = Context.get().getRequest().getHeader("referer");
        return ActionResponse.redirect(refer);
    }

    @ActionMethod
    public ActionResponse delete(
            @RequestParameter(name = "delete") String action,
            @RequestParameter(name = "cat_id") String cat_id,
            @RequestParameter(name = "text_id") String text_id
    ) {
        cmsService.deleteSimpleText(cat_id, text_id);

        String refer = Context.get().getRequest().getHeader("referer");
        return ActionResponse.redirect(refer);
    }
}
