package few.routing;

import few.core.ServiceRegistry;
import few.services.Routing;

import java.util.*;

/**
* User: igor
* Date: 20.01.12
*/
public class RouteBuilder {

    private static Routing routing = ServiceRegistry.get(Routing.class);

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

    public static ErrorRouteBuilder errorRoute(int code) {
        ErrorRouteBuilder ret = new ErrorRouteBuilder();
        ret.code = code;
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
            routing.addGetRoute(ret);
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
            this.remapping = new HashMap<String, String>();
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

        public PostRouteBuilder map(String response, String map) {
            this.remapping.put(response, map);
            return this;
        }

        public PostRouteBuilder map(Map<String, String> remapping) {
            this.remapping.putAll(remapping);
            return this;
        }

        public PostRoute build() {
            PostRoute ret = new PostRoute(urlPattern, permission, controller, action, remapping == null ? Collections.<String, String>emptyMap() : remapping);
            routing.addPostRoute(ret);
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
            if( !(ftl != null ^ servlet != null) )
                throw new IllegalStateException("only one of ftl or servlet should be null");

            ErrorRoute ret = new ErrorRoute(code, ftl, servlet);
            routing.addErrorRoute(ret);
            return ret;
        }
    }


}
