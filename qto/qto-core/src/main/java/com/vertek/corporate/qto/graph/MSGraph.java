package com.vertek.corporate.qto.graph;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.ejb.Singleton;
import javax.ejb.Startup;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.azure.identity.ClientSecretCredential;
import com.azure.identity.ClientSecretCredentialBuilder;
import com.microsoft.graph.authentication.TokenCredentialAuthProvider;
import com.microsoft.graph.models.DirectoryObject;
import com.microsoft.graph.models.Group;
import com.microsoft.graph.models.User;
import com.microsoft.graph.requests.DirectoryObjectCollectionWithReferencesPage;
import com.microsoft.graph.requests.GraphServiceClient;
import com.microsoft.graph.requests.GroupCollectionPage;

import okhttp3.Request;

@Startup
@Singleton
public class MSGraph {

    private static final Logger LOGGER = LoggerFactory.getLogger(MSGraph.class);

    /** Map of group name to list of email addresses */
    private Map<String, List<String>> groups = new HashMap<>();

    @PostConstruct
    public void init(){
        loadGroups();
    }

    public Map<String, List<String>> loadGroups() {
        try {
            LOGGER.debug("Loading groups from Azure AD");
            String aadClientId = System.getProperty("aadClientId");
            String aadTenantId = System.getProperty("aadTenantId");
            String clientSecret = System.getProperty("aadClientSecret");

            // Local dev: skip Azure AD if credentials are not configured
            if (aadClientId == null || aadClientId.isBlank()) {
                LOGGER.info("Azure AD disabled - aadClientId not configured, skipping group sync");
                return java.util.Collections.emptyMap();
            }

            //initialize the MS Graph client
            ClientSecretCredential clientSecretCredential = new ClientSecretCredentialBuilder()
                    .clientId(aadClientId)
                    .clientSecret(clientSecret)
                    .tenantId(aadTenantId)
                    .build();

            GraphServiceClient<Request> appClient = GraphServiceClient
                    .builder()
                    .authenticationProvider(
                            new TokenCredentialAuthProvider(List.of("https://graph.microsoft.com/.default"),
                                    clientSecretCredential)
                    )
                    .buildClient();

            //Retrieve groups starting with 'i90'
            GroupCollectionPage groupPage = appClient.groups()
                    .buildRequest()
                    .filter("startswith(displayName, 'i90')")
                    .get();
            LOGGER.debug("Found {} groups in Azure AD", groupPage.getCurrentPage().size());

            //Map group name to list of email addresses
            Map<String, List<String>> groupMap = new HashMap<>();
            for (Group group : groupPage.getCurrentPage()) {
                DirectoryObjectCollectionWithReferencesPage users = appClient.groups(group.id)
                        .members()
                        .buildRequest()
                        .top(999)
                        .get();

                LOGGER.debug("Found {} members for group {}", users.getCurrentPage().size(), group.displayName);
                List<String> emailAddresses = new ArrayList<>();
                for (DirectoryObject member : users.getCurrentPage()) {
                    User user = (User) member;
                    // if the user is not an Azure AD user (e.g. a guest user with a gmail account)
                    // we need to use the issuer to get the directory key that will match our subjects table
                    String email = user.mail;
                    if (email.contains("@hotmail.com")) {
                        emailAddresses.add("live.com#" + user.mail);
                    } else {
                        emailAddresses.add(user.mail);
                    }
                }
                groupMap.put(group.displayName, emailAddresses);
            }
            this.groups = groupMap;
            LOGGER.debug("Done loading groups from Azure AD");
            return this.groups;
        } catch (Exception e) {
            LOGGER.error("Error loading groups from Azure AD", e);
            return null;
        }
    }

    public Map<String, List<String>> getGroups() {
        return groups;
    }
}
