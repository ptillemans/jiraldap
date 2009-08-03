package com.melexis.jiraldap;

import java.util.List;

/**
 * An interface to encapsualte the Jira infrastructure
 * User: pti
 * Date: 16-Jul-2009
 * Time: 18:14:27
 */
public interface UserService {
    JiraUser getUser(String uid) throws JiraLdapException;

    /**
     * Create a user.
     *
     * @param uid    The login name for the user
     * @return       A newly created user object.
     * @throws       JiraLdapException when no user can be created.
     */
    JiraUser createUser(String uid) throws JiraLdapException;

    List<JiraUser> getUsers();
}
