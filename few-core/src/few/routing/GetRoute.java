package few.routing;

/**
* User: igor
* Date: 20.01.12
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
