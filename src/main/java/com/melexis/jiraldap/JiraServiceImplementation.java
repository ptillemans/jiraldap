/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.melexis.jiraldap;

import com.dolby.jira.net.soap.jira.JiraSoapService;
import com.dolby.jira.net.soap.jira.JiraSoapServiceServiceLocator;
import com.dolby.jira.net.soap.jira.RemoteGroup;
import com.dolby.jira.net.soap.jira.RemoteUser;
import java.rmi.RemoteException;
import java.util.HashSet;
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


    public JiraServiceImplementation(JiraSoapService svc, String username, String password) throws RemoteException {
        token = jira.login(username, password);
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

}
