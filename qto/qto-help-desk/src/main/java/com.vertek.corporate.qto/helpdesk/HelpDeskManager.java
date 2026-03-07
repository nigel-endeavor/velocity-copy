package com.vertek.corporate.qto.helpdesk;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.vertek.corporate.qto.config.ConfigPropertyManager;
import com.vertek.corporate.qto.config.ConfigurationProperty;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import jakarta.ejb.Stateless;
import jakarta.inject.Inject;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

@Stateless
public class HelpDeskManager {

    private static final Logger LOGGER = LoggerFactory.getLogger(HelpDeskManager.class);

    @Inject
    private ConfigPropertyManager configPropertyManager;

    public void createTicket(final TicketRequest ticketRequest) throws Exception {
        LOGGER.info("Creating ticket: " + ticketRequest.getTitle());

        ConfigurationProperty adoAccessToken = configPropertyManager.findByKey("ADO_ACCESS_TOKEN");
        HttpClient client = HttpClient.newHttpClient();
        ObjectMapper objectMapper = new ObjectMapper();

        String createTicketRequestBody = "[{\"op\": \"add\", \"path\": \"/fields/System.Title\",\"from\": null,\"value\": \"" + ticketRequest.getTitle() + "\" }," +
                "{\"op\": \"add\",\"path\": \"/fields/System.Description\",\"from\": null,\"value\": \"" + ticketRequest.getDescription() + "\"}," +
                "{\"op\": \"add\",\"path\": \"/fields/userEmail\",\"from\": null,\"value\": \"" + ticketRequest.getUserEmail() + "\"}]";

        //Sanitization:  fix url
        HttpRequest createTicketRequest = HttpRequest.newBuilder()
                .uri(URI.create("https://dev.azure.com/<orgization/project>/_apis/wit/workitems/$task?api-version=7.1-preview.3"))
                .POST(HttpRequest.BodyPublishers.ofString(createTicketRequestBody))
                .header("Content-Type", "application/json-patch+json")
                .header("Authorization", "Basic " + adoAccessToken.getValue())
                .build();

        HttpResponse<String> createTicketResponse = client.send(createTicketRequest, HttpResponse.BodyHandlers.ofString());

        // capture the newly made ticket's ID
        String createTicketResponseBody = createTicketResponse.body();
        JsonNode jsonNode = objectMapper.readTree(createTicketResponseBody);
        int createdTicketId = jsonNode.get("id").asInt();

        if (createTicketResponse.statusCode() != 200) {
            LOGGER.error(createTicketResponseBody);
            throw new Exception("Error creating ticket.");
        }

        if (ticketRequest.getAttachment() != null) {
            byte[] fileData = new byte[ticketRequest.getAttachment().size()];
            for (int i = 0; i < ticketRequest.getAttachment().size(); i++) {
                Integer val = ticketRequest.getAttachment().get(Integer.toString(i));
                fileData[i] = val.byteValue();
            }

            //Sanitization:  fix url
            HttpRequest attachmentRequest = HttpRequest.newBuilder()
                    .uri(URI.create("https://dev.azure.com/<orgization/project>/_apis/wit/attachments?fileName="
                            + ticketRequest.getFileName() + "&api-version=7.1-preview.3"))
                    .POST(HttpRequest.BodyPublishers.ofByteArray(fileData))
                    .header("Content-Type", "application/octet-stream")
                    .header("Authorization", "Basic " + adoAccessToken.getValue())
                    .build();
            HttpResponse<String> attachmentResponse = client.send(attachmentRequest, HttpResponse.BodyHandlers.ofString());
            if (attachmentResponse.statusCode() != 201) {
                LOGGER.error(attachmentResponse.body());
                throw new Exception("Error uploading attachment.");
            }

            String attachmentResponseBody = attachmentResponse.body();
            JsonNode attachmentJsonNode = objectMapper.readTree(attachmentResponseBody);
            String attachmentLink = attachmentJsonNode.get("url").asText();

            String patchRequestBody = "[{\"op\": \"add\", \"path\": \"/relations/-\"," +
                    "\"value\": {\"rel\": \"AttachedFile\",\"url\": \"" + attachmentLink + "\"," +
                    "\"attributes\": {\"comment\": \"Added by user during ticket creation.\"}}}]";

            //Sanitization:  fix url
            HttpRequest linkAttachmentToWorkItemRequest = HttpRequest.newBuilder()
                    .uri(URI.create("https://dev.azure.com/<orgization/project>/_apis/wit/workitems/"+ createdTicketId +"?api-version=7.1-preview.3"))
                    .method("PATCH", HttpRequest.BodyPublishers.ofString(patchRequestBody))
                    .header("Content-Type", "application/json-patch+json")
                    .header("Authorization", "Basic " + adoAccessToken.getValue())
                    .build();

            HttpResponse<String> linkAttachmentToWorkItemResponse = client.send(linkAttachmentToWorkItemRequest, HttpResponse.BodyHandlers.ofString());
            if (linkAttachmentToWorkItemResponse.statusCode() != 200) {
                LOGGER.error(linkAttachmentToWorkItemResponse.body());
                throw new Exception("Error adding attachment.");
            }
        }
    }
}
