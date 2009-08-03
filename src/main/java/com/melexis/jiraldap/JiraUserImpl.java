package com.melexis.jiraldap;

import com.opensymphony.user.User;
import com.opensymphony.user.ImmutableException;

/**
 * A wrapper class which delagates all functionality to the JIRA User class
 * User: pti
 * Date: 16-Jul-2009
 * Time: 19:04:23
 */
public class JiraUserImpl implements JiraUser {
    private User user;

    public JiraUserImpl(String uid) {
        this.user = new User(uid, null);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        JiraUserImpl jiraUser = (JiraUserImpl) o;

        return !(user != null ? !user.equals(jiraUser.user) : jiraUser.user != null);

    }

    @Override
    public int hashCode() {
        return user != null ? user.hashCode() : 0;
    }

    public void setEmail(String s) {
        user.setEmail(s);
    }

    public String getEmail() {
        return user.getEmail();
    }

    public void setFullName(String s) {
        user.setFullName(s);
    }

    public String getFullName() {
        return user.getFullName();
    }

    public void store() throws JiraLdapException {
        try {
            user.store();
        } catch (ImmutableException e) {
            throw new JiraLdapException("Unable to persist user with uid =" + user.getName(), e);
        }
    }

    public String getName() {
        return user.getName();
    }

    public JiraUserImpl(User user) {
        this.user = user;
    }


}
