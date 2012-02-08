package few.annotations;

import few.Controller;
import junit.framework.Assert;
import junit.framework.TestCase;

import javax.servlet.*;
import javax.servlet.descriptor.JspConfigDescriptor;
import java.io.File;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.*;

/**
 * User: gerbylev
 * Date: 30.11.11
 */
public class AnnotationFinderTest extends TestCase {

    @SuppressWarnings("deprecation")
    public static class SC implements ServletContext {

        String classesFolder;
        String libFolder;

        public SC(String classesFolder, String libFolder) {
            this.classesFolder = classesFolder;
            this.libFolder = libFolder;
        }

        public ServletContext getContext(String uripath) {
            return null;  
        }

        public String getContextPath() {
            return null;  
        }

        public int getMajorVersion() {
            return 0;  
        }

        public int getMinorVersion() {
            return 0;  
        }

        public int getEffectiveMajorVersion() {
            return 0;
        }

        public int getEffectiveMinorVersion() {
            return 0;
        }

        public String getMimeType(String file) {
            return null;  
        }

        public Set getResourcePaths(String path) {
            return null;  
        }

        public URL getResource(String path) throws MalformedURLException {
            if( path.endsWith("/WEB-INF/classes"))
                return new File(classesFolder).toURI().toURL();
            if( path.endsWith("/WEB-INF/lib"))
                return new File(libFolder).toURI().toURL();
            return null;
        }

        public InputStream getResourceAsStream(String path) {
            return null;  
        }

        public RequestDispatcher getRequestDispatcher(String path) {
            return null;  
        }

        public RequestDispatcher getNamedDispatcher(String name) {
            return null;  
        }

        public Servlet getServlet(String name) throws ServletException {
            return null;  
        }

        public Enumeration getServlets() {
            return null;  
        }

        public Enumeration getServletNames() {
            return null;  
        }

        public void log(String msg) {
            
        }

        public void log(Exception exception, String msg) {
            
        }

        public void log(String message, Throwable throwable) {
            
        }

        public String getRealPath(String path) {
            return null;  
        }

        public String getServerInfo() {
            return null;  
        }

        public String getInitParameter(String name) {
            return null;  
        }

        public Enumeration getInitParameterNames() {
            return null;  
        }

        public boolean setInitParameter(String s, String s1) {
            return false;
        }

        public Object getAttribute(String name) {
            return null;  
        }

        public Enumeration getAttributeNames() {
            return null;  
        }

        public void setAttribute(String name, Object object) {
            
        }

        public void removeAttribute(String name) {
            
        }

        public String getServletContextName() {
            return null;  
        }

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

    public void _test1() throws MalformedURLException {
        few.services.AnnotationFinder af = new AnnotationFinderImpl(
                new SC("D:\\workSpace\\DRGN\\out\\production\\public-web", "D:\\workSpace\\DRGN\\lib\\testng-6.1.1"),
                Controller.class.getClassLoader());

        Map<Class, List<Class>> ans =
                af.findAnnotations();

        Assert.assertTrue( ans.containsKey(Controller.class) );

    }

}
