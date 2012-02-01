package few.impl;

import few.services.Configuration;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by IntelliJ IDEA.
 * User: igor
 * Date: 19.12.11
 * Time: 1:16
 * To change this template use File | Settings | File Templates.
 */
public class DefaultConfigurationImpl implements Configuration {

    volatile Properties properties;
    volatile Collection<String> propertyNames;

    public DefaultConfigurationImpl() {
        this("few-default.properties");
    }
    public DefaultConfigurationImpl(String config) {
        this.config = config;
        URL u = Thread.currentThread().getContextClassLoader().getResource(config);
        if( u != null && new File(u.getFile()).exists() ) {
            this.configFile = new File(u.getFile());
            this.autocheckTimer = new Timer("DefaultConfigurationImpl autocheckTimer");
            this.autocheckTimer.schedule(new ConfigAutocheck(), 1000, 1000 );
        }
        load();
    }

    private String config;
    private File configFile;
    private long lm;
    private Timer autocheckTimer;

    public void destroy() {
        if( autocheckTimer != null )
            autocheckTimer.cancel();
    }

    class ConfigAutocheck extends TimerTask {

        public void run() {
            if( configFile.lastModified() != lm ) {
                load();
            }
        }
    }

    private void load() {
        try {
            Properties properties = new Properties();
            properties.load(
                    Thread.currentThread().getContextClassLoader().getResourceAsStream(config)
            );
            Collection<String> propertyNames = new LinkedList<String>();
            Enumeration e = properties.propertyNames();
            while (e.hasMoreElements()) {
                propertyNames.add((String) e.nextElement());
            }

            this.properties = properties;
            this.propertyNames = Collections.unmodifiableCollection(propertyNames);

            if( configFile != null )
                lm = configFile.lastModified();
        } catch(IOException e) {
            log.log(Level.SEVERE, "", e);
        }
    }

    public String getProperty(String key) {
        return properties.getProperty(key);
    }

    public Collection<String> propertyNames() {
        return propertyNames;
    }


    Logger log = Logger.getLogger(DefaultConfigurationImpl.class.getName());
}
