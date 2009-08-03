/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.melexis.jiraldap;

import org.junit.Test;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.CoreMatchers.*;

/**
 *
 * @author pti
 */
public class UserTest {
    @Test
    public void testEqualsIdenticalObjects() {
        LdapUser user1 = new LdapUser("abc", "Alice Botticelli", "abc@xtrion.be");
        LdapUser user2 = new LdapUser("abc", "Alice Botticelli", "abc@xtrion.be");

        assertThat(user1, is(user2));
    }

    @Test
    public void testEqualsSameObjects() {
        LdapUser user1 = new LdapUser("abc", "Alice Botticelli", "abc@xtrion.be");

        assertThat(user1, is(user1));
    }

    @Test
    public void testEqualsDifferentObjects() {
        LdapUser user1 = new LdapUser("abc", "Alice Botticelli", "abc@xtrion.be");

        LdapUser user2 = new LdapUser("abo", "Alice Botticelli", "abc@xtrion.be");
        assertThat(user1, not(is(user2)));

        user2 = new LdapUser("abc", "Andrew Botticelli", "abc@xtrion.be");
        assertThat(user1, not(is(user2)));


        user2 = new LdapUser("abc", "Alice Botticelli", "abc@melexis.com");
        assertThat(user1, not(is(user2)));

    }

}
