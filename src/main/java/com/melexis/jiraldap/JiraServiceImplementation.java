/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.melexis.jiraldap;

import com.dolby.jira.net.soap.jira.JiraSoapService;
import com.dolby.jira.net.soap.jira.RemoteAuthenticationException;
import com.dolby.jira.net.soap.jira.RemoteGroup;
import com.dolby.jira.net.soap.jira.RemotePermissionException;
import com.dolby.jira.net.soap.jira.RemoteUser;
import com.dolby.jira.net.soap.jira.RemoteValidationException;
import com.google.inject.name.Named;
import java.rmi.RemoteException;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.rpc.ServiceException;

/**
 *
 * @author pti
 */
public class JiraServiceImplementation implements JiraService {

    JiraSoapService jira = null;

    private String token;
    private Random rand;


    public JiraServiceImplementation(JiraSoapService svc,
            @Named("jira.server.user") String username,
            @Named("jira.server.pasword") String password
            ) throws RemoteException {
        jira = svc;
        token = jira.login(username, password);
        rand = new Random();
    }

    private RemoteUser[] getRemoteUsers() throws ServiceException, RemoteException {
        RemoteGroup remoteGroup = jira.getGroup(token, "jira-users");
        return remoteGroup.getUsers();
    }

    public Set<User> getUsers() {
        Set<User> users = new HashSet<User>();
        try {
            for (RemoteUser ru : getRemoteUsers()) {
                users.add(new User(ru.getName(),ru.getFullname(),ru.getEmail()));
            }
        } catch (ServiceException ex) {
            Logger.getLogger(JiraServiceImplementation.class.getName()).log(Level.SEVERE, null, ex);
        } catch (RemoteException ex) {
            Logger.getLogger(JiraServiceImplementation.class.getName()).log(Level.SEVERE, null, ex);
        }
        return users;
    }

    public void addUser(User user) {
        try {
            jira.createUser(token, user.getUid(), Long.toHexString(rand.nextLong()), user.getName(), user.getEmail());
        } catch (RemoteException ex) {
            Logger.getLogger(JiraServiceImplementation.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
