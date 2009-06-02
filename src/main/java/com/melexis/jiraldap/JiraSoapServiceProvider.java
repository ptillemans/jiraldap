/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.melexis.jiraldap;

import com.dolby.jira.net.soap.jira.JiraSoapService;
import com.dolby.jira.net.soap.jira.JiraSoapServiceServiceLocator;
import com.google.inject.Inject;
import com.google.inject.Provider;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.rpc.ServiceException;

/**
 *
 * @author pti
 */
public class JiraSoapServiceProvider implements Provider<JiraSoapService> {

    private String jiraEndPoint;

    @Inject
    public JiraSoapServiceProvider(String jiraEndPoint) {
        this.jiraEndPoint = jiraEndPoint;
    }

    public JiraSoapService get() {
        JiraSoapServiceServiceLocator locator = new JiraSoapServiceServiceLocator();
        locator.setJirasoapserviceV2EndpointAddress(jiraEndPoint);
        locator.setMaintainSession(true);
        JiraSoapService svc = null;
        try {
            svc = locator.getJirasoapserviceV2();
        } catch (ServiceException ex) {
            Logger.getLogger(JiraSoapServiceProvider.class.getName()).log(Level.SEVERE, null, ex);
        }
        return svc;
    }

}
