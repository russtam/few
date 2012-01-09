package few.common.dispatcher_view.presentation;

/**
 * Created by IntelliJ IDEA.
 * User: igor
 * Date: 10.01.12
 * Time: 0:20
 * To change this template use File | Settings | File Templates.
 */
public class ViewRequestParameter {
    private String name;
    private boolean required;
    private String type;

    public ViewRequestParameter(String name, boolean required, String type) {
        this.name = name;
        this.required = required;
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public boolean isRequired() {
        return required;
    }

    public String getType() {
        return type;
    }
}
