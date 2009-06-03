/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.melexis.jiraldap;

import java.util.Set;

/**
 *
 * @author pti
 */
interface JiraService {

    public void addUser(User user);

    public Set<User> getUsers();

}
