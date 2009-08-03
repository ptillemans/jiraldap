/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.melexis.jiraldap;

import com.atlassian.jira.service.ServiceException;

import java.rmi.RemoteException;
import java.util.List;

/**
 *
 * @author pti
 */
interface JiraService {

    public void addUser(LdapUser user);

    public List<JiraUser> getUsers();

    public List<LdapUser> getLdapUsers();

}
