package few.routing;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
* Created by IntelliJ IDEA.
* User: igor
* Date: 20.01.12
* Time: 19:11
* To change this template use File | Settings | File Templates.
*/
public class RouteBuilder {

    static List<GetRoute> getRoutes = new LinkedList<GetRoute>();
    static List<PostRoute> postRoutes = new LinkedList<PostRoute>();
    static List<ErrorRoute> errorRoutes = new LinkedList<ErrorRoute>();


    public static GetRouteBuilder getRoute(String urlPattern) {
        GetRouteBuilder ret = new GetRouteBuilder();
        ret.urlPattern = urlPattern;
        return ret;
    }

    public static PostRouteBuilder postRoute(String urlPattern) {
        PostRouteBuilder ret = new PostRouteBuilder();
        ret.urlPattern = urlPattern;
        return ret;
    }


    public static class GetRouteBuilder {
        private String urlPattern;
        private String permission;

        private String ftl;
        private String servlet;

        private GetRouteBuilder() {
        }

        public GetRouteBuilder permission(String permission) {
            this.permission = permission;
            return this;
        }

        public GetRouteBuilder toPage(String ftl) {
            if( servlet != null )
                throw new IllegalStateException();
            this.ftl = ftl;
            return this;
        }

        public GetRouteBuilder toServlet(String servlet) {
            if( ftl != null )
                throw new IllegalStateException();
            this.servlet = servlet;
            return this;
        }

        public GetRoute build() {
            if( !(ftl != null ^ servlet != null) )
                throw new IllegalStateException("only one of ftl or servlet should be null");

            GetRoute ret = new GetRoute(urlPattern, permission, ftl, servlet);
            getRoutes.add(ret);
            return ret;
        }
    }

    public static class PostRouteBuilder {
        private String urlPattern;
        private String[] urlParameters;
        private String permission;

        private String controller;
        private String action;
        private Map<String, String> remapping;

        private PostRouteBuilder() {
        }

        public PostRouteBuilder permission(String permission) {
            this.permission = permission;
            return this;
        }

        public PostRouteBuilder urlParameters(String[] urlParameters) {
            this.urlParameters = urlParameters;
            return this;
        }

        public PostRouteBuilder ctrl(Class clazz) {
            controller = clazz.getName();
            return this;
        }
        public PostRouteBuilder ctrl(String controller) {
            this.controller = controller;
            return this;
        }
        public PostRouteBuilder action(String action) {
            this.action = action;
            return this;
        }

        public PostRouteBuilder action(Map<String, String> remapping) {
            this.remapping.putAll(remapping);
            return this;
        }

        public PostRoute build() {
            PostRoute ret = new PostRoute(urlPattern, permission, controller, action, remapping == null ? Collections.<String, String>emptyMap() : remapping);
            postRoutes.add(ret);
            return ret;
        }
    }

    public static class ErrorRouteBuilder {
        private int code;
        private String ftl;
        private String servlet;

        private ErrorRouteBuilder() {
        }

        public ErrorRouteBuilder toPage(String ftl) {
            if( servlet != null )
                throw new IllegalStateException();
            this.ftl = ftl;
            return this;
        }

        public ErrorRouteBuilder toServlet(String servlet) {
            if( ftl != null )
                throw new IllegalStateException();
            this.servlet = servlet;
            return this;
        }

        public ErrorRoute build() {
            if( ftl != null ^ servlet != null )
                throw new IllegalStateException("only one of ftl or servlet should be null");

            ErrorRoute ret = new ErrorRoute(code, ftl, servlet);
            errorRoutes.add(ret);
            return ret;
        }
    }


}
