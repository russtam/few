package few.core;

import few.*;
import few.annotations.AnnotationFinder;

import javax.servlet.ServletContext;
import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.*;
import java.util.logging.Logger;

/**
 * Created by IntelliJ IDEA.
 * User: gerbylev
 * Date: 05.11.11
 * Time: 12:12
 * To change this template use File | Settings | File Templates.
 */
public class DispatcherMap {

    public static class ControllerDesc {
        String name;
        String permission;
        Object instance;
        List<ActionDescription> methods = new LinkedList<ActionDescription>();

        public String getName() {
            return name;
        }

        public String getPermission() {
            return permission;
        }

        public Object getInstance() {
            return instance;
        }

        public List<ActionDescription> getMethods() {
            return methods;
        }
    }

    public static class ActionDescription {
        ControllerDesc ctrl;
        String name;
        Method method;
        String permission;
        List<String> parameters = new LinkedList<String>();

        public Method getMethod() {
            return method;
        }

        public String[] getAuthorized_roles() {
            return authorized_roles;
        }

        public String getUnauthorized_redirect() {
            return unauthorized_redirect;
        }

        public List<String> getParameters() {
            return parameters;
        }
    }

    public static class ModelBeanDescription {
        String name;
        Class clazz;
        Method method;

        public String getName() {
            return name;
        }

        public Class getClazz() {
            return clazz;
        }

        public Method getMethod() {
            return method;
        }
    }

    private static DispatcherMap instance;
    public static DispatcherMap get() {
        return instance;
    }

    public static DispatcherMap build(ServletContext context, ClassLoader classLoader) {
        DispatcherMap ret = new DispatcherMap();
        try {
            ret._build(context, classLoader);
            instance = ret;
            return ret;
        } catch (Exception e) {
            throw new Error(e);
        }
    }

    private Map<String, ActionDescription> actions;
    private Map<Class, Object> controllers;
    private Map<String, ModelBeanDescription> models;

    public Map<String, ActionDescription> getActions() {
        return actions;
    }

    public Map<Class, Object> getControllers() {
        return controllers;
    }

    public Map<String, ModelBeanDescription> getModels() {
        return models;
    }

    private void _build(ServletContext context, ClassLoader classLoader) throws IOException, ClassNotFoundException, IllegalAccessException, InstantiationException {
        // 1. find all classes in action package

        Map<Class, List<Class>>
            annotations = new AnnotationFinder(context, classLoader).findAnnotations();

        // 2. parse ActionClass annotations
        List<Class> classes = annotations.get(ActionClass.class);
        if( classes != null )
            loadControllers(classes);
        else {
            this.actions = Collections.emptyMap();
            this.controllers = Collections.emptyMap();
        }

        // 3. parse ModelBean annotations
        classes = annotations.get(ModelBean.class);
        if( classes != null )
            loadModels(classes);
        else
            this.models = Collections.emptyMap();

        unmoifable();
    }

    private void unmoifable() {
        actions = Collections.unmodifiableMap(actions);
        controllers = Collections.unmodifiableMap(controllers);
        models = Collections.unmodifiableMap(models);
    }

    private void loadControllers(List<Class> classes) throws IllegalAccessException, InstantiationException {
        actions = new HashMap<String, ActionDescription>();
        controllers = new HashMap<Class, Object>();

        for (Iterator<Class> iterator = classes.iterator(); iterator.hasNext(); ) {
            Class clazz = iterator.next();
            ActionClass ac = (ActionClass) clazz.getAnnotation(ActionClass.class);
            Restriction rc = (Restriction) clazz.getAnnotation(Restriction.class);

            Method[] ms = clazz.getMethods();
            for (int i = 0; i < ms.length; i++) {
                Method m = ms[i];
                ActionMethod am = m.getAnnotation(ActionMethod.class);
                if( am == null )
                    continue;

                Restriction restriction = m.getAnnotation(Restriction.class);
                if( restriction == null )
                    restriction = rc;
                String action = ac.action();

                ActionDescription ad = actions.get(action);
                if( ad == null ) {
                    ad = new ActionDescription();
                    ad.name = action;
                    if( rc != null ) {
                        ad.authorized_roles = rc.roles();
                        ad.unauthorized_redirect = rc.redirect();
                    }
                    actions.put(action, ad);
                }
                ActionMethodDescription amd = new ActionMethodDescription();
                Object instance = controllers.get(m.getDeclaringClass());
                if( instance == null) {
                    instance = m.getDeclaringClass().newInstance();
                    controllers.put(m.getClass(), instance);
                }
                amd.instance = instance;
                amd.method = m;
                if( restriction != null ) {
                    amd.authorized_roles = restriction.roles();
                    amd.unauthorized_redirect = restriction.redirect();
                }

                Annotation pa[][] = m.getParameterAnnotations();
                for (int j = 0; j < pa.length; j++) {
                    Annotation[] a = pa[j];
                    if( a != null && a.length == 1 && a[0] instanceof RequestParameter) {
                        RequestParameter rp = (RequestParameter) a[0];
                        if( rp.required() )
                            amd.parameters.add(rp.name());
                    }
                }

                if( am._default() ) {
                    if( ad.defaultMethod == null )
                        ad.defaultMethod = amd;
                    else
                        log.severe("Action already contains default method. Class" + clazz.getName());
                }

                ad.methods.add(amd);
            }
        }

        for (Iterator<ActionDescription> iterator = actions.values().iterator(); iterator.hasNext(); ) {
            ActionDescription actionDescription = iterator.next();
            if( actionDescription.defaultMethod == null && actionDescription.methods.size() == 1)
                actionDescription.defaultMethod = actionDescription.methods.get(0);
            if( actionDescription.defaultMethod == null )
                log.warning("no default method for action " + actionDescription.name);
        }
    }

    private void loadModels(List<Class> classes) {
        models = new HashMap<String, ModelBeanDescription>();

        for (Iterator<Class> iterator = classes.iterator(); iterator.hasNext(); ) {
            Class clazz = iterator.next();
            ModelBean bean = (ModelBean)clazz.getAnnotation(ModelBean.class);

            ModelBeanDescription desc = new ModelBeanDescription();
            desc.clazz = clazz;
            desc.name = bean.name();
            if( models.containsKey(desc.name) ) {
                ModelBeanDescription aDesc = models.get(desc.name);
                log.severe("ambigous modelBean name '" + desc.name + "'. classes " +
                    desc.clazz.getName() + " and " + aDesc.clazz.getName() );
                continue;
            }

            Method buildMethod = null;
            Method [] methods = clazz.getMethods();
            for (int i = 0; i < methods.length; i++) {
                Method method = methods[i];
                if( method.getName().equals("build") &&
                        method.getReturnType().equals(clazz) ) {
                    if( buildMethod != null ) {
                        log.severe("more then one build method for ModelBean " + clazz.getName() + "! try use the first");
                    } else {
                        buildMethod = method;
                    }
                }
            }
            if( buildMethod == null ) {
                log.severe("no build method for ModelBean " + clazz.getName());
                continue;
            }
            desc.method = buildMethod;
            models.put(desc.name, desc);
        }
    }

    static Logger log = Logger.getLogger(DispatcherMap.class.getName());
}
