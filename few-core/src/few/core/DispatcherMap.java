package few.core;

import few.*;
import few.annotations.AnnotationFinderImpl;
import few.services.AnnotationFinder;
import few.utils.Utils;

import javax.servlet.ServletContext;
import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.*;
import java.util.logging.Logger;

/**
 * User: gerbylev
 * Date: 05.11.11
 */
public class DispatcherMap {

    public static class Controller {
        String name;
        String permission;
        Object instance;
        Map<String, Action> actions;

        Controller() {
        }

        public String getName() {
            return name;
        }

        public String getPermission() {
            return permission;
        }

        public Object getInstance() {
            return instance;
        }

        public Map<String, Action> getActions() {
            return actions;
        }
    }

    public static class Action {
        Controller ctrl;
        String name;
        Method method;
        String permission;
        List<String> parameters = new LinkedList<String>();

        Action() {
        }

        public Method getMethod() {
            return method;
        }

        public Controller getCtrl() {
            return ctrl;
        }

        public String getName() {
            return name;
        }

        public String getPermission() {
            return permission;
        }

        public List<String> getParameters() {
            return parameters;
        }
    }

    public static class ModelBean {
        String name;
        String permission;
        Class clazz;
        Method method;

        ModelBean() {
        }

        public String getName() {
            return name;
        }

        public Class getClazz() {
            return clazz;
        }

        public Method getMethod() {
            return method;
        }

        public String getPermission() {
            return permission;
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

    private Map<String, Controller> controllers;
    private Map<String, ModelBean> models;

    public Map<String, Controller> getControllers() {
        return controllers;
    }

    public Map<String, ModelBean> getModels() {
        return models;
    }

    private void _build(ServletContext context, ClassLoader classLoader) throws IOException, ClassNotFoundException, IllegalAccessException, InstantiationException {
        // 1. load annotations
        AnnotationFinder af = ServiceRegistry.get(AnnotationFinder.class);
        Map<Class, List<Class>>
            annotations = af.findAnnotations();

        // 2. parse Controller annotations
        List<Class> classes = annotations.get(few.Controller.class);
        if( classes != null )
            loadControllers(classes);
        else
            this.controllers = Collections.emptyMap();

        // 3. parse ModelBean annotations
        classes = annotations.get(few.ModelBean.class);
        if( classes != null )
            loadModels(classes);
        else
            this.models = Collections.emptyMap();

        controllers = Collections.unmodifiableMap(controllers);
        models = Collections.unmodifiableMap(models);
    }


    private void loadControllers(List<Class> classes) throws IllegalAccessException, InstantiationException {
        controllers = new HashMap<String, Controller>();

        for (Iterator<Class> iterator = classes.iterator(); iterator.hasNext(); ) {
            Class clazz = iterator.next();
            few.Controller ac = (few.Controller) clazz.getAnnotation(few.Controller.class);

            Controller ctrl = new Controller();
            ctrl.name = ac.name();
            ctrl.instance = clazz.newInstance();
            if( Utils.isNotNull(ac.permission()) )
                ctrl.permission = ac.permission();
            ctrl.actions = new HashMap<String, Action>();

            controllers.put(clazz.getName(), ctrl);
            if( !controllers.containsKey(ac.name()) ) {
                controllers.put(ac.name(), ctrl);
            } else {
                if( controllers.get(ac.name()) != null )
                    log.warning("ambiguos controller name '" + ac.name() + "', class " + controllers.get(ac.name()).instance.getClass().getName() );
                log.warning("ambiguos controller name '" + ac.name() + "', class " + clazz.getName());
                controllers.put(ac.name(), null);
            }

            Method[] ms = clazz.getMethods();
            for (int i = 0; i < ms.length; i++) {
                Method m = ms[i];
                few.Action am = m.getAnnotation(few.Action.class);
                if( am == null )
                    continue;

                Action action = new Action();
                action.ctrl = ctrl;
                action.method = m;
                action.name = m.getName();
                if( Utils.isNotNull(am.permission()) )
                    action.permission = am.permission();
                action.parameters = new LinkedList<String>();

                Annotation pa[][] = m.getParameterAnnotations();
                for (int j = 0; j < pa.length; j++) {
                    Annotation[] a = pa[j];
                    if( a != null && a.length == 1 && a[0] instanceof RequestParameter) {
                        RequestParameter rp = (RequestParameter) a[0];
                        if( rp.required() )
                            action.parameters.add(rp.name());
                    }
                }

                ctrl.actions.put(action.name, action);
            }
        }

    }

    private void loadModels(List<Class> classes) {
        models = new HashMap<String, ModelBean>();

        for (Iterator<Class> iterator = classes.iterator(); iterator.hasNext(); ) {
            Class clazz = iterator.next();
            few.ModelBean bean = (few.ModelBean)clazz.getAnnotation(few.ModelBean.class);

            ModelBean desc = new ModelBean();
            desc.clazz = clazz;
            desc.name = bean.name();
            if( Utils.isNotNull(bean.permission()) )
                desc.permission = bean.permission();
            if( models.containsKey(desc.name) ) {
                ModelBean aDesc = models.get(desc.name);
                log.severe("ambigous modelBean name '" + desc.name + "'. classes " +
                    desc.clazz.getName() + " and " + aDesc.clazz.getName() );
                continue;
            }

            Method buildMethod = null;
            Method [] methods = clazz.getMethods();
            for (int i = 0; i < methods.length; i++) {
                Method method = methods[i];
                if( method.getName().equals("build")  ) {
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
