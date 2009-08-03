package com.melexis.jiraldap;

import com.atlassian.jira.service.ServiceException;
import com.google.inject.Guice;
import com.google.inject.Injector;
import java.rmi.RemoteException;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args ) throws ServiceException, RemoteException
    {
        Injector injector = Guice.createInjector(new JiraLdapModule());
        JiraUserSyncher syncer = injector.getInstance(JiraUserSyncher.class);
        syncer.syncUsers();
        System.out.println( "Hello World!" );
    }
}
