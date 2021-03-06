package few.core;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * User: igor
 * Date: 19.12.11
 */
public class ServiceRegistry {

    static private Map<Class, Class> serviceClasses = new HashMap<Class, Class>();
    static private Map<Class, Class> defaultClasses = new HashMap<Class, Class>();
    static private volatile Map<Class, Object> serviceInstances = new HashMap<Class, Object>();

    static public void registerService(Class clazz) {
        Class[] interfaces = clazz.getInterfaces();
        synchronized (serviceClasses) {
            for (int i = 0; i < interfaces.length; i++) {
                Class anInterface = interfaces[i];
                if( !anInterface.getName().startsWith("java") ) {
                    if( serviceClasses.containsKey(anInterface) || serviceInstances.containsKey(anInterface) ) {
                        throw new IllegalStateException("the impl for service " + anInterface.getName() + " already registered");
                    }
                    serviceClasses.put(anInterface, clazz);
                }
            }
        }
    }

    static public void registerDefaultImpl(Class clazz) {
        Class[] interfaces = clazz.getInterfaces();
        synchronized (serviceClasses) {
            for (int i = 0; i < interfaces.length; i++) {
                Class anInterface = interfaces[i];
                if( !anInterface.getName().startsWith("java") ) {
                    if( defaultClasses.containsKey(anInterface) ) {
                        throw new IllegalStateException("the default impl for service " + anInterface.getName() + " already registered");
                    }
                    defaultClasses.put(anInterface, clazz);
                }
            }
        }
    }


    static public void registerService(Object instance) {
        Class[] interfaces = instance.getClass().getInterfaces();
        synchronized (serviceClasses) {
            for (int i = 0; i < interfaces.length; i++) {
                Class anInterface = interfaces[i];
                if( !anInterface.getName().startsWith("java") ) {
                    if( serviceClasses.containsKey(anInterface) ) {
                        throw new IllegalStateException("the impl for service " + anInterface.getName() + " already registered");
                    }
                    serviceClasses.put(anInterface, null);
                    serviceInstances.put(anInterface, instance);
                }
            }
        }
    }

    static public<T> T get(Class<T> clazz) {
        Object impl = serviceInstances.get(clazz);
        if( impl != null )
            return (T) impl;

        synchronized (serviceClasses) {
            Class cl = serviceClasses.get(clazz);
            if( cl == null ) {
                cl = defaultClasses.get(clazz);
                if( cl != null )
                    log.warning("use default impl " + cl.getName() + " for service " + clazz.getName());
                else {
                    log.severe("no impl registered for service " + clazz.getName() + ", return null;");
                    return null;
                }
            }
            try {
                impl = cl.newInstance();
            } catch (Exception e) {
                throw new RuntimeException("could not create impl for service " + clazz.getName(), e);
            }
            HashMap<Class, Object> copy = new HashMap<Class, Object>();
            copy.putAll(serviceInstances);
            copy.put(clazz, impl);
            serviceInstances = copy;
        }

        return (T) impl;
    }

    static public void shutdown() {
        synchronized (serviceClasses) {
            Collection instances = serviceInstances.values();
            for (Iterator iterator = instances.iterator(); iterator.hasNext(); ) {
                Object o = iterator.next();
                try {
                    Method m = o.getClass().getMethod("destroy");
                    if( m != null ) {
                        m.invoke(o);
                    }
                } catch (NoSuchMethodException e) {
                } catch (Exception e) {
                    log.log(Level.SEVERE, "", e);
                }
            }

        }
    }

    static private Logger log = Logger.getLogger(ServiceRegistry.class.getName());
}
