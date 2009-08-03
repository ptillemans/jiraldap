/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.melexis.jiraldap;

import com.atlassian.jira.service.ServiceException;
import com.google.inject.Inject;
import java.rmi.RemoteException;
import java.util.HashSet;
import java.util.Set;

/**
 *
 * @author pti
 */
class JiraUserSyncher {
    private LdapService ldap;
    private JiraService jira;

    @Inject
    public JiraUserSyncher(LdapService ldap, JiraService jira) {
        this.ldap = ldap;
        this.jira = jira;
    }

    private Set<LdapUser> getNewUsers() {
        Set<LdapUser> rslt = ldap.getUsers();

        rslt.removeAll(jira.getLdapUsers());
        
        return rslt;
    }

    void syncUsers()  {
        for(LdapUser user:getNewUsers()) {
            jira.addUser(user);
        }
    }

}
