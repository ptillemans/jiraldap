/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.melexis.jiraldap;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;

import junit.framework.TestCase;
import static org.easymock.classextension.EasyMock.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

/**
 *
 * @author pti
 */
public class JiraServiceImplementationTest extends TestCase {

    private JiraService jira;
    private UserService userService;

    private List<JiraUser> createUsers() {
        List<JiraUser> users = new ArrayList<JiraUser>();
        JiraUser user;

        user = new MockJiraUser("abc");

        user.setFullName("Alice Botticelli");
        user.setEmail("abc@sevenseas.com");
        users.add(user);

        user = new MockJiraUser("bde");
        user.setFullName("Bob Detroit");
        user.setEmail("bde@sevenseas.com");
        users.add(user);

        user = new MockJiraUser("cef");
        user.setFullName("Charles Earphones");
        user.setEmail("cef@sevenseas.com");
        users.add(user);

        return users;
    }

    @Override
    public void setUp() throws RemoteException {
        userService = createMock(UserService.class);
        jira = new JiraServiceImplementation(userService);
    }

    public void tearDown() {
        jira = null;
    }

    public void testGetUsers() {

        List<JiraUser> testUsers = createUsers();
        expect(userService.getUsers()).andReturn(testUsers);
        replay(userService);

        List<JiraUser> users = jira.getUsers();

        verify(userService);


        assertThat(users.size(), equalTo(3));
        assertThat(users, hasItem(testUsers.get(0)));
        assertThat(users, hasItem(testUsers.get(1)));
        assertThat(users, hasItem(testUsers.get(2)));
    }

    public void testAddExistingUser() throws JiraLdapException {
        LdapUser user = new LdapUser("new", "New User", "new@shiny.com");
        JiraUser jiraUser = createMock(JiraUser.class);

        expect(userService.getUser(eq(user.getUid()))).andReturn(jiraUser);
        jiraUser.setFullName(eq(user.getName()));
        jiraUser.setEmail(eq(user.getEmail()));
        jiraUser.store();
        replay(userService);
        replay(jiraUser);

        jira.addUser(user);
        verify(userService);
        verify(jiraUser);
    }

    public void testAddNewUser() throws JiraLdapException {
        LdapUser user = new LdapUser("new", "New User", "new@shiny.com");
        JiraUser jiraUser = createMock(JiraUser.class);

        expect(userService.getUser(eq(user.getUid()))).andThrow(new JiraLdapException("test", null));
        expect(userService.createUser(eq(user.getUid()))).andReturn(jiraUser);
        jiraUser.setFullName(eq(user.getName()));
        jiraUser.setEmail(eq(user.getEmail()));
        jiraUser.store();
        replay(userService);
        replay(jiraUser);

        jira.addUser(user);
        verify(userService);
        verify(jiraUser);
    }
}
