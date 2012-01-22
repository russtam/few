package few.sample;

import few.Controller;
import few.Action;
import few.ActionResponse;

/**
 * Created by IntelliJ IDEA.
 * User: igor
 * Date: 24.12.11
 * Time: 0:13
 * To change this template use File | Settings | File Templates.
 */
@Controller(name = "")
public class DefaultPage {

    @Action(_default = true)
    public ActionResponse _default() {
        return ActionResponse.redirect("login");
    }

}
