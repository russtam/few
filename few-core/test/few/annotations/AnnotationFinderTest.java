package few.annotations;

import few.ActionClass;
import junit.framework.Assert;
import junit.framework.TestCase;

import javax.servlet.RequestDispatcher;
import javax.servlet.Servlet;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import java.io.File;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.*;

/**
 * Created by IntelliJ IDEA.
 * User: gerbylev
 * Date: 30.11.11
 * Time: 17:59
 * To change this template use File | Settings | File Templates.
 */
public class AnnotationFinderTest extends TestCase {

    public static class SC implements ServletContext {

        String classesFolder;
        String libFolder;

        public SC(String classesFolder, String libFolder) {
            this.classesFolder = classesFolder;
            this.libFolder = libFolder;
        }

        public ServletContext getContext(String uripath) {
            return null;  //To change body of implemented methods use File | Settings | File Templates.
        }

        public String getContextPath() {
            return null;  //To change body of implemented methods use File | Settings | File Templates.
        }

        public int getMajorVersion() {
            return 0;  //To change body of implemented methods use File | Settings | File Templates.
        }

        public int getMinorVersion() {
            return 0;  //To change body of implemented methods use File | Settings | File Templates.
        }

        public String getMimeType(String file) {
            return null;  //To change body of implemented methods use File | Settings | File Templates.
        }

        public Set getResourcePaths(String path) {
            return null;  //To change body of implemented methods use File | Settings | File Templates.
        }

        public URL getResource(String path) throws MalformedURLException {
            if( path.endsWith("/WEB-INF/classes"))
                return new File(classesFolder).toURI().toURL();
            if( path.endsWith("/WEB-INF/lib"))
                return new File(libFolder).toURI().toURL();
            return null;
        }

        public InputStream getResourceAsStream(String path) {
            return null;  //To change body of implemented methods use File | Settings | File Templates.
        }

        public RequestDispatcher getRequestDispatcher(String path) {
            return null;  //To change body of implemented methods use File | Settings | File Templates.
        }

        public RequestDispatcher getNamedDispatcher(String name) {
            return null;  //To change body of implemented methods use File | Settings | File Templates.
        }

        public Servlet getServlet(String name) throws ServletException {
            return null;  //To change body of implemented methods use File | Settings | File Templates.
        }

        public Enumeration getServlets() {
            return null;  //To change body of implemented methods use File | Settings | File Templates.
        }

        public Enumeration getServletNames() {
            return null;  //To change body of implemented methods use File | Settings | File Templates.
        }

        public void log(String msg) {
            //To change body of implemented methods use File | Settings | File Templates.
        }

        public void log(Exception exception, String msg) {
            //To change body of implemented methods use File | Settings | File Templates.
        }

        public void log(String message, Throwable throwable) {
            //To change body of implemented methods use File | Settings | File Templates.
        }

        public String getRealPath(String path) {
            return null;  //To change body of implemented methods use File | Settings | File Templates.
        }

        public String getServerInfo() {
            return null;  //To change body of implemented methods use File | Settings | File Templates.
        }

        public String getInitParameter(String name) {
            return null;  //To change body of implemented methods use File | Settings | File Templates.
        }

        public Enumeration getInitParameterNames() {
            return null;  //To change body of implemented methods use File | Settings | File Templates.
        }

        public Object getAttribute(String name) {
            return null;  //To change body of implemented methods use File | Settings | File Templates.
        }

        public Enumeration getAttributeNames() {
            return null;  //To change body of implemented methods use File | Settings | File Templates.
        }

        public void setAttribute(String name, Object object) {
            //To change body of implemented methods use File | Settings | File Templates.
        }

        public void removeAttribute(String name) {
            //To change body of implemented methods use File | Settings | File Templates.
        }

        public String getServletContextName() {
            return null;  //To change body of implemented methods use File | Settings | File Templates.
        }
    }

    public void test1() throws MalformedURLException {
        AnnotationFinder af = new AnnotationFinder(
                new SC("D:\\workSpace\\DRGN\\out\\production\\public-web", "D:\\workSpace\\DRGN\\lib\\testng-6.1.1"),
                ActionClass.class.getClassLoader());

        Map<Class, List<Class>> ans =
                af.findAnnotations();

        Assert.assertTrue( ans.containsKey(ActionClass.class) );

    }

}