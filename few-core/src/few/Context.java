package few;

import few.core.DispatcherMap;
import few.core.LazyDataModel;
import few.needed.Credentials;
import few.needed.OuterFactory;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.*;

/**
 * Created by IntelliJ IDEA.
 * User: gerbylev
 * Date: 02.11.11
 * Time: 21:29
 * To change this template use File | Settings | File Templates.
 */
public class Context {

    protected static ThreadLocal<Context> threadLocal = new ThreadLocal<Context>();

    static public void init(HttpServletRequest request, HttpServletResponse response, ServletContext servletContext, DispatcherMap config) {
        threadLocal.set(new Context(request, response, servletContext, config));
    }

    static public Context get() {
        return threadLocal.get();
    }

    static public void fini() {
        threadLocal.set(null);
    }

    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++
    private HttpServletRequest request;
    private HttpServletResponse response;
    private ServletContext servletContext;
    private Map<String, List<Message>> messages;
    private LazyDataModel model;

    private Credentials credentials = OuterFactory.get().getCredentials();

    private Context(HttpServletRequest request, HttpServletResponse response, ServletContext servletContext, DispatcherMap config) {
        this.request = request;
        this.response = response;
        this.servletContext = servletContext;
        this.messages = new HashMap<String, List<Message>>();
        this.model = new LazyDataModel(config);
    }

    public LazyDataModel getModel() {
        return model;
    }

    public void addMessage(Message message) {
        List<Message> list = messages.get(message.getSource());
        if( list == null ) {
            list = new LinkedList<Message>();
            messages.put(message.getSource(), list);
        }
        list.add(message);
    }

    public boolean isSignedIn() {
        return credentials.isSignedIn(request);
    }

    public String getUserID() {
        return credentials.getUserID(request);
    }

    private Set<String> userRoles;
    public boolean isUserInRole(String role) {
        if( userRoles == null ) {
            userRoles = credentials.getRoles(request);
        }

        if( userRoles.contains(role) )
            return true;
        else
            return false;
    }

    public ServletContext getServletContext() {
        return servletContext;
    }

    public HttpServletRequest getRequest() {
        return request;
    }

    public Map<String, List<Message>> getMessages() {
        return Collections.unmodifiableMap(messages);
    }
}
