/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.melexis.jiraldap;

import com.atlassian.configurable.ObjectConfiguration;
import com.atlassian.configurable.ObjectConfigurationException;
import com.atlassian.jira.service.AbstractService;

/**
 *
 * @author pti
 */public class LdapUserSyncService extends AbstractService {

    @Override
    public void run() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public ObjectConfiguration getObjectConfiguration() throws ObjectConfigurationException {
        return getObjectConfiguration("LDAPUSERSYNCSERVICE", "/LDAPUserSyncService.xml", null);
    }

}
