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
interface LdapService {

    public Set<LdapUser> getUsers();
    
}
