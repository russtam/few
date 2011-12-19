package few.core;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

/**
 * Created by IntelliJ IDEA.
 * User: igor
 * Date: 19.12.11
 * Time: 0:46
 * To change this template use File | Settings | File Templates.
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
                else
                    throw new IllegalStateException("no impl registered for service " + clazz.getName());
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

    static private Logger log = Logger.getLogger(ServiceRegistry.class.getName());
}
