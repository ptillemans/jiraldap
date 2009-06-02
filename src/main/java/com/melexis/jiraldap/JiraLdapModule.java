/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.melexis.jiraldap;

import com.dolby.jira.net.soap.jira.JiraSoapService;
import com.google.inject.AbstractModule;


/**
 *
 * @author pti
 */
public class JiraLdapModule extends AbstractModule {

    String jiraEndPoint = null;

    @Override
    protected void configure() {
        bind(JiraSoapService.class).toProvider(JiraSoapServiceProvider.class);
    }

}
