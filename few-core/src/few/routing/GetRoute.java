package few.routing;

/**
* Created by IntelliJ IDEA.
* User: igor
* Date: 20.01.12
* Time: 19:11
* To change this template use File | Settings | File Templates.
*/
public class GetRoute extends Route{
    private String ftl; // может быть маска
    private String servlet; // тоже может быть маской


    GetRoute(String urlPatern, String permission, String ftl, String servlet) {
        super("GET", urlPatern, permission);
        this.ftl = ftl;
        this.servlet = servlet;
    }

    public String getFtl() {
        return ftl;
    }

    public String getServlet() {
        return servlet;
    }


}
