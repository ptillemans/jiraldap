/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.melexis.jiraldap;

/**
 *
 * @author pti
 */
class User {

    private String uid;
    private String name;
    private String email;

    public User(String uid, String name, String email) {
        this.uid = uid;
        this.name = name;
        this.email = email;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    @Override
    public boolean equals(Object o) {
        User user = (User)o;
        boolean rslt = true;
        rslt = rslt && user.getUid().equals(uid);
        rslt = rslt && user.getEmail().equals(email);
        rslt = rslt && user.getName().equals(name);
        return rslt;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 97 * hash + (this.uid != null ? this.uid.hashCode() : 0);
        hash = 97 * hash + (this.name != null ? this.name.hashCode() : 0);
        hash = 97 * hash + (this.email != null ? this.email.hashCode() : 0);
        return hash;
    }


}
