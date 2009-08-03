/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.melexis.jiraldap;

import com.google.inject.AbstractModule;
import com.google.inject.name.Names;
import com.opensymphony.user.UserManager;

import java.net.URL;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;


/**
 *
 * @author pti
 */
public class JiraLdapModule extends AbstractModule {
    private static final String PROPERTIES_FILE = "jiraldap.properties";

   	private static Properties loadProperties() throws Exception {
		Properties properties = new Properties();

		ClassLoader loader = JiraLdapModule.class.getClassLoader();
		URL url = loader.getResource("/" + PROPERTIES_FILE);
		properties.load(url.openStream());
		return properties;
	}

    @Override
    protected void configure() {
        try {
            Names.bindProperties(binder(), loadProperties());
        } catch (Exception ex) {
            Logger.getLogger(JiraLdapModule.class.getName()).log(Level.SEVERE, "Unable to load " + PROPERTIES_FILE, ex);
        }
        bind(UserService.class).to(UserServiceImpl.class);
        bind(UserManager.class).toProvider(UserManagerProvider.class);
    }

}
