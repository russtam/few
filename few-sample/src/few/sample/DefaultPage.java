package few.sample;

import few.ActionClass;
import few.ActionMethod;
import few.ActionResponse;

/**
 * Created by IntelliJ IDEA.
 * User: igor
 * Date: 24.12.11
 * Time: 0:13
 * To change this template use File | Settings | File Templates.
 */
@ActionClass(action = "")
public class DefaultPage {

    @ActionMethod(_default = true)
    public ActionResponse _default() {
        return ActionResponse.redirect("login");
    }

}
