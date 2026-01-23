package com.vertek.corporate.qto.dataverse;

import com.azure.core.credential.TokenRequestContext;
import com.azure.identity.ClientSecretCredential;
import com.azure.identity.ClientSecretCredentialBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;


public class Dataverse {


    private static final Logger LOGGER = LoggerFactory.getLogger(Dataverse.class);

    public String pullFromCrm(final String opportunityNum) {
        try {
            //Sanitization:  add url and credentials
            String resource = "crm url";
            String aadClientId = "Client ID";
            String aadTenantId = "Tenant ID";
            String clientSecret = "client secret";
            String params = "/api/data/v9.2/opportunities(" + opportunityNum + ")?$select=name,new_pricingmrr,new_pricingnrc,new_contractterminmonths,actualclosedate";



            // Authenticate using Client Secret
           ClientSecretCredential clientSecretCredential = new ClientSecretCredentialBuilder()
                   .clientId(aadClientId)
                   .clientSecret(clientSecret)
                   .tenantId(aadTenantId)
                   .authorityHost("https://login.microsoftonline.com/" + aadTenantId) // Ensure correct tenant
                   .build();


            TokenRequestContext tokenRequestContext = new TokenRequestContext()
                    .addScopes(resource + "/.default");
            String accessToken = clientSecretCredential.getToken(tokenRequestContext).block().getToken();

            String response = null;
            if (accessToken != null) {
                response = callDataverseApi(resource + params, accessToken);
            } else {
                LOGGER.error("Failed to get access token");
                throw new RuntimeException("Failed to get access token");
            }
            return response;
        } catch (Exception e) {
            LOGGER.error("Failed to pull data from Dataverse", e);
            if (e.getMessage().contains("Illegal character")) {
                throw new RuntimeException("Illegal character in Opportunity # " + opportunityNum, e);
            } else {
                throw new RuntimeException(e.getMessage(), e);
            }
        }
    }

    private static String callDataverseApi(String resource, String accessToken) throws Exception {
        HttpClient client = HttpClient.newBuilder()
                .connectTimeout(Duration.ofMinutes(2))
                .build();

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(resource))
                .header("Authorization", "Bearer " + accessToken)
                .header("OData-MaxVersion", "4.0")
                .header("OData-Version", "4.0")
                .header("Accept", "application/json")
                .GET()
                .build();


        HttpResponse<String> response = null;
        try {
            response = client.send(request, HttpResponse.BodyHandlers.ofString());

            if (response.statusCode() != 200) {
               LOGGER.error(String.format("Web API call failed. Reason: %d %s", response.statusCode(), response.body()));
                if (response.statusCode() == 400) {
                    throw new RuntimeException("Invalid Opportunity #, there must be 36 characters in the Opportunity #");
                } else if (response.body().contains("Does Not Exist")) {
                    throw new RuntimeException("Opportunity # does not exist in Dataverse");
                } else {
                    throw new RuntimeException(response.body());
                }
            }
        } catch (Exception e) {
               LOGGER.error(String.format("Web API call failed. Reason: %d %s", response.statusCode(), response.body()));
            throw new RuntimeException(e.getMessage(), e);
        }
        return response.body();
    }
}
