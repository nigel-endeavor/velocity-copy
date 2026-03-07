package com.vertek.corporate.qto.fileimport.importactivity;

import jakarta.ejb.Singleton;
import jakarta.websocket.OnClose;
import jakarta.websocket.OnMessage;
import jakarta.websocket.OnOpen;
import jakarta.websocket.Session;
import jakarta.websocket.server.ServerEndpoint;
import java.util.ArrayList;
import java.util.List;

/**
 * @author rcasey
 * @since 9/8/2023
 */
@ServerEndpoint("/importActivities")
@Singleton
public class ImportActivityWebsocket {

    List<Session> sessionList = new ArrayList<>();

    @OnOpen
    public void onOpen(final Session session) {
        sessionList.add(session);
    }

    @OnMessage
    public void onMessage(final String message, final Session session) throws Exception {
        if ("\"ping\"".equals(message)) {
            session.getBasicRemote().sendObject("pong");
        }
    }

    public void sendRefreshMessage() {
        for (Session s : sessionList) {
            try {
                s.getBasicRemote().sendObject("refresh");
            } catch (Exception e) {
                //ignore
            }
        }
    }

    @OnClose
    public void onClose(final Session session) {
        sessionList.remove(session);
    }

}
