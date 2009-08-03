/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.melexis.jiraldap;

import java.util.List;
import java.util.Random;
import java.util.ArrayList;

import com.google.inject.Inject;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author pti
 */
public class JiraServiceImplementation implements JiraService {

    private UserService userService;

    @Inject
    public JiraServiceImplementation(UserService userService) {
        this.userService = userService;
    }

    public JiraServiceImplementation() {
    }

    public List<JiraUser> getUsers() {
        return userService.getUsers();
    }

    public List<LdapUser> getLdapUsers() {
        List<LdapUser> ldapUsers = new ArrayList<LdapUser>();
        for(JiraUser user : getUsers()) {
            ldapUsers.add(new LdapUser(user.getName(), user.getFullName(), user.getEmail()));
        }
        return ldapUsers;
    }

   public void addUser(LdapUser ldapUser) {

        try {
            JiraUser user = null;
            try {
                user = userService.getUser(ldapUser.getUid());
            } catch (JiraLdapException ex) {
                Logger.getLogger(JiraServiceImplementation.class.getName()).log(Level.FINE, "User not found, creating new one", ex);
                user = userService.createUser(ldapUser.getUid());
            }

            user.setFullName(ldapUser.getName());
            user.setEmail(ldapUser.getEmail());
            user.store();

        } catch (JiraLdapException e) {
            Logger.getLogger(JiraServiceImplementation.class.getName()).log(Level.SEVERE, "Unable to find or create a user with uid=" + ldapUser.getUid(), e);
        }

    }
}
