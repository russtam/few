package few.common;

import few.Context;
import few.core.DispatcherMap;

import javax.servlet.*;
import javax.servlet.descriptor.JspConfigDescriptor;
import javax.servlet.http.*;
import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.security.Principal;
import java.util.*;

/**
 * User: igor
 * Date: 07.01.12
 */
@SuppressWarnings(value = "deprecation")
public class TestContext extends Context{

    public static void set() {
        threadLocal.set(new TestContext());
    }
    public static void clear() {
        threadLocal.set(null);
    }

    private TestContext() {
        super(
                new TestHttpServletRequest() ,
                new TestHttpServletResponse() ,
                new TestServletContext() ,
                new DispatcherMap());
    }

    @SuppressWarnings(value = "deprecation")
    public static class TestHttpServletRequest implements HttpServletRequest {
        TestHttpSession session = new TestHttpSession();

        public String getAuthType() {return null;}
        public Cookie[] getCookies() {return new Cookie[0];}
        public long getDateHeader(String name) {return 0;}
        public String getHeader(String name) {return null;}
        public Enumeration getHeaders(String name) {return null;}
        public Enumeration getHeaderNames() {return null;}
        public int getIntHeader(String name) {return 0;}
        public String getMethod() {
            return "GET";
        }
        public String getPathInfo() {return null;}
        public String getPathTranslated() {return null;}
        public String getContextPath() {return null;}
        public String getQueryString() {return null;}
        public String getRemoteUser() {return null;}
        public boolean isUserInRole(String role) {return false;}
        public Principal getUserPrincipal() {return null;}
        public String getRequestedSessionId() {return null;}
        public String getRequestURI() {return null;}
        public StringBuffer getRequestURL() {return null;}
        public String getServletPath() {return null;}
        public HttpSession getSession(boolean create) {return session;}
        public HttpSession getSession() {return session;}
        public boolean isRequestedSessionIdValid() {return false;}
        public boolean isRequestedSessionIdFromCookie() {return false;}
        public boolean isRequestedSessionIdFromURL() {return false;}
        public boolean isRequestedSessionIdFromUrl() {return false;}

        public boolean authenticate(HttpServletResponse response) throws IOException, ServletException {
            return false;
        }

        public void login(String s, String s1) throws ServletException {

        }

        public void logout() throws ServletException {

        }

        public Collection<Part> getParts() throws IOException, IllegalStateException, ServletException {
            return null;
        }

        public Part getPart(String s) throws IOException, IllegalStateException, ServletException {
            return null;
        }

        public Object getAttribute(String name) {return null;}
        public Enumeration getAttributeNames() {return null;}
        public String getCharacterEncoding() {return null;}
        public void setCharacterEncoding(String env) throws UnsupportedEncodingException {}
        public int getContentLength() {return 0;}
        public String getContentType() {return null;}
        public ServletInputStream getInputStream() throws IOException {return null;}
        public String getParameter(String name) {return null;}
        public Enumeration getParameterNames() {return null;}
        public String[] getParameterValues(String name) {return new String[0];}
        public Map getParameterMap() {return null;}
        public String getProtocol() {return null;}
        public String getScheme() {return null;}
        public String getServerName() {return null;}
        public int getServerPort() {return 0;}
        public BufferedReader getReader() throws IOException {return null;}
        public String getRemoteAddr() {return null;}
        public String getRemoteHost() {return null;}
        public void setAttribute(String name, Object o) {}
        public void removeAttribute(String name) {}
        public Locale getLocale() {return null;}
        public Enumeration getLocales() {return null;}
        public boolean isSecure() {return false;}
        public RequestDispatcher getRequestDispatcher(String path) {return null;}
        public String getRealPath(String path) {return null;}
        public int getRemotePort() {return 0;}
        public String getLocalName() {return null;}
        public String getLocalAddr() {return null;}
        public int getLocalPort() {return 0;}

        public ServletContext getServletContext() {
            return null;
        }

        public AsyncContext startAsync() {
            return null;
        }

        public AsyncContext startAsync(ServletRequest servletRequest, ServletResponse servletResponse) {
            return null;
        }

        public boolean isAsyncStarted() {
            return false;
        }

        public boolean isAsyncSupported() {
            return false;
        }

        public AsyncContext getAsyncContext() {
            return null;
        }

        public DispatcherType getDispatcherType() {
            return null;
        }
    }

    @SuppressWarnings(value = "deprecation")
    public static class TestHttpServletResponse implements HttpServletResponse{

        public void addCookie(Cookie cookie) {}
        public boolean containsHeader(String name) {return false;}
        public String encodeURL(String url) {return null;}
        public String encodeRedirectURL(String url) {return null;}
        public String encodeUrl(String url) {return null;}
        public String encodeRedirectUrl(String url) {return null;}
        public void sendError(int sc, String msg) throws IOException {}
        public void sendError(int sc) throws IOException {}
        public void sendRedirect(String location) throws IOException {}
        public void setDateHeader(String name, long date) {}
        public void addDateHeader(String name, long date) {}
        public void setHeader(String name, String value) {}
        public void addHeader(String name, String value) {}
        public void setIntHeader(String name, int value) {}
        public void addIntHeader(String name, int value) {}
        public void setStatus(int sc) {}
        public void setStatus(int sc, String sm) {}

        public int getStatus() {
            return 0;
        }

        public String getHeader(String s) {
            return null;
        }

