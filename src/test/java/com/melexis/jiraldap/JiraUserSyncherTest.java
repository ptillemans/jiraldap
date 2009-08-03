/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.melexis.jiraldap;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import org.junit.Test;

import java.rmi.RemoteException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.ArrayList;

/**
 *
 * @author pti
 */
public class JiraUserSyncherTest {

    private Set<LdapUser> createUsers() {
        Set<LdapUser> users = new HashSet<LdapUser>();
        users.add(new LdapUser("abc", "Alice Botticelli", "abc@sevenseas.com"));
        users.add(new LdapUser("bde", "Bob Detroit", "bde@sevenseas.com"));
        users.add(new LdapUser("cef", "Charles Earphones", "cef@sevenseas.com"));
        return users;
    }

    @Test
    public void testSyncUsers() {

        LdapService ldap = new LdapService() {

            private Set<LdapUser> users = new HashSet<LdapUser>();


            {
                users = createUsers();
                users.add(new LdapUser("new", "New User", "new@sevenseas.com"));
            }

            public Set<LdapUser> getUsers() {
                return users;
            }
        };

        final List<LdapUser> newUsers = new ArrayList<LdapUser>();
        JiraService jira;
        jira = new JiraServiceImplementation() {
            public ArrayList<JiraUser> users;

            {
                users =  new ArrayList<JiraUser>();
                for (LdapUser user : createUsers()) {
                    addUser(user);
                }
                newUsers.clear();
            }

            @Override
            public void addUser(LdapUser user) {
                users.add(new MockJiraUser(user));
                newUsers.add(user);
            }

            @Override
            public List<JiraUser> getUsers()  {
               return users;
            }
        };

        JiraUserSyncher jus = new JiraUserSyncher(ldap, jira);

        
        jus.syncUsers();

        assertThat(newUsers.size(), is(1));

        for (LdapUser actual : newUsers) {
            assertThat(actual.getUid(), is("new"));
        }
    }
}
