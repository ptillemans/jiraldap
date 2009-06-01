/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.melexis.jiraldap;

import java.util.Set;

import javax.naming.NamingException;
import javax.naming.directory.Attribute;
import javax.naming.directory.Attributes;
import javax.naming.directory.DirContext;

import javax.naming.directory.SearchControls;

/**
 *
 * @author pti
 */
public class LdapTest extends AbstractLdapTest {

    /**
     * Test that the partition has been correctly created
     */
    public void testPartition() throws NamingException
    {

        // We should be able to read it
        DirContext appRoot = createContext("o=sevenSeas");
        assertNotNull( appRoot );

        // Let's get the entry associated to the top level
        Attributes attributes = appRoot.getAttributes( "" );
        assertNotNull( attributes );
        assertEquals( "sevenseas", attributes.get( "o" ).get() );

        Attribute attribute = attributes.get( "objectClass" );
        assertNotNull( attribute );
        assertTrue( attribute.contains( "top" ) );
        assertTrue( attribute.contains( "organization" ) );
        // Ok, everything is fine
    }

    /**
     * Test that the ldif data has correctly been imported
     */
    public void testImport() throws NamingException
    {
        // Searching for all
        Set<String> result = searchDNs( "(ObjectClass=*)", "o=sevenSeas", "",
                                          SearchControls.ONELEVEL_SCOPE );

        assertTrue( result.contains( "ou=groups,o=sevenSeas" ) );
        assertTrue( result.contains( "ou=people,o=sevenSeas" ) );
        assertTrue( result.contains( "ou=people,o=sevenSeas" ) );
    }

    public void testImportPeople() throws NamingException {
        Set<String> people = searchDNs("(ObjectClass=*)", "ou=people,o=sevenSeas", "", SearchControls.ONELEVEL_SCOPE);
        assertTrue(people.contains("uid=abc,ou=people,o=sevenSeas"));
        assertTrue(people.contains("uid=bde,ou=people,o=sevenSeas"));
        assertTrue(people.contains("uid=cef,ou=people,o=sevenSeas"));
    }
}