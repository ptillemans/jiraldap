package com.melexis.jiraldap;

import com.google.inject.Guice;
import com.google.inject.Injector;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
        Injector injector = Guice.createInjector(new JiraLdapModule());
        JiraUserSyncher syncer = injector.getInstance(JiraUserSyncher.class);
        syncer.syncUsers();
        System.out.println( "Hello World!" );
    }
}
