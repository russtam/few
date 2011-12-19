package few.impl;

import few.services.Configuration;

import java.io.IOException;
import java.util.Properties;

/**
 * Created by IntelliJ IDEA.
 * User: igor
 * Date: 19.12.11
 * Time: 1:16
 * To change this template use File | Settings | File Templates.
 */
public class DefaultConfigurationImpl implements Configuration {

    Properties properties;

    public DefaultConfigurationImpl() {
        try {
            properties = new Properties();
            properties.load( this.getClass().getClassLoader().getResourceAsStream("few-default.properties") );
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public String getProperty(String key) {
        return properties.getProperty(key);
    }

}
