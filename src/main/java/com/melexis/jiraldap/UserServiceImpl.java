package com.melexis.jiraldap;

import com.opensymphony.user.*;
import com.google.inject.Inject;

import java.util.List;
import java.util.ArrayList;
import java.util.Random;

/**
 * Created by IntelliJ IDEA.
 * User: pti
 * Date: 16-Jul-2009
 * Time: 18:36:05
 * To change this template use File | Settings | File Templates.
 */
public class UserServiceImpl implements UserService {

    private Random rand = new Random();

    private UserManager userManager;

    @Inject
    public UserServiceImpl(UserManager userManager) {
        this.userManager = userManager;
    }

    public JiraUser getUser(String uid) throws JiraLdapException {
        User user = null;
        try {
            user = userManager.getUser(uid);
        } catch (EntityNotFoundException e) {
            throw new JiraLdapException("Unable to get user with uid=" + uid, e);
        }
        return new JiraUserImpl(user);
    }

    public JiraUser createUser(String uid) throws JiraLdapException {
        User user = null;
        try {
            user = userManager.createUser(uid);
            user.setPassword(String.valueOf(rand.nextLong()));
        } catch (DuplicateEntityException e) {
            throw new JiraLdapException("Unable to create user with uid=" + uid, e);
        } catch (ImmutableException e) {
            throw new JiraLdapException("Unable to create user with uid=" + uid, e);
        }
        return new JiraUserImpl(user);
    }

    public List<JiraUser> getUsers() {
        List<JiraUser> jiraUsers = new ArrayList<JiraUser>();
        List users = userManager.getUsers();
        for (Object x : users) {
            User user = (User)x;
            jiraUsers.add(new JiraUserImpl(user));
        }
        return jiraUsers;
    }
}
