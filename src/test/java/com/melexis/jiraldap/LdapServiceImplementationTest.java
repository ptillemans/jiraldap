/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.melexis.jiraldap;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import javax.naming.NamingException;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

/**
 *
 * @author pti
 */
public class LdapServiceImplementationTest extends AbstractLdapTest {

    private LdapServiceImplementation ldap;

    @Override
    public void setUp() throws Exception {
        ldap = new LdapServiceImplementation("","(ObjectClass=inetOrgPerson)");
        super.setUp();
    }

    @Override
    public void tearDown() throws Exception {
        super.tearDown();
    }

    public void testUsers() throws NamingException {
       ldap.setContext(createContext("o=sevenSeas"));
       Set<LdapUser> users = ldap.getUsers();

       Map<String,LdapUser> uids = new HashMap<String,LdapUser>();
       for (LdapUser user:users) {
           uids.put(user.getUid(),user);
       }

       assertThat(uids.size(),equalTo(3));

       assertThat(uids.get("abc").getName(),equalTo("Alice Botticelli"));
       assertThat(uids.get("abc").getEmail(),equalTo("abc@sevenseas.com"));

       assertThat(uids.get("bde").getName(),equalTo("Bob Detroit"));
       assertThat(uids.get("bde").getEmail(),equalTo("bde@sevenseas.com"));

       assertThat(uids.get("cef").getName(),equalTo("Charles Earphones"));
       assertThat(uids.get("cef").getEmail(),equalTo("cef@sevenseas.com"));

    }
}
