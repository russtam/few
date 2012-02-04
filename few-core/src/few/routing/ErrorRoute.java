package few.routing;

/**
* User: igor
* Date: 20.01.12
*/
public class ErrorRoute {
    private int code;
    private String ftl;
    private String servlet;

    ErrorRoute(int code, String ftl, String servlet) {
        this.code = code;
        this.ftl = ftl;
        this.servlet = servlet;
    }

    public int getCode() {
        return code;
    }

    public String getFtl() {
        return ftl;
    }

    public String getServlet() {
        return servlet;
    }


}
