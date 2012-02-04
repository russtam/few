package few.routing;

import java.util.ArrayList;
import java.util.regex.Pattern;

/**
* User: igor
* Date: 20.01.12
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

        buildPattern(urlPattern);
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

    private void buildPattern(String pattern) {
        ArrayList<String> vars = new ArrayList<String>();
        StringBuilder sb = new StringBuilder();
        sb.append("^");

        pattern = pattern.replace(".", "\\.");
        pattern = pattern.replace("*", ".*");

        int i = 0, j = 0;
        while(true) {
            i = pattern.indexOf("${", j);
            if( i != -1 ) {
                sb.append(pattern.substring(j, i));
                sb.append("(.+)");

                j = pattern.indexOf("}", i);
                String var = pattern.substring(i + 2, j);
                vars.add(var);
                j += 1;
            } else {
                sb.append(pattern.substring(j, pattern.length()));
                break;
            }
        }
        //sb.append(".*");

        this.pattern = Pattern.compile(sb.toString());
        this.vars = vars.toArray(new String[vars.size()]);
    }

}
