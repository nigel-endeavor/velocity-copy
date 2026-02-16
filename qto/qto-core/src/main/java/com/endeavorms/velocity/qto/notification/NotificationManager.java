package com.endeavorms.velocity.qto.notification;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.lang.Nullable;

import com.endeavorms.velocity.qto.common.SecurityUtils;
import com.endeavorms.velocity.qto.common.StandardManager;
import com.endeavorms.velocity.qto.subject.Subject;
import com.endeavorms.velocity.qto.subject.SubjectManager;

import jakarta.ejb.TransactionAttribute;
import jakarta.ejb.TransactionAttributeType;
/**
 * @author rcasey
 * @since 6/15/2023
 */
@Component
public class NotificationManager extends StandardManager<Notification> {

    /**
     * Persistence tier for Notification.
     */
    @Autowired
    private NotificationJpaDao dao;

    
    @Autowired(required = false)
    @Nullable
    private NotificationWebsocket websocket;

    @Autowired
    private SubjectManager subjectManager;

    @Override
    protected NotificationJpaDao getDao() {
        return dao;
    }

    @Override
    public Notification create(final Notification notification) {
        notification.setCreatedDate(new Date());
        Notification created = super.create(notification);

        if (websocket != null) {
            websocket.pushNotification(created);
        }
        return created;
    }

    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
    public Notification create(final Long subjectId, final String header, final String body,
                               final String icon, final String link) {
        Notification notification = new Notification();
        notification.setSubjectId(subjectId);
        notification.setHeader(header);
        notification.setBody(body);
        notification.setIcon(icon);
        notification.setLinkTo(link);
        return create(notification);
    }

    public Notification dismiss(final Long notificationId) {
        Notification notification = retrieve(notificationId);
        notification.setDismissed(true);
        return edit(notification);
    }

    public void dismissAll() {
        Subject subject = subjectManager.findByUsername(SecurityUtils.getLoggedInUser());
        List<Notification> notifications = dao.findBySubjectId(subject.getId(), false);
        for (Notification notification : notifications) {
            notification.setDismissed(true);
            edit(notification);
        }
    }

    public List<Notification> findBySubjectId(final Long subjectId, final boolean includeDismissed) {
        return dao.findBySubjectId(subjectId, includeDismissed);
    }
}
