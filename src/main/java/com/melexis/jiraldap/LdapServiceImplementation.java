/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.melexis.jiraldap;

import java.util.HashSet;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.naming.Context;
import javax.naming.NamingEnumeration;
import javax.naming.NamingException;
import javax.naming.directory.Attribute;
import javax.naming.directory.Attributes;
import javax.naming.directory.DirContext;
import javax.naming.directory.SearchControls;
import javax.naming.directory.SearchResult;

/**
 *
 * @author pti
 */
public class LdapServiceImplementation implements LdapService {
    private String base;
    private String filter;
    private DirContext context;

    public DirContext getContext() {
        return context;
    }

    public void setContext(DirContext context) {
        this.context = context;
    }

    public String getBase() {
        return base;
    }

    private void setBase(String base) {
        this.base = base;
    }

    public String getFilter() {
        return filter;
    }

    public void setFilter(String filter) {
        this.filter = filter;
    }


    public LdapServiceImplementation() {
    }

    public LdapServiceImplementation(String base, String filter) {
        this();
        setBase(base);
        setFilter(filter);
    }

    private String getValue(Attributes attributes, String key) throws NamingException {
        Attribute attr = attributes.get(key);
        if (attr != null) {
            return (String) attr.get();
        } else {
            return "(null)";
        }
    }

    private LdapUser createUser(SearchResult entry) throws NamingException {
        Attributes attributes = entry.getAttributes();
        String uid = getValue(attributes,"uid");
        String name = getValue(attributes,"cn")
                      + " "
                      + getValue(attributes,"sn");
        String mail = getValue(attributes,"mail");
        LdapUser user = new LdapUser(uid,name,mail);
        return user;
    }

    private Set<LdapUser> searchUsers(int scope) throws NamingException, NamingException {
        SearchControls controls = new SearchControls();
        controls.setSearchScope(scope);
        NamingEnumeration result = context.search(getBase(), getFilter(), controls);
        // collect all results
        HashSet<LdapUser> users = new HashSet<LdapUser>();
        while (result.hasMore()) {
            SearchResult entry = (SearchResult) result.next();
            users.add(createUser(entry));
        }
        return users;
    }

    public Set<LdapUser> getUsers() {
        Set<LdapUser> users = new HashSet<LdapUser>();
        try {
            users = searchUsers(SearchControls.SUBTREE_SCOPE);
        } catch (NamingException ex) {
            Logger.getLogger(LdapServiceImplementation.class.getName()).log(Level.SEVERE, null, ex);
        }
        return users;
    }

}
