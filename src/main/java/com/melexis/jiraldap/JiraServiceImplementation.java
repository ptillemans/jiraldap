/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.melexis.jiraldap;

import com.google.inject.name.Named;
import com.opensymphony.user.UserManager;
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

    private Random rand;


    public JiraServiceImplementation() {
        rand = new Random();
    }

    private List<LdapUser> getUsers() throws ServiceException, RemoteException {
        UserManager um = UserManager.getInstance();
        
        RemoteGroup remoteGroup = jira.getGroup(token, "jira-users");
        return List(<User>) um.getUsers();
    }

    public Set<LdapUser> getUsers() {
        Set<LdapUser> users = new HashSet<LdapUser>();
        try {
            for (RemoteUser ru : getRemoteUsers()) {
                users.add(new LdapUser(ru.getName(),ru.getFullname(),ru.getEmail()));
            }
        } catch (ServiceException ex) {
            Logger.getLogger(JiraServiceImplementation.class.getName()).log(Level.SEVERE, null, ex);
        } catch (RemoteException ex) {
            Logger.getLogger(JiraServiceImplementation.class.getName()).log(Level.SEVERE, null, ex);
        }
        return users;
    }

    public void addUser(LdapUser user) {
        UserManager um = UserManager.getInstance();
        um.
        jira.createUser(token, user.getUid(), Long.toHexString(rand.nextLong()), user.getName(), user.getEmail());

    }

}
