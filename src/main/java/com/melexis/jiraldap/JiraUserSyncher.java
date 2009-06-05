/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.melexis.jiraldap;

import com.google.inject.Inject;
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
    JiraUserSyncher(LdapService ldap, JiraService jira) {
        this.ldap = ldap;
        this.jira = jira;
    }

    private Set<LdapUser> getNewUsers() {
        Set<LdapUser> rslt = ldap.getUsers();

        rslt.removeAll(jira.getUsers());
        
        return rslt;
    }

    void syncUsers() {
        for(LdapUser user:getNewUsers()) {
            jira.addUser(user);
        }
    }

}
