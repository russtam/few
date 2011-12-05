package few.core;

import few.ActionResponse;
import few.Context;
import few.RequestParameter;
import few.support.MultipartRequest;
import org.apache.commons.fileupload.FileItem;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by IntelliJ IDEA.
 * User: gerbylev
 * Date: 30.11.11
 * Time: 19:06
 * To change this template use File | Settings | File Templates.
 */
public class ActionInvoker {

    public static ActionResponse invokeActionMethod(DispatcherMap.ActionMethodDescription am, HttpServletRequest request, HttpServletResponse response) {
        Object instance = am.instance;
        Method m = am.method;

        Object[] args = mapParameters(m, request, response);

        try {
            if( ActionResponse.class.isAssignableFrom(m.getReturnType() ) )
                return (ActionResponse) m.invoke(instance, args);
            if( m.getReturnType().getName().equals("void") ) {
                m.invoke(instance, args);
                return ActionResponse._default();
            }
            throw new RuntimeException("bad return type in action method " + am.instance.getClass().getName() + "." + m.getName());
        } catch (Throwable t) {
            log.log(Level.SEVERE, "can not invoke action method " + am.instance.getClass().getName() + "." + m.getName(), t);
            throw new RuntimeException(t);
        }
    }

    private static Object[] mapParameters(Method method, HttpServletRequest request, HttpServletResponse response) {
        Object[] ret = new Object[method.getParameterTypes().length];

        for (int i = 0; i < method.getParameterTypes().length; i++) {
            Class<?> type = method.getParameterTypes()[i];
            Annotation[] annotations = method.getParameterAnnotations()[i];

            if( type.isAssignableFrom(request.getClass()) ) {
                ret[i] = request;
            } else
            if( type.isAssignableFrom(response.getClass()) ) {
                ret[i] = response;
            } else
            if( type.isAssignableFrom(request.getSession().getClass()) ) {
                ret[i] = request.getSession();
            } else
            if( type.isAssignableFrom(request.getSession().getServletContext().getClass()) ) {
                ret[i] = request.getSession().getServletContext();
            } else
            if( annotations.length > 0 && annotations[0].annotationType() == RequestParameter.class ) {
                RequestParameter param = (RequestParameter) annotations[0];

                if( type == FileItem.class ) { // it means file upload
                    MultipartRequest mpRequest = (MultipartRequest) Context.get().getRequest();
                    ret[i] = mpRequest.getFileItem(param.name());
                    continue;
                }

                String value = request.getParameter(param.name());
                if( value == null && param.required() ) {
                    throw new IllegalArgumentException("no required parameter '" + param.name() + "' in request");
                } if( value != null ) {
                    ret[i] = valueOf(value, type);
                }

            }
        }

        return ret;
    }

    private static Map<Class, Method> valueOfMethods = new HashMap<Class, Method>();
    private static Object valueOf(String str, Class type) {
        if( type == String.class )
            return str;

        if( !valueOfMethods.containsKey(type) ) {
            try {
                Method m = type.getMethod("valueOf", String.class);
                valueOfMethods.put(type, m);
            } catch (NoSuchMethodException e) {
            }
        }

        Method m = valueOfMethods.get(type);
        if( m != null ) {
            try {
                return m.invoke(null, str);
            } catch (IllegalAccessException e) {
                log.log(Level.SEVERE, "", e);
                throw new IllegalArgumentException("can not convert value '" + str + "' to type " + type.getName(), e);
            } catch (InvocationTargetException e) {
                log.log(Level.SEVERE, "can not convert value '" + str + "' to type " + type.getName());
                throw new IllegalArgumentException("can not convert value '" + str + "' to type " + type.getName(), e.getTargetException());
            }
        } else {
            log.log(Level.SEVERE, "no method to convert String into type " + type.getName());
            throw new IllegalArgumentException("can not convert value '" + str + "' to type " + type.getName());
        }
    }

    static Logger log = Logger.getLogger(ActionInvoker.class.getName());
}