/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.melexis.jiraldap;

import java.util.HashSet;
import java.util.Set;

/**
 *
 * @author pti
 */
class JiraUserSyncher {
    private LdapService ldap;
    private JiraService jira;

    JiraUserSyncher(LdapService ldap, JiraService jira) {
        this.ldap = ldap;
        this.jira = jira;
    }

    Set<User> getNewUsers() {
        Set<User> rslt = new HashSet<User>();
        Set<User> jiraUsers = jira.getUsers();

        for (User user1 : ldap.getUsers()) {
            if (! jiraUsers.contains(user1)) {
                rslt.add(user1);
            }
        }
        
        return rslt;
    }

}
