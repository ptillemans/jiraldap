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

    private Set<User> createUsers() {
        Set users = new HashSet<User>();
        users.add(new User("abc", "Alice Botticelli", "abc@sevenseas.com"));
        users.add(new User("bde", "Bob Detroit", "bde@sevenseas.com"));
        users.add(new User("cef", "Charles Earphones", "cef@sevenseas.com"));
        return users;
    }

    @Test
    public void testSyncUsers() {

        LdapService ldap = new LdapService() {

            private Set<User> users = new HashSet<User>();


            {
                users = createUsers();
                users.add(new User("new", "New User", "new@sevenseas.com"));
            }

            public Set<User> getUsers() {
                return users;
            }
        };

        final Set<User> newUsers = new HashSet();
        JiraService jira = new JiraService() {

            private Set<User> users;


            {
                users = createUsers();
            }

            public Set<User> getUsers() {
                return users;
            }

            public void addUser(User user) {
                newUsers.add(user);
            }
        };

        JiraUserSyncher jus = new JiraUserSyncher(ldap, jira);

        
        jus.syncUsers();

        assertThat(newUsers.size(), is(1));

        for (User actual : newUsers) {
            assertThat(actual.getUid(), is("new"));
        }
    }
}
