/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.melexis.jiraldap;

import java.io.File;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.Set;
import org.apache.directory.server.unit.AbstractServerTest;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingEnumeration;
import javax.naming.NamingException;
import javax.naming.directory.Attribute;
import javax.naming.directory.Attributes;
import javax.naming.directory.BasicAttribute;
import javax.naming.directory.BasicAttributes;
import javax.naming.directory.DirContext;

import javax.naming.directory.SearchControls;
import javax.naming.directory.SearchResult;
import org.apache.directory.server.core.configuration.MutablePartitionConfiguration;
import org.apache.directory.server.unit.AbstractServerTest;

/**
 *
 * @author pti
 */
public class LdapTest extends AbstractServerTest {


    /**
     * Initialize the server.
     */
    @Override
    public void setUp() throws Exception
    {
        // Add partition 'sevenSeas'
        MutablePartitionConfiguration pcfg = new MutablePartitionConfiguration();
        pcfg.setName( "sevenSeas" );
        pcfg.setSuffix( "o=sevenseas" );

        // Create some indices
        Set<String> indexedAttrs = new HashSet<String>();
        indexedAttrs.add( "objectClass" );
        indexedAttrs.add( "o" );
        pcfg.setIndexedAttributes( indexedAttrs );

        // Create a first entry associated to the partition
        Attributes attrs = new BasicAttributes( true );

        // First, the objectClass attribute
        Attribute attr = new BasicAttribute( "objectClass" );
        attr.add( "top" );
        attr.add( "organization" );
        attrs.put( attr );

        // The the 'Organization' attribute
        attr = new BasicAttribute( "o" );
        attr.add( "sevenseas" );
        attrs.put( attr );

        // Associate this entry to the partition
        pcfg.setContextEntry( attrs );

        // As we can create more than one partition, we must store
        // each created partition in a Set before initialization
        Set<MutablePartitionConfiguration> pcfgs = new HashSet<MutablePartitionConfiguration>();
        pcfgs.add( pcfg );

        configuration.setContextPartitionConfigurations( pcfgs );

        // Create a working directory
        File workingDirectory = new File( "server-work" );
        configuration.setWorkingDirectory( workingDirectory );

        // Now, let's call the upper class which is responsible for the
        // partitions creation
        super.setUp();

        // Load a demo ldif file
        importLdif( this.getClass().getResourceAsStream( "test.ldif" ) );

    }

    private DirContext createContext(String partition) throws NamingException {
        Hashtable<Object, Object> env = new Hashtable<Object, Object>(configuration.toJndiEnvironment());
        // Create a new context pointing to the overseas partition
        env.put(Context.PROVIDER_URL, partition);
        env.put(Context.SECURITY_PRINCIPAL, "uid=admin,ou=system");
        env.put(Context.SECURITY_CREDENTIALS, "secret");
        env.put(Context.SECURITY_AUTHENTICATION, "simple");
        env.put(Context.INITIAL_CONTEXT_FACTORY, "org.apache.directory.server.jndi.ServerContextFactory");
        // Let's open a connection on this partition
        InitialContext initialContext = new InitialContext(env);
        // We should be able to read it
        DirContext appRoot = (DirContext) initialContext.lookup( "" );
        return appRoot;
    }

    /**
     * Performs a single level search from a root base and
     * returns the set of DNs found.
     */
    private Set<String> searchDNs( String filter, String partition, String base, int scope )
       throws NamingException
    {
        DirContext appRoot = createContext( partition );

        SearchControls controls = new SearchControls();
        controls.setSearchScope( scope );
        NamingEnumeration result = appRoot.search( base, filter, controls );

        // collect all results
        HashSet<String> entries = new HashSet<String>();

        while ( result.hasMore() )
        {
            SearchResult entry = ( SearchResult ) result.next();
            entries.add( entry.getName() );
        }

        return entries;
    }

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


    /**
     * Shutdown the server.
     */
    @Override
    public void tearDown() throws Exception
    {
        super.tearDown();
    }
}