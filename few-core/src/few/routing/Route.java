package few.routing;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
* Created by IntelliJ IDEA.
* User: igor
* Date: 20.01.12
* Time: 19:10
* To change this template use File | Settings | File Templates.
*/
public class Route {
    private String method;
    private String urlPattern;
    private String permission;

    private Pattern pattern;
    private String[] vars;

    Route(String method, String urlPattern, String permission) {
        this.method = method;
        this.urlPattern = urlPattern;
        this.permission = permission;

        RouteProcessor.PatternWithVars pv = RouteProcessor.buildPattern(urlPattern);
        pattern = pv.getPattern();
        vars = pv.getVars();
    }

    public String getMethod() {
        return method;
    }

    public String getUrlPattern() {
        return urlPattern;
    }

    public String getPermission() {
        return permission;
    }

    public Pattern getPattern() {
        return pattern;
    }

    public String[] getVars() {
        return vars;
    }
}
