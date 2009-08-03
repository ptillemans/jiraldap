package com.melexis.jiraldap;

/**
 * User: pti
 * Date: 16-Jul-2009
 * Time: 19:07:01
 */
public interface JiraUser {
    void setEmail(String s);

    String getEmail();

    void setFullName(String s);

    String getFullName();

    void store() throws JiraLdapException;

    String getName();
}
