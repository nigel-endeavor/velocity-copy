package com.vertek.corporate.qto.notification;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.vertek.corporate.qto.common.SecurityUtils;
import com.vertek.corporate.qto.subject.Subject;
import com.vertek.corporate.qto.subject.SubjectManager;
import org.apache.shiro.authc.BearerToken;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import jakarta.ejb.Singleton;
import jakarta.inject.Inject;
import jakarta.websocket.OnClose;
import jakarta.websocket.OnError;
import jakarta.websocket.OnMessage;
import jakarta.websocket.Session;
import jakarta.websocket.server.ServerEndpoint;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author rcasey
 * @since 6/15/2023
 */
@ServerEndpoint("/notifications")
@Singleton
public class NotificationWebsocket {

    /** Private logger for this class. */
    private static final Logger LOGGER = LoggerFactory.getLogger(NotificationWebsocket.class);

    @Inject
    private NotificationManager notificationManager;

    @Inject
    private SubjectManager subjectManager;

    /** Client session map, key is subjectId. */
    Map<Long, Session> sessionMap = new HashMap<>();

    @OnMessage
    public void onMessage(final String message, final Session session) throws Exception {
        if ("\"ping\"".equals(message)) {
            session.getBasicRemote().sendObject("pong");
            return;
        }
        //parse the message for user token and showDismissed flag
        ObjectMapper mapper = new ObjectMapper();
        JsonNode jsonNode = mapper.readTree(message);
        BearerToken token = new BearerToken(jsonNode.get("token").asText());
        boolean showDismissed = jsonNode.get("showDismissed") != null && jsonNode.get("showDismissed").asBoolean();
        //authenticate the user
        org.apache.shiro.SecurityUtils.getSubject().login(token);
        String username = SecurityUtils.getLoggedInUser();
        Subject subject = subjectManager.findByUsername(username);
        LOGGER.debug("onMessage - subject is {}", username);
        //associate subject and session
        sessionMap.put(subject.getId(), session);
        //send notifications to client
        List<Notification> notifications = notificationManager.findBySubjectId(subject.getId(), showDismissed);
        String json = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(notifications);
        session.getBasicRemote().sendObject(json);
    }

    public void pushNotification(final Notification notification) {
        LOGGER.debug("Pushing notification {}", notification.getId());
        try {
            Session s = sessionMap.get(notification.getSubjectId());
            if (s != null && s.isOpen()) {
                ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
                String json = ow.writeValueAsString(notification);
                s.getBasicRemote().sendText(json);
            }
        } catch (Exception e) {
            LOGGER.error("Error pushing notification: {}", e.getMessage(), e);
        }
    }

    @OnClose
    public void onClose(final Session session) {
        LOGGER.debug("onClose");
        sessionMap.values().remove(session);
    }

    @OnError
    public void onError(final Throwable t) {
        LOGGER.error("onError: {}", t.getMessage(), t);
    }
}
