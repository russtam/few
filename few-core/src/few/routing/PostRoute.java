package few.routing;

import java.util.Collections;
import java.util.Map;

/**
* Created by IntelliJ IDEA.
* User: igor
* Date: 20.01.12
* Time: 19:11
* To change this template use File | Settings | File Templates.
*/
public class PostRoute extends Route {
    private String controller;
    private String action;
    private Map<String, String> remapping;

    public PostRoute(String urlPatern, String permission, String controller, String action, Map<String, String> remapping) {
        super("POST", urlPatern, permission);
        this.controller = controller;
        this.action = action;
        this.remapping = Collections.unmodifiableMap(remapping);
    }

    public String getController() {
        return controller;
    }

    public String getAction() {
        return action;
    }

    public Map<String, String> getRemapping() {
        return remapping;
    }
}
