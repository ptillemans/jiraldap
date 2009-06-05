/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.melexis.jiraldap;

import java.util.HashSet;
import java.util.Set;
import org.junit.Test;
import static org.easymock.EasyMock.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

/**
 *
 * @author pti
 */
public class JiraUserSyncherTest {

    private Set<LdapUser> createUsers() {
        Set users = new HashSet<LdapUser>();
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

        final Set<LdapUser> newUsers = new HashSet();
        JiraService jira = new JiraService() {

            private Set<LdapUser> users;


            {
                users = createUsers();
            }

            public Set<LdapUser> getUsers() {
                return users;
            }

            public void addUser(LdapUser user) {
                newUsers.add(user);
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
