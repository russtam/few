package few.core;

import few.ActionResponse;
import few.Context;
import few.RequestParameter;
import few.RequestParameters;
import few.support.MultipartRequest;
import org.apache.commons.fileupload.FileItem;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.HashSet;
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

    public static Object invokeModelMethod(DispatcherMap.ModelBeanDescription desc, HttpServletRequest request, HttpServletResponse response) {
        try {
            Method m = desc.method;

            Object[] args = mapParameters(m, request, response);

            Object ret = desc.method.invoke(null, args);

            return ret;
        } catch (Throwable e) {
            if( e.getCause() != null )
                throw new RuntimeException("can not create ModelBean " + desc.name, e.getCause());
            else
                throw new RuntimeException("can not create ModelBean " + desc.name, e);
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
                    throw new IllegalArgumentException("no required parameter '" + param.name() + "' in request, class - " + method.getDeclaringClass().getName());
                } if( value != null ) {
                    if( type == Boolean.class && (value.equals("on") || value.equals("off")) ) {
                        ret[i] = value.equals("on");
                    }
                    else {
                        if( !type.isArray() )
                            ret[i] = valueOf(value, type);
                        else {
                            String[] values = request.getParameterValues(param.name());
                            Object[] o = new Object[values.length];
                            for (int j = 0; j < values.length; j++) {
                                o[j] = valueOf(values[j], type.getComponentType());
                            }
                            ret[i] = o;
                        }
                    }
                }
            } else
            if( annotations.length > 0 && annotations[0].annotationType() == RequestParameters.class ) {
                if( type != Map.class ) {
                    throw new IllegalArgumentException("a type of action parameter annotated with RequestParameters should be Map<String, String>");
                }
                // 1. remember declared parameters
                HashSet<String> declared = new HashSet<String>();
                for (Annotation[] an : method.getParameterAnnotations()) {
                    if( an.length == 1 && an[0].annotationType() == RequestParameter.class) {
                        RequestParameter rp = (RequestParameter) an[0];
                        declared.add(rp.name());
                    }
                }

                // 2. fill Map
                HashMap<String, String> map = new HashMap<String, String>();
                for (Object o : request.getParameterMap().entrySet()) {
                    Map.Entry<String, String[]> entry = (Map.Entry<String, String[]>) o;
                    if( !declared.contains(entry.getKey())) {
                        if( entry.getValue().length > 0)
                            map.put(entry.getKey(), entry.getValue()[0]);
                    }
                }
                ret[i] = map;
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
