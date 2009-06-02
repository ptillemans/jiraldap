/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.melexis.jiraldap;

import com.dolby.jira.net.soap.jira.JiraSoapService;
import com.dolby.jira.net.soap.jira.RemoteGroup;
import com.dolby.jira.net.soap.jira.RemoteUser;
import com.melexis.jiraldap.User;
import java.rmi.RemoteException;
import java.util.Arrays;
import java.util.Set;
import junit.framework.TestCase;
import org.hamcrest.collection.IsCollectionContaining;
import static org.easymock.EasyMock.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;


/**
 *
 * @author pti
 */
public class JiraServiceImplementationTest extends TestCase {

    JiraSoapService jiraSoapSvc;
    RemoteGroup group;

    private RemoteUser[] createRemoteUsers() {
        RemoteUser[] users = new RemoteUser[3];

        users[0] = new RemoteUser();
        users[0].setName("abc");
        users[0].setFullname("Alice Botticelli");
        users[0].setEmail("abc@sevenseas.com");
        
        users[1] = new RemoteUser();
        users[1].setName("bde");
        users[1].setFullname("Bob Detroit");
        users[1].setEmail("bde@sevenseas.com");

        users[2] = new RemoteUser();
        users[2].setName("cef");
        users[2].setFullname("Charles Earphones");
        users[2].setEmail("cef@sevenseas.com");
        return users;
    }

    @Override
    public void setUp() {
        jiraSoapSvc = createMock(JiraSoapService.class);
        group = new RemoteGroup();
        group.setUsers(createRemoteUsers());
    }

    public void testGetUsers() throws RemoteException {
        final String username = "dummy";
        final String password = "secret";
        final String token = "token";
        
        expect(jiraSoapSvc.login(username,password)).andReturn(token);
        expect(jiraSoapSvc.getGroup(token,"jira-users")).andReturn(group);
        replay(jiraSoapSvc);

        JiraService jira = new JiraServiceImplementation(jiraSoapSvc, username, password);
        Set<User> users = jira.getUsers();

        verify(jiraSoapSvc);
        
        assertThat(users.size(),equalTo(3));
        assertThat(users,hasItem(new User("abc","Alice Botticelli","abc@sevenseas.com")));
        assertThat(users,hasItem(new User("bde","Bob Detroit","bde@sevenseas.com")));
        assertThat(users,hasItem(new User("cef","Charles Earphones","cef@sevenseas.com")));
    }
}