        public Collection<String> getHeaders(String s) {
            return null;
        }

        public Collection<String> getHeaderNames() {
            return null;
        }

        public String getCharacterEncoding() {return null;}
        public String getContentType() {return null;}
        public ServletOutputStream getOutputStream() throws IOException {return null;}
        public PrintWriter getWriter() throws IOException {return null;}
        public void setCharacterEncoding(String charset) {}
        public void setContentLength(int len) {}
        public void setContentType(String type) {}
        public void setBufferSize(int size) {}
        public int getBufferSize() {return 0;}
        public void flushBuffer() throws IOException {}
        public void resetBuffer() {}
        public boolean isCommitted() {return false;}
        public void reset() {}
        public void setLocale(Locale loc) {}
        public Locale getLocale() {return null;}
    }

    @SuppressWarnings(value = "deprecation")
    public static class TestServletContext implements ServletContext {
        public ServletContext getContext(String uripath) {return null;}
        public String getContextPath() {return null;}
        public int getMajorVersion() {return 0;}
        public int getMinorVersion() {return 0;}

        public int getEffectiveMajorVersion() {
            return 0;
        }

        public int getEffectiveMinorVersion() {
            return 0;
        }

        public String getMimeType(String file) {return null;}
        public Set getResourcePaths(String path) {return null;}
        public URL getResource(String path) throws MalformedURLException {return null;}
        public InputStream getResourceAsStream(String path) {return null;}
        public RequestDispatcher getRequestDispatcher(String path) {return null;}
        public RequestDispatcher getNamedDispatcher(String name) {return null;}
        public Servlet getServlet(String name) throws ServletException {return null;}
        public Enumeration getServlets() {return null;}
        public Enumeration getServletNames() {return null;}
        public void log(String msg) {}
        public void log(Exception exception, String msg) {}
        public void log(String message, Throwable throwable) {}
        public String getRealPath(String path) {return null;}
        public String getServerInfo() {return null;}
        public String getInitParameter(String name) {return null;}
        public Enumeration getInitParameterNames() {return null;}

        public boolean setInitParameter(String s, String s1) {
            return false;
        }

        public Object getAttribute(String name) {return null;}
        public Enumeration getAttributeNames() {return null;}
        public void setAttribute(String name, Object object) {}
        public void removeAttribute(String name) {}
        public String getServletContextName() {return null;}

        public ServletRegistration.Dynamic addServlet(String s, String s1) {
            return null;
        }

        public ServletRegistration.Dynamic addServlet(String s, Servlet servlet) {
            return null;
        }

        public ServletRegistration.Dynamic addServlet(String s, Class<? extends Servlet> aClass) {
            return null;
        }

        public <T extends Servlet> T createServlet(Class<T> tClass) throws ServletException {
            return null;
        }

        public ServletRegistration getServletRegistration(String s) {
            return null;
        }

        public Map<String, ? extends ServletRegistration> getServletRegistrations() {
            return null;
        }

        public FilterRegistration.Dynamic addFilter(String s, String s1) {
            return null;
        }

        public FilterRegistration.Dynamic addFilter(String s, Filter filter) {
            return null;
        }

        public FilterRegistration.Dynamic addFilter(String s, Class<? extends Filter> aClass) {
            return null;
        }

        public <T extends Filter> T createFilter(Class<T> tClass) throws ServletException {
            return null;
        }

        public FilterRegistration getFilterRegistration(String s) {
            return null;
        }

        public Map<String, ? extends FilterRegistration> getFilterRegistrations() {
            return null;
        }

        public SessionCookieConfig getSessionCookieConfig() {
            return null;
        }

        public void setSessionTrackingModes(Set<SessionTrackingMode> sessionTrackingModes) throws IllegalStateException, IllegalArgumentException {

        }

        public Set<SessionTrackingMode> getDefaultSessionTrackingModes() {
            return null;
        }

        public Set<SessionTrackingMode> getEffectiveSessionTrackingModes() {
            return null;
        }

        public void addListener(Class<? extends EventListener> aClass) {

        }

        public void addListener(String s) {

        }

        public <T extends EventListener> void addListener(T t) {

        }

        public <T extends EventListener> T createListener(Class<T> tClass) throws ServletException {
            return null;
        }

        public void declareRoles(String... strings) {

        }

        public ClassLoader getClassLoader() {
            return null;
        }

        public JspConfigDescriptor getJspConfigDescriptor() {
            return null;
        }
    }

    @SuppressWarnings(value = "deprecation")
    public static class TestHttpSession implements HttpSession {
        public long getCreationTime() {return 0;}
        public String getId() {return null;}
        public long getLastAccessedTime() {return 0;}
        public ServletContext getServletContext() {return null;}
        public void setMaxInactiveInterval(int interval) {}
        public int getMaxInactiveInterval() {return 0;}
        @SuppressWarnings(value = "deprecation")
        public HttpSessionContext getSessionContext() {return null;}
        public Object getAttribute(String name) {return null;}
        public Object getValue(String name) {return null;}
        public Enumeration getAttributeNames() {return null;}
        public String[] getValueNames() {return new String[0];}
        public void setAttribute(String name, Object value) {}
        public void putValue(String name, Object value) {}
        public void removeAttribute(String name) {}
        public void removeValue(String name) {}
        public void invalidate() {}
        public boolean isNew() { return false; }
    }
}
