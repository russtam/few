package few.routing;

import java.util.*;
import java.util.regex.MatchResult;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by IntelliJ IDEA.
 * User: igor
 * Date: 20.01.12
 * Time: 22:44
 * To change this template use File | Settings | File Templates.
 */
public class RouteProcessor {

    public static class PatternWithVars {
        private Pattern pattern;
        private String[] vars;

        public PatternWithVars(Pattern pattern, String[] vars) {
            this.pattern = pattern;
            this.vars = vars;
        }

        public Pattern getPattern() {
            return pattern;
        }

        public String[] getVars() {
            return vars;
        }
    }

    public static PatternWithVars buildPattern(String pattern) {
        ArrayList<String> vars = new ArrayList<String>();
        StringBuilder sb = new StringBuilder();
        sb.append("^");

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
        sb.append(".*");

        Pattern pt = Pattern.compile(sb.toString());

        return new PatternWithVars(pt, vars.toArray(new String[vars.size()]));
    }

    public static Map<String, String> processUri(String uri, String pattern) {
        PatternWithVars pv = buildPattern(pattern);

        return processUri(uri, pv.getPattern(), pv.getVars());
    }

    public static Map<String, String> processUri(String uri, Pattern pattern, String[] vars) {

        Matcher m = pattern.matcher(uri);
        if( m.matches() ) {
            MatchResult mr = m.toMatchResult();

            Map<String, String> map = new HashMap<String, String>();
            for (int k = 0; k < vars.length; k++)
                map.put(vars[k], mr.group(k+1));

            return Collections.unmodifiableMap(map);
        }
        else
            return null;
    }

    public static String processVars(String pattern, Map<String, String> vars) {
        StringBuilder sb = new StringBuilder();

        int i = 0, j = 0;
        while(true) {
            i = pattern.indexOf("${", j);
            if( i != -1 ) {
                sb.append(pattern.substring(j, i));
                j = pattern.indexOf("}", i);
                String var = pattern.substring(i + 2, j);
                sb.append(vars.get(var));
                j += 1;
            } else {
                sb.append(pattern.substring(j, pattern.length()));
                break;
            }
        }
        return sb.toString();
    }
}
