/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.melexis.jiraldap;

import java.util.HashSet;
import java.util.Set;
import org.junit.Test;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.CoreMatchers.*;

/**
 *
 * @author pti
 */
public class JiraUserSyncherTest {

    private Set<User> createUsers() {
        Set users = new HashSet<User>();
        users.add(new User("abc", "Alice Botticelli", "abc@xtrion.be"));
        users.add(new User("bde", "Bob Detroit", "bde@xtrion.be"));
        users.add(new User("cef", "Charles Earphones", "cef@xtrion.be"));
        return users;
    }

    @Test
    public void testCreateUsers() {

        LdapService ldap = new LdapService() {

            private Set<User> users = new HashSet<User>();


            {
                users = createUsers();
                users.add(new User("new", "New User", "new@xtrion.be"));
            }

            public Set<User> getUsers() {
                return users;
            }
        };
        JiraService jira = new JiraService() {

            private Set<User> users;


            {
                users = createUsers();
            }

            public Set<User> getUsers() {
                return users;
            }
        };

        JiraUserSyncher jus = new JiraUserSyncher(ldap, jira);

        Set<User> newUsers = jus.getNewUsers();

        assertThat(newUsers.size(), is(1));

        for (User actual : newUsers) {
            assertThat(actual.getUid(), is("new"));
        }
    }
}
