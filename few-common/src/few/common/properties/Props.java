package few.common.properties;


import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: gerbylev
 * Date: 23.11.11
 * Time: 2:48
 * To change this template use File | Settings | File Templates.
 */
public class Props {

    private static final SystemPropertyService service = SystemPropertyService.get();

    private static LinkedList<Property> properties = new LinkedList<Property>();

    public static List<Property> getProperties() {
        return Collections.unmodifiableList(properties);
    }

    public static class Property {
        private String key;
        private String description;
        private String defaultValue;

        public Property(String key, String description, String defaultValue) {
            this.key = key;
            this.description = description;
            this.defaultValue = defaultValue;

            properties.add(this);
        }

        public String getKey() {
            return key;
        }

        public String getDescription() {
            return description;
        }

        protected String getDefaultValue() {
            return defaultValue;
        }

        public String getString() {
            String ret = service.getProperty(key);
            if( ret != null )
                return ret;
            else
                return defaultValue;
        }
        public int getInt() {
            return Integer.valueOf(getString());
        }
        public boolean getBoolean() {
            return Boolean.valueOf(getString());
        }

        public boolean exists() {
            return service.getProperty(key) != null && !service.getProperty(key).trim().isEmpty();
        }

        @Override
        public String toString() {
            return getString();
        }
    }

    public static final Property ADMIN_EMAIL = new Property(
            "admin_email",
            "Почта админа. На неё будут приходить нотификации об авариях и отчёты о регулярных действиях",
            "superuser@site.com");

    public static final Property MAIL_COPY = new Property(
            "copy_email",
            "Адрес, на который будут отправляться копии всех отправленных писем. Нужно на этапе отладки для мониторинга рассылки",
            "");

    public static final Property MAILS_FROM = new Property(
            "mails.from",
            "Адрес отправителя писем",
            "robot@site.com");

    public static final Property HTTP_REQUEST_TIMEOUT = new Property(
            "http_timeout",
            "Таймаут на исходящие http-запросы (yandex, google). (сек)",
            "30");

    public static final Property GMAP_APPKEY = new Property(
            "gmap_appkey",
            "Ключ приложения, для доступа к Google API.",
            "--");

    public static final Property YANDEX_APPKEY = new Property(
            "yandex_appkey",
            "Ключ приложения, для доступа к Yandex API.",
            "--");


    public static final Property SMTP_HOST = new Property(
            "smtp.host",
            "SMTP_HOST",
            "smtp.gmail.com");
    public static final Property SMTP_PORT = new Property(
            "smtp.port",
            "SMTP_PORT",
            "25");
    public static final Property SMTP_USER = new Property(
            "smtp.user",
            "SMTP_USER",
            "smtp.gmail.com");
    public static final Property SMTP_PASSWORD = new Property(
            "smtp.password",
            "SMTP_PASSWORD",
            "25");
    public static final Property SITE_NAME = new Property(
            "site",
            "Имя сайта",
            "site.com");

    public static final Property PROXY_SET = new Property(
            "proxySet",
            "proxySet",
            "");
    public static final Property HTTP_PROXY_HOST = new Property(
            "http.proxyHost",
            "http.proxyHost",
            "");
    public static final Property HTTP_PROXY_PORT = new Property(
            "http.proxyPort",
            "http.proxyPort",
            "");
    public static final Property HTTPS_PROXY_HOST = new Property(
            "https.proxyHost",
            "https.proxyHost",
            "");
    public static final Property HTTPS_PROXY_PORT = new Property(
            "https.proxyPort",
            "https.proxyPort",
            "");

}
