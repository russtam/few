package few.common.dispatcher_view.model;

import few.RequestParameter;
import few.RequestParameters;
import few.core.DispatcherMap;
import few.utils.ListWrapper;
import freemarker.template.TemplateMethodModel;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.*;

/**
 * User: igor
 * Date: 10.01.12
 */
@few.ModelBean(name = "model_beans", permission = "admin")
public class ViewModelBeans extends ListWrapper<ViewModelBeans.ViewModelBean>{

    private ViewModelBeans(List<ViewModelBean> viewModelBeans) {
        super(viewModelBeans);
    }

    public static class ViewModelBean {
        private String name;
        private String permission;
        private String className;
        private List<ViewRequestParameter> parameters;

        public ViewModelBean(String name, String permission, String className, List<ViewRequestParameter> parameters) {
            this.name = name;
            this.permission = permission;
            this.className = className;
            this.parameters = Collections.unmodifiableList(parameters);
        }

        public String getName() {
            return name;
        }

        public String getPermission() {
            return permission;
        }

        public String getClassName() {
            return className;
        }

        public List<ViewRequestParameter> getParameters() {
            return parameters;
        }
    }

    public static ViewModelBeans build() {
        List<ViewModelBean> beans = new LinkedList<ViewModelBean>();

        DispatcherMap map = DispatcherMap.get();

        for (DispatcherMap.ModelBean mbd : map.getModels().values()) {
            List<ViewRequestParameter> parameters;

            parameters = buildParametersFromMethod(mbd.getMethod());
            String name = mbd.getName();
            if( TemplateMethodModel.class.isAssignableFrom(mbd.getClazz()) )
                name += "()";

            beans.add(new ViewModelBean(name, mbd.getPermission(), mbd.getClazz().getName(), parameters));
        }

        Collections.sort(beans, new Comparator<ViewModelBean>() {
            public int compare(ViewModelBean o1, ViewModelBean o2) {
                return o1.getName().compareTo(o2.getName());
            }
        });

        return new ViewModelBeans(beans);
    }

    static List<ViewRequestParameter> buildParametersFromMethod(Method method) {
        List<ViewRequestParameter> parameters = new LinkedList<ViewRequestParameter>();
        for (int i = 0; i < method.getParameterTypes().length; i++) {
            Class<?> type = method.getParameterTypes()[i];
            Annotation[] annotations = method.getParameterAnnotations()[i];
            ViewRequestParameter rp = null;
            if( annotations.length > 0 ) {
                if( annotations[0].annotationType() == RequestParameter.class) {
                    RequestParameter rpa = (RequestParameter) annotations[0];
                    String tn = type.getName();
                    if( tn.startsWith("java.lang.") )
                        tn = tn.substring("java.lang.".length());
                    rp = new ViewRequestParameter(
                            rpa.name(), rpa.required(), tn
                    );
                } else if( annotations[0].annotationType() == RequestParameters.class && Map.class.isAssignableFrom(type)) {
                    rp = new ViewRequestParameter(
                            "{vararg}", false, "Map"
                    );
                }
            }
            if( rp != null )
                parameters.add(rp);
        }

        Collections.sort(parameters, new Comparator<ViewRequestParameter>() {
            public int compare(ViewRequestParameter o1, ViewRequestParameter o2) {
                if( o1.isRequired() && !o2.isRequired() )
                    return -1;
                return o1.getName().compareTo(o2.getName());
            }
        });

        return parameters;
    }

}
