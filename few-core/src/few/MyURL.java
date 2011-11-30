package few;

import few.needed.Configuration;
import few.needed.OuterFactory;
import few.utils.Utils;

import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: gerbylev
 * Date: 31.10.11
 * Time: 23:22
 * To change this template use File | Settings | File Templates.
 */
public class MyURL {
    public static final String WEB_SERVER_HOST =
            OuterFactory.get().getConfiguration().getString(Configuration.WEB_SERVER_HOST);
    public static final int WEB_SERVER_HTTP_PORT =
            OuterFactory.get().getConfiguration().getInteger(Configuration.WEB_SERVER_HTTP_PORT);
    public static final int WEB_SERVER_HTTPS_PORT =
            OuterFactory.get().getConfiguration().getInteger(Configuration.WEB_SERVER_HTTPS_PORT);

    boolean secure;
    String page;
    List<Parameter> params = new LinkedList<Parameter>();

    private static class Parameter {
        String name;
        String value;

        private Parameter(String name, String value) {
            this.name = name;
            this.value = value;
        }
    }

    public MyURL(String page) {
        this.secure = false;
        this.page = page;
    }

    public MyURL(boolean secure, String page) {
        this.secure = secure;
        this.page = page;
    }

    public String toString() {
        HttpServletRequest request = Context.get().getRequest();

        StringBuilder stringBuilder = new StringBuilder();

        if( !secure )
            stringBuilder.append("http://");
        else
            stringBuilder.append("https://");

        if( Utils.isNotNull(WEB_SERVER_HOST) )
            stringBuilder.append(WEB_SERVER_HOST);
        else
            stringBuilder.append(request.getServerName());

        if( !secure && WEB_SERVER_HTTP_PORT != 0 ) {
            stringBuilder.append(":");
            stringBuilder.append(WEB_SERVER_HTTP_PORT);
        } else
            if( secure && WEB_SERVER_HTTPS_PORT != 0 ) {
                stringBuilder.append(":");
                stringBuilder.append(WEB_SERVER_HTTPS_PORT);
            } else
                if( request.getServerPort() != 80 ) {
                    stringBuilder.append(":");
                    stringBuilder.append(request.getServerPort());
                }
        stringBuilder.append(page);

        encodeParameters(stringBuilder);

        return stringBuilder.toString();
    }

    public MyURL p(String name, String value) {
        params.add(new Parameter(name, value));
        return this;
    }

    private void encodeParameters(StringBuilder builder) {
        if( !params.isEmpty() )
            builder.append("?");

        for (Iterator<Parameter> iterator = params.iterator(); iterator.hasNext(); ) {
            Parameter param = iterator.next();
            if( iterator.hasNext() )
                builder.append("&");

            try {
                builder.append(param.name).append("=").append(
                        URLEncoder.encode(param.value, "UTF-8")
                );
            } catch (UnsupportedEncodingException e) {
                throw new Error();
            }

        }
    }

}
